package com.example.proiecto.DAO;

import com.example.proiecto.DAO.GenericHibernateDAO;
import com.example.proiecto.Model.Item;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.ObservableList;

public class ItemDAO extends GenericHibernateDAO<Item> {

    public ItemDAO() {
        super(Item.class);
    }

    public ObservableList<Item> getAllItems() {
        return getAll();
    }
}