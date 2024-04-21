package com.example.proiecto.Controller;

import com.example.proiecto.DAO.GenericHibernateDAO;
import com.example.proiecto.Model.UserAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SingUpController {

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button signUpBtn;
    @FXML
    private Button logInBtn;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        // Check if the username field is empty
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter both username and password.");
            alert.showAndWait();
            return;
        }

        // Create a new user account with the provided username and password
        UserAccount newUser = new UserAccount(username, password);

        // Create a DAO instance for the User entity
        GenericHibernateDAO<UserAccount> userDAO = new GenericHibernateDAO<>(UserAccount.class);

        // Add the new user account to the database
        int result = userDAO.addData(newUser);
        if (result == 1) {
            // Sign-up successful
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sign Up Success");
            alert.setHeaderText(null);
            alert.setContentText("Sign up successful! You can now log in with your new account.");
            alert.showAndWait();
        } else {
            // Sign-up failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up Error");
            alert.setHeaderText(null);
            alert.setContentText("Sign up failed. Please try again later.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLogIn(ActionEvent event) {
        try {
            // Load the login page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proiecto/View/log-in-view.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

