package com.example.proiecto.Controller;

import com.example.proiecto.DAO.DAOInterface;
import com.example.proiecto.DAO.UserAccountDAO;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class MainPageController {
    ObservableList<UserAccount> allUserAccount;

//    @FXML
//    public TextField textField;

    @FXML
    public ImageView doughnut1ImageView;


    public void initialize() {
        DAOInterface<UserAccount> userAccountDAO = new UserAccountDAO();
        allUserAccount = (ObservableList<UserAccount>) userAccountDAO.getAll();

//        textField.setText(allUserAccount.toString());
    }
}
