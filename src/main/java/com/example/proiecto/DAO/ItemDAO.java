package com.example.proiecto.DAO;

import com.example.proiecto.Model.Item;
import javafx.collections.ObservableList;
import org.hibernate.Session;

public class ItemDAO extends GenericHibernateDAO<Item> {

    public ItemDAO() {
        super(Item.class);
    }

    @Override
    public int deleteData(Item data) {
        // You can add additional business logic or logging here if necessary
        System.out.println("Deleting item: " + data.getName());
        return super.deleteData(data);
    }

    public ObservableList<Item> getAllItems() {
        return getAll();
    }

    public Item findItemById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Item.class, id);
        } catch (Exception e) {
            // Handle exception - for example, log the error or throw a custom exception
            return null;
        }
    }
}