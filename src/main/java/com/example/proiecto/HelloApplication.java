package com.example.proiecto;

import com.example.proiecto.Controller.NavigateController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        NavigateController navigateController = NavigateController.getInstance();
//        navigateController.setStage(stage);
//        navigateController.switchMainPageView(new ActionEvent());

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/log-in-view.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Doughnuts Shop Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}