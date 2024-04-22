package com.example.proiecto.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class NavigateController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Deque<String> backHistory = new ArrayDeque<>();
    private Deque<String> forwardHistory = new ArrayDeque<>();

    private String currentView = null;
    private static NavigateController instance;


    public NavigateController(){}

//    public static NavigateController getInstance() {
//        if (instance == null) instance = new NavigateController();
//        return instance;
//    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void navigateTo(String fxmlPath, ActionEvent event) {
        try {
            if (currentView != null) {
                backHistory.push(currentView);
                forwardHistory.clear();
            }
            currentView = fxmlPath;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(currentView)));
            setUpSceneAndStage(event);
        } catch (IOException | NullPointerException e) {
            System.err.println("Navigation error: " + e.getMessage());
            // Optionally display an error dialog or notification to the user
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        if (!backHistory.isEmpty()) {
            forwardHistory.push(currentView);
            currentView = backHistory.pop();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(currentView)));
            setUpSceneAndStage(event);
        }
    }

    public void goForward(ActionEvent event) throws IOException {
        if (!forwardHistory.isEmpty()) {
            backHistory.push(currentView);
            currentView = forwardHistory.pop();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(currentView)));
            setUpSceneAndStage(event);
        }
    }


    public void switchToLogInView(ActionEvent event) throws IOException {
        navigateTo("/com/example/proiecto/View/log-in-view.fxml", event);
    }

    public void switchToSignUpView(ActionEvent event) throws IOException {
        navigateTo("/com/example/proiecto/View/sign-up-view.fxml", event);
    }

    public void switchOrdersHistoryView(ActionEvent event) throws IOException {
        navigateTo("/com/example/proiecto/View/orders-history-view.fxml", event);
    }

    public void switchToCurrentOrderView(ActionEvent event) throws IOException {
        navigateTo("/com/example/proiecto/View/current-order-view.fxml", event);
    }

    public void switchMainPageView(ActionEvent event) throws IOException {
        navigateTo("/com/example/proiecto/View/main-page-view.fxml", event);
    }

    private void setUpSceneAndStage(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        centerStageOnScreen();
        stage.show();
    }

    protected void centerStageOnScreen() {
        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }


}
