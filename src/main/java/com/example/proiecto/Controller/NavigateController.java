package com.example.proiecto.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigateController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLogInView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/proiecto/View/log-in-view.fxml")));
        setUpSceneAndStage(event);
    }

    public void switchToSignUpView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/proiecto/View/sign-up-view.fxml")));
        setUpSceneAndStage(event);
    }

    public void switchOrdersHistoryView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/proiecto/View/orders-history-view.fxml")));
        setUpSceneAndStage(event);
    }

    public void switchToCurrentOrderView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/proiecto/View/current-order-view.fxml")));
        setUpSceneAndStage(event);
    }

    public void switchMainPageView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/proiecto/View/main-page-view.fxml")));
        setUpSceneAndStage(event);
    }

    private void setUpSceneAndStage(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
