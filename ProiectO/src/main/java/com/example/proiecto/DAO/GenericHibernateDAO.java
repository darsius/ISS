package com.example.proiecto.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class GenericHibernateDAO<T> implements DAOInterface<T> {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private final Class<T> type;

    public GenericHibernateDAO(Class<T> type) {
        this.type = type;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // ... other methods ...

    @Override
    public int addData(T data) {
        return 0;
    }

    @Override
    public int updateData(T data) {
        return 0;
    }

    @Override
    public int deleteData(T data) {
        return 0;
    }

    @Override
    public ObservableList<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            List<T> list = session.createQuery("from " + type.getName(), type).getResultList();
            return FXCollections.observableArrayList(list);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList(); // Return an empty ObservableList in case of error
        }
    }

    // ... shutdown method ...
}
