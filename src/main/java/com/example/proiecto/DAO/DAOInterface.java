package com.example.proiecto.DAO;

import java.util.List;

public interface DAOInterface<T> {
    public int addData(T data);
    public int updateData(T data);
    public int deleteData(T data);

    List<T> getAll();
}
