package com.example.proiecto.Controller;

import com.example.proiecto.DAO.DAOInterface;
import com.example.proiecto.DAO.UserAccountDAO;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainPageController {
    ObservableList<UserAccount> allUserAccount;

    @FXML
    public TextField textField;



    public void initialize() {
        DAOInterface<UserAccount> userAccountDAO = new UserAccountDAO();
        allUserAccount = (ObservableList<UserAccount>) userAccountDAO.getAll();
        textField.setText(allUserAccount.toString());
    }
}
