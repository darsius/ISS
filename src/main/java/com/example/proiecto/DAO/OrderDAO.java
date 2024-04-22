package com.example.proiecto.DAO;

import com.example.proiecto.Model.CustomerOrders;

public class OrderDAO extends GenericHibernateDAO{
    public OrderDAO() {
        super(CustomerOrders.class);
    }
}
