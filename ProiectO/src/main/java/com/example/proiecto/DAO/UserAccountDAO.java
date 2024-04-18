package com.example.proiecto.DAO;

import com.example.proiecto.Model.UserAccount;

public class UserAccountDAO extends  GenericHibernateDAO<UserAccount> {
    public UserAccountDAO() {
        super(UserAccount.class);
    }
}
