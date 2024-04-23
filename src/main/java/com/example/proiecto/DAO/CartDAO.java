package com.example.proiecto.DAO;

import com.example.proiecto.Model.Cart;
import com.example.proiecto.Model.CartItems;
import com.example.proiecto.Model.CartItemsPK;
import com.example.proiecto.Model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDAO extends GenericHibernateDAO<Cart> {

    public CartDAO() {
        super(Cart.class);
    }

    // Method to update or insert item quantity in a cart
    public void updateItemQuantity(Cart cart, Item item, int quantity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CartItemsPK pk = new CartItemsPK();
            pk.setCart_id(cart.getId());
            pk.setItem_id(item.getId());
            CartItems cartItem = session.get(CartItems.class, pk);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
            } else {
                cartItem = new CartItems(cart, item, quantity);
                session.persist(cartItem); // use persist instead of save to enforce consistency
            }
            session.saveOrUpdate(cartItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e; // Rethrow the exception after rollback to handle or log it in the calling code.
        }
    }

    // Method to clear all items from a cart
    public void clearCart(Cart cart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM CartItems WHERE id.cart_id = :cartId")
                    .setParameter("cartId", cart.getId())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e; // Rethrow the exception after rollback to handle or log it in the calling code.
        }
    }


}
