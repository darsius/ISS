package com.example.proiecto.Controller;

import com.example.proiecto.DAO.GenericHibernateDAO;
import com.example.proiecto.DAO.UserAccountDAO;
import com.example.proiecto.Model.UserAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LogInController {

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button logInBtn;
    @FXML
    private Button signUpBtn;

    private static UserAccount currentUser;

    private UserAccountDAO userDAO = new UserAccountDAO();

    @FXML
    private void handleLogin(ActionEvent event) {
        // Check username and password (add your authentication logic here)
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        // For demo purposes, assuming any username and password is valid
        if (isValidUser(username, password)) {
            currentUser = UserAccountDAO.getByUsername(username);
            try {
                // Load the main page FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proiecto/View/main-page-view.fxml"));
                Scene scene = new Scene(loader.load());

                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
                stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }

    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    private boolean isValidUser(String username, String password) {
        // Create a DAO instance for the User entity
//        GenericHibernateDAO<UserAccount> userDAO = new GenericHibernateDAO<>(UserAccount.class);

        // Check if a user with the given username exists
        List<UserAccount> userList = userDAO.getAll(); // Assuming you have a getAll method in your DAO
        for (UserAccount user : userList) {
            if (user.getUsername().equals(username)) {
                // Username found, check if the password matches
                if (user.getPassword().equals(password)) {
                    // Password matches, return true
                    return true;
                } else {
                    // Password doesn't match, show error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password. Please try again.");
                    alert.showAndWait();
                    return false;
                }
            }
        }
        // Username not found
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText("User not found. Please try again.");
        alert.showAndWait();
        return false;
    }


    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            // Load the sign-up page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proiecto/View/sign-up-view.fxml"));
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



