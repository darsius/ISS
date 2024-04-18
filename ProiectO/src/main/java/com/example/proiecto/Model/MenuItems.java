package com.example.proiecto.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(MenuItemsPK.class)
public class MenuItems {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Menu_id")
    private int menuId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Item_id")
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
        MenuItems menuItems = (MenuItems) o;
        return menuId == menuItems.menuId && itemId == menuItems.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, itemId);
    }
}
