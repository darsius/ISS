package com.example.proiecto.Controller;

import com.example.proiecto.DAO.DAOInterface;
import com.example.proiecto.DAO.UserAccountDAO;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class MainPageController {
    ObservableList<UserAccount> allUserAccount;

//    @FXML
//    public TextField textField;

    @FXML
    public ImageView doughnut1ImageView;

    @FXML
    private void handleBack(ActionEvent actionEvent) throws IOException {
        NavigateController.getInstance().goBack(actionEvent);
    }

    @FXML
    private void handleForward(ActionEvent actionEvent) throws IOException {
        NavigateController.getInstance().goForward(actionEvent);
    }


    public void initialize() {
        DAOInterface<UserAccount> userAccountDAO = new UserAccountDAO();
        allUserAccount = (ObservableList<UserAccount>) userAccountDAO.getAll();

//        textField.setText(allUserAccount.toString());
    }
}
