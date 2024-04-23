package com.example.proiecto.Controller;

import com.example.proiecto.DAO.CartItemsDAO;
import com.example.proiecto.DAO.OrderDAO;
import com.example.proiecto.Model.Cart;
import com.example.proiecto.Model.CustomerOrders;
import com.example.proiecto.Model.Item;
import com.example.proiecto.Model.UserAccount;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentOrderController extends NavigateController{
    @FXML
    private Button logOutButton;
    @FXML
    private Button goToOrdersHistoryButton;
    @FXML
    private Button goToMainPageButton;

    @FXML
    private TextField orderIdTF;
    @FXML
    private TextField orderStatusTF;
    @FXML
    private TextField orderPlacedTimeTF;
    @FXML
    private TextField orderDeliveryTimeTF;
    @FXML
    private TextField orderTotalTF;

    @FXML
    private TableView tableView;

//    private CartItemsDAO cartItemsDAO;

    private List<Item> cartItems = new ArrayList<>();
    private Map<Item, Integer> cartItemsWithQuantities;
    private UserAccount currentUser;

    private List<Item> cartItms;

    private CustomerOrders order;

    private LocalTime deliveryTime;


    public void initialize() {
        deliveryTime = Menu.getDeliveryTimeInMinutes();


        currentUser = LogInController.getCurrentUser();
        List<Item> l = MainPageController.returnCartItems();
        cartItems.addAll(l);
        l.clear();
        System.out.println("Id of the user is " + currentUser.getId());

        Cart cart = currentUser.getCart();
        cartItms = cart.getItems();
        for (Item i: cartItms) {
            System.out.println(i.getName() + " " + i.getPrice());
        }

        order = OrderDAO.getPendingOrderForUser(currentUser.getId());
        //order e null
        int clientId = order.getClientId();

        System.out.println("Id ul clientului la order este "+ clientId);


        tableView.refresh();

        if (currentUser != null) {
            CustomerOrders pendingOrder = OrderDAO.getPendingOrderForUser(currentUser.getId());
            if (pendingOrder != null) {
                System.out.println(pendingOrder.getId());
                seUpDetailsField();
                setupTableColumns();
            }
        }

        logOutButton.setOnAction(event -> {
            try {
                switchToLogInView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the log-in view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToOrdersHistoryButton.setOnAction(event -> {
            try {
                switchOrdersHistoryView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the current order view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToMainPageButton.setOnAction(event -> {
            try {
                switchMainPageView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the main view: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void seUpDetailsField() {
        Timestamp orderTimestamp = order.getDate(); // Assuming getDate() returns the timestamp when the order was placed
        LocalTime orderTime = orderTimestamp.toLocalDateTime().toLocalTime();
        DateTimeFormatter formatterPlacedOrder = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTimeForPlacedOrder = orderTime.format(formatterPlacedOrder);

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);


        String orderDetails = MainPageController.returnOrderDetails();
        String[] word = orderDetails.split(" ");

        orderIdTF.setText(String.valueOf(order.getId()));
        orderStatusTF.setText(order.getStatus());
        orderPlacedTimeTF.setText(formattedTimeForPlacedOrder);
        orderDeliveryTimeTF.setText(String.valueOf(Math.min(orderTime.getMinute() + deliveryTime.getMinute() - currentTime.getMinute(), 0)) + " minutes");
        if (orderDeliveryTimeTF.getText().equals("0 minutes")) {
            System.out.println("the order has been delivered!");
        }
//        orderTotalTF.setText(word[4] + " RON");
        //delivery time - current time for "time until delivery"
    }

    private void setupTableColumns() {
        // Name column
        TableColumn<Item, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Price column
        TableColumn<Item, BigDecimal> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Description column
        TableColumn<Item, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Quantity column
        TableColumn<Item, Integer> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(MainPageController.returnCartQuantities().get(cellData.getValue()))
        );

        // Set the columns to the table
        tableView.getColumns().setAll(colName, colPrice, colDescription, colQuantity);

        // Set the items to the table
        tableView.setItems(FXCollections.observableArrayList(cartItms));
    }

}