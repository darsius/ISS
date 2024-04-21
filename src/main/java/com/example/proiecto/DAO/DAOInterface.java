package com.example.proiecto.DAO;

import java.util.List;
import java.util.Objects;

public interface DAOInterface<T> {
    public int addData(T data);
    public int updateData(T data);
    public int deleteData(T data);

    public boolean exists(Object key);

    public T getById(Object id);
    List<T> getAll();
}
