package com.example;

import com.example.entities.UserAccount;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

//        Map<?, ?> props = new HashMap<>();
//        EntityManagerFactory emf = new HibernatePersistenceProvider()
//                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);
//
//        EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//
//        UserAccount u = new UserAccount();
//        u.setUsername("User2");
//        u.setPassword("password2");
//
//        em.persist(u);
//
//        em.getTransaction().commit();

    }
}