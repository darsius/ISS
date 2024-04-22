package com.example.proiecto.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CurrentOrderController extends NavigateController{
    @FXML
    private Button logOutButton;
    @FXML
    private Button goToOrdersHistoryButton;
    @FXML
    private Button goToMainPageButton;

    @FXML
    private TextField orderIdTF;
    @FXML
    private TextField orderStatusTF;
    @FXML
    private TextField orderPlacedTimeTF;
    @FXML
    private TextField orderDeliveryTimeTF;
    @FXML
    private TextField orderTotalTF;


    public void initialize() {
        seUpDetailsField();
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

    private void seUpDetailsField() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        String orderDetails = MainPageController.returnOrderDetails();
        String[] word = orderDetails.split(" ");
        orderIdTF.setText(word[0]);
        orderStatusTF.setText(word[1]);
        orderPlacedTimeTF.setText(formattedTime);
        orderTotalTF.setText(word[4] + " RON");
        //delivery time - current time for "time until delivery"
    }

}
