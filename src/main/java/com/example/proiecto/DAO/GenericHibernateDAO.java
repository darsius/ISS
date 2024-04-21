package com.example.proiecto.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public class GenericHibernateDAO<T> implements DAOInterface<T> {
    public static final SessionFactory sessionFactory = buildSessionFactory();
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

    @Override
    public boolean exists(Object key) {
        try (Session session = sessionFactory.openSession()) {
            Field idField = type.getDeclaredField("id"); // Assumes that the primary key field is named "id"
            idField.setAccessible(true);

            Query query = session.createQuery("from " + type.getName() + " where " + idField.getName() + " = :id");
            query.setParameter("id", key);
            List<T> resultList = query.list();
            return !resultList.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T getById(Object id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(type, (Serializable) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int addData(T data) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(data);
            transaction.commit();
            return 1;  // Return 1 to indicate success
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return 0;  // Return 0 to indicate failure
        }
    }

    @Override
    public int updateData(T data) {
        Transaction transaction = null;
        int result = 0;

        try (Session session = sessionFactory.openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();

            // Save the data object
            session.update(data);

            // Commit the transaction
            transaction.commit();
            result = 1; // or you can return the ID of the updated entity
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            result = -1;
        }
        return result;
    }


    @Override
    public int deleteData(T data) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(data);
            transaction.commit();
            return 1;  // Return 1 to indicate successful deletion
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Roll back the transaction in case of errors
            }
            e.printStackTrace();
            return 0;  // Return 0 to indicate failure
        }
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
