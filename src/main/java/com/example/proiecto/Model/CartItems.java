package com.example.proiecto.Model;

import javax.persistence.*;

@Entity
@Table(name = "Cart_Items")
public class CartItems {
    @EmbeddedId
    private CartItemsPK id;

    @Column(name = "quantity")
    private Integer quantity;

    // Constructors, getters, and setters
    public CartItems() {}

    public CartItems(Cart cart, Item item, int quantity) {
        this.id = new CartItemsPK();
        this.id.setCart_id(cart.getId());
        this.id.setItem_id(item.getId());
        this.quantity = quantity;
    }

    public CartItemsPK getId() {
        return id;
    }

    public void setId(CartItemsPK id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
