package com.example.proiecto.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CurrentOrderController extends NavigateController{
    @FXML
    private Button logOutButton;
    @FXML
    private Button goToOrdersHistoryButton;
    @FXML
    private Button goToMainPageButton;


    public void initialize() {
        logOutButton.setOnAction(event -> {
            try {
                switchToLogInView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the log-in view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToOrdersHistoryButton.setOnAction(event -> {
            try {
                switchOrdersHistoryView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the current order view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToMainPageButton.setOnAction(event -> {
            try {
                switchMainPageView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the main view: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
