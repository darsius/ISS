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

public class CartItemsDAO extends GenericHibernateDAO<CartItems> {

    public CartItemsDAO() {
        super(CartItems.class);
    }

    // Method to update or insert item quantity in a cart
    public void updateItemQuantity(Cart cart, Item item, int quantity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CartItemsPK pk = new CartItemsPK(cart.getId(), item.getId());
            CartItems cartItem = session.get(CartItems.class, pk);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
            } else {
                cartItem = new CartItems(cart, item, quantity);
                session.persist(cartItem);
            }
            session.saveOrUpdate(cartItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Failed to update or insert item quantity", e);
        }
    }

    // Method to fetch all items with their quantities in a cart
    public Map<CartItemsPK, Integer> getItemsWithQuantities(int cartId) {
        Map<CartItemsPK, Integer> itemsWithQuantities = new HashMap<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Object[]> query = session.createQuery(
                    "SELECT ci.id, ci.quantity FROM CartItems ci WHERE ci.id.cart_id = :cartId", Object[].class);
            query.setParameter("cartId", cartId);
            List<Object[]> resultList = query.list();
            for (Object[] result : resultList) {
                itemsWithQuantities.put((CartItemsPK) result[0], (Integer) result[1]);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch items with quantities", e);
        }
        return itemsWithQuantities;
    }

    // Method to clear all items from a cart
    public void clearCartItems(Cart cart) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM CartItems ci WHERE ci.id = :cart");
            query.setParameter("cart", cart);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Failed to clear cart items", e);
        }
    }
}
