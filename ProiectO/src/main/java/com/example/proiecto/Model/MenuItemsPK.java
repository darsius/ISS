package com.example.proiecto.Model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MenuItemsPK implements Serializable {
    @Column(name = "Menu_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuId;
    @Column(name = "Item_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemsPK that = (MenuItemsPK) o;
        return menuId == that.menuId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, itemId);
    }
}
