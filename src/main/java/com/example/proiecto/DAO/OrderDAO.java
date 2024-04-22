package com.example.proiecto.DAO;

import com.example.proiecto.Model.CustomerOrders;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO extends GenericHibernateDAO{
    public OrderDAO() {
        super(CustomerOrders.class);
    }

    public static CustomerOrders getPendingOrderForUser(int userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<CustomerOrders> query = session.createQuery(
                    "FROM CustomerOrders WHERE clientId = :clientId AND status = 'Pending' ORDER BY date DESC",
                    CustomerOrders.class
            );
            query.setParameter("clientId", userId);
            query.setMaxResults(1);

            List<CustomerOrders> results = query.list();

            if (!results.isEmpty()) {
                return results.get(0); // Return the most recent pending order
            }
            return null; // Return null if there's no such order
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of any exception
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed after the operation
            }
        }
    }
}
