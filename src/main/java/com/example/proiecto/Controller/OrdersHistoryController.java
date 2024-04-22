package com.example.proiecto.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class OrdersHistoryController extends NavigateController{
    @FXML
    private Button logOutButton;
    @FXML
    private Button goToCurrentOrderButton;
    @FXML
    private Button goToMainPageButton;


    public void initialize() {
        logOutButton.setOnAction(event -> {
            try {
                switchToLogIn(event);
            } catch (IOException e) {
                System.err.println("Failed to load the log-in view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToCurrentOrderButton.setOnAction(event -> {
            try {
                switchToCurrentOrderView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the current order view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToMainPageButton.setOnAction(event -> {
            try {
                switchToMainView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the main view: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void switchToLogIn(ActionEvent event) throws IOException {
        super.switchToLogInView(event);
    }


    private void switchToMainView(ActionEvent event) throws IOException {
        super.switchMainPageView(event);
    }
}
