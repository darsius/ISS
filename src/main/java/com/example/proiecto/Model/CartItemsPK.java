package com.example.proiecto.Model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemsPK implements Serializable {
    private int cart_id;
    private int item_id;

    // Constructors, getters, and setters
    public CartItemsPK() {}

    public CartItemsPK(int cart_id, int item_id) {
        this.cart_id = cart_id;
        this.item_id = item_id;

    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemsPK)) return false;
        CartItemsPK that = (CartItemsPK) o;
        return getCart_id() == that.getCart_id() &&
                getItem_id() == that.getItem_id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCart_id(), getItem_id());
    }

}