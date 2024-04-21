package com.example.proiecto.Controller;

import com.example.proiecto.DAO.DAOInterface;
import com.example.proiecto.DAO.UserAccountDAO;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class MainPageController{
    ObservableList<UserAccount> allUserAccount;

//    @FXML
//    public TextField textField;

    @FXML
    public ImageView doughnut1ImageView;

    @FXML
    public TableView cartTable;

    @FXML
    private Button selectDoughnutImageButton;

    private LogInController logInController;

    @FXML
    private void handleBack(ActionEvent actionEvent) throws IOException {
        NavigateController.getInstance().goBack(actionEvent);
    }

    @FXML
    private void handleForward(ActionEvent actionEvent) throws IOException {
        NavigateController.getInstance().goForward(actionEvent);
    }

    @FXML
    private void addToCart(){}

    @FXML
    private void removeFromCart(){}


    public void initialize() {
        UserAccount currentUser = LogInController.getCurrentUser();

        if (currentUser != null &&
            currentUser.getUsername().equals("admin") &&
            currentUser.getPassword().equals("admin")) {
            System.out.println("admin logat");
            selectDoughnutImageButton.setVisible(true);
        } else {
            System.out.println("user logat");
            selectDoughnutImageButton.setVisible(false);
        }



    }
}
