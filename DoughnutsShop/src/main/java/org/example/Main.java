package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.UserAccount;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        UserAccount u = new UserAccount();
        u.setUsername("User2");
        u.setPassword("password2");

        em.persist(u);

        em.getTransaction().commit();

    }
}