package com.example.proiecto.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SingUpController implements Initializable {

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button signUpBtn;
    @FXML
    private Button logInBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
