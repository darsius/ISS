package com.example.proiecto.DAO;

import com.example.proiecto.Model.UserAccount;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.Serializable;

public class UserAccountDAO extends  GenericHibernateDAO<UserAccount> {
    public UserAccountDAO() {
        super(UserAccount.class);
    }

    public static UserAccount getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            // Create HQL query to retrieve the user by username
            String hql = "FROM UserAccount WHERE username = :username";
            Query query = session.createQuery(hql, UserAccount.class);
            query.setParameter("username", username);
            return (UserAccount) ((org.hibernate.query.Query<?>) query).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
