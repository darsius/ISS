package com.example.proiecto.Controller;

import com.example.proiecto.DAO.CartDAO;
import com.example.proiecto.DAO.CartItemsDAO;
import com.example.proiecto.DAO.ItemDAO;
import com.example.proiecto.DAO.OrderDAO;
import com.example.proiecto.Model.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class MainPageController extends NavigateController{
    ObservableList<UserAccount> allUserAccount;

    @FXML
    public ImageView doughnut1ImageView;
    @FXML
    public ImageView doughnut2ImageView;
    @FXML
    public ImageView doughnut3ImageView;
    @FXML
    public ImageView doughnut4ImageView;
    @FXML
    public ImageView doughnut5ImageView;
    @FXML
    public ImageView doughnut6ImageView;
    @FXML
    public ImageView doughnut7ImageView;
    @FXML
    public ImageView doughnut8ImageView;
    @FXML
    public ImageView doughnut9ImageView;

    @FXML
    public TableView cartTable;

    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Number> priceColumn;

    @FXML
    private Button selectDoughnutImageButton;
    @FXML
    private Button addButton;
    @FXML
    private Button payAndPlaceOrderButton;

    @FXML
    private Button logOutButton;
    @FXML
    private Button goToOrdersHistoryButton;
    @FXML
    private Button goToOrderButton;

    @FXML
    private Label totalSumLabel;

    private final LinkedHashMap<ImageView, Item> imageViewItemMap = new LinkedHashMap<>();

    private Item selectedItem;

    private ItemDAO itemDAO = new ItemDAO();

    private OrderDAO orderDAO = new OrderDAO();

    private ObservableList<Item> cartItems = FXCollections.observableArrayList();
    private static ObservableList<Item> cartItemsBeforePaying = FXCollections.observableArrayList();
    private Map<Item, Integer> itemQuantities = new HashMap<>();
    private static Map<Item, Integer> itemQuantitiesBeforePaying = new HashMap<>();

    private DoubleProperty totalSum = new SimpleDoubleProperty(0.0);

    private static StringBuilder orderDetails = new StringBuilder();

    private CartDAO cartDAO = new CartDAO();
    private CartItemsDAO cartItemsDAO = new CartItemsDAO();

    private UserAccount currentUser;

    public MainPageController() {

    }

    public MainPageController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public MainPageController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    public void initialize() {
        System.out.println("Delivery time is: " + Menu.getDeliveryTimeInMinutes());


        logOutButton.setOnAction(event -> {
            try {
                switchToLogIn(event);
            } catch (IOException e) {
                System.err.println("Failed to load the log-in view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToOrdersHistoryButton.setOnAction(event -> {
            try {
                switchToOrdersHistoryView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the orders history view: " + e.getMessage());
                e.printStackTrace();
            }
        });
        goToOrderButton.setOnAction(event -> {
            try {
                switchToOrderView(event);
            } catch (IOException e) {
                System.err.println("Failed to load the current order view: " + e.getMessage());
                e.printStackTrace();
            }
        });

        totalSumLabel.textProperty().bind(totalSum.asString("Total: $%.2f"));
        addButton.setOnAction(event -> addToCart());

        cartTable.setItems(cartItems);
        setupTableColumns(cartTable);
        cartTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        List<Item> listOfItemsFromMenu = getMenuItemsFromDatabase();
        currentUser = LogInController.getCurrentUser();

        if (currentUser != null &&
                currentUser.getUsername().equals("admin") &&
                currentUser.getPassword().equals("admin")) {
            selectDoughnutImageButton.setVisible(true);
        } else {
            selectDoughnutImageButton.setVisible(false);
        }

        loadImagesForMenuItems(listOfItemsFromMenu.subList(0, 9));
        addToolTipsForImageViews();

    }

    private static boolean checkForPendingOrder() {
        UserAccount currentUser1 = LogInController.getCurrentUser();
        if (currentUser1 != null) {
            CustomerOrders pendingOrder = OrderDAO.getPendingOrderForUser(currentUser1.getId());
            if (pendingOrder != null) {
                if (pendingOrder.getClientId() == currentUser1.getId()) {
                    System.out.println("avem order: " +pendingOrder.getId());
                    return true;
                }
            }
        }
        return false;
    }

    private void setupTableColumns(TableView<Item> tableView) {
        TableColumn<Item, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, BigDecimal> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Item, Integer> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(itemQuantities.get(cellData.getValue())));
        tableView.getColumns().setAll(colName, colPrice, colDescription, colQuantity);
    }

    @FXML
    private void addToCart() {
        if (selectedItem != null) {
            if (itemQuantities.containsKey(selectedItem)) {
                itemQuantities.put(selectedItem, itemQuantities.get(selectedItem) + 1);
            } else {
                itemQuantities.put(selectedItem, 1);
                cartItems.add(selectedItem);
            }
            updateTotalSum();
            cartTable.refresh(); // Refresh the table to update the view
            System.out.println("Added to cart: " + selectedItem.getName() +
                    ", Quantity: " + itemQuantities.get(selectedItem));
        } else {
            System.out.println("No item selected.");
        }
    }

    @FXML
    private void handleImageViewClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        selectedItem = imageViewItemMap.get(clickedImageView);
        System.out.println("Selected item: " + selectedItem.getName());
    }

    private void loadImagesForMenuItems(List<Item> items) {
        ImageView[] imageViews = {
                doughnut1ImageView, doughnut2ImageView, doughnut3ImageView,
                doughnut4ImageView, doughnut5ImageView, doughnut6ImageView,
                doughnut7ImageView, doughnut8ImageView, doughnut9ImageView
        };

        for (int i = 0; i < 9; i++) {
            ImageView imageView = imageViews[i];
            Item item = items.get(i);
            imageViewItemMap.put(imageView, item);

            imageView.setOnMouseClicked(this::handleImageViewClick);
        }
    }

    private void addToolTipsForImageViews() {
        for (ImageView imageView : imageViewItemMap.keySet()) {
            Item item = imageViewItemMap.get(imageView);
            String tooltipText = String.format("Name: %s\nPrice: %.2f\nDescription: %s",
                    item.getName(), item.getPrice(), item.getDescription());
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setShowDuration(Duration.INDEFINITE);
            Tooltip.install(imageView, tooltip);
        }
    }

    private List<Item> getMenuItemsFromDatabase() {
        List<Item> listOfItems = itemDAO.getAllItems();
        List<Item> menuListOfItems = new ArrayList<>();
        for (Item item : listOfItems) {
            if (item.isInMenu()) {
                menuListOfItems.add(item);
            }
        }
        return menuListOfItems;
    }

    @FXML
    private void removeSelectedItem() {
        Item selectedItem = (Item) cartTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null && itemQuantities.containsKey(selectedItem)) {
            int currentQuantity = itemQuantities.get(selectedItem);
            if (currentQuantity > 1) {
                itemQuantities.put(selectedItem, currentQuantity - 1);
            } else {
                itemQuantities.remove(selectedItem);
                cartItems.remove(selectedItem); // Remove item from the observable list if quantity goes to zero
            }
            updateTotalSum();
            cartTable.refresh(); // Refresh the table
            System.out.println("Removed from cart: " + selectedItem.getName() + ", Remaining Quantity: " + itemQuantities.getOrDefault(selectedItem, 0));
        } else {
            System.out.println("No item selected to remove.");
        }
    }

    private void switchToLogIn(ActionEvent event) throws IOException {
        super.switchToLogInView(event);
    }

    private void switchToOrdersHistoryView(ActionEvent event) throws IOException {
        super.switchOrdersHistoryView(event);
    }

    private void switchToOrderView(ActionEvent event) throws IOException {
        if (!checkForPendingOrder()) {
            showErrorAlert("You need to place an order first!");
            return;
        }
        super.switchToCurrentOrderView(event);
    }

    @FXML
    private void handlePayAndPlaceOrder(ActionEvent event) {
        System.out.println("Pay and Place Order button pressed. Preparing order...");
        UserAccount currentUser = LogInController.getCurrentUser(); // Placeholder for user fetching method

        CustomerOrders newOrder = new CustomerOrders();

        newOrder.setDate(new Timestamp(System.currentTimeMillis())); // Set the current time as order time
        newOrder.setStatus("Pending"); // Assuming a default status
        newOrder.setClientId(currentUser.getId());

        BigDecimal total = BigDecimal.valueOf(totalSum.get());

        System.out.println("Idul clientului logat este " + currentUser.getId());

        if (checkForPendingOrder()) {
            showErrorAlert("There is an order in process");
            return;
        }
        if (orderDAO.addData(newOrder) == 1) {
            System.out.println("Order successfully placed in the database with ID: " + newOrder.getId());
//            orderId = newOrder.getId();
            showSuccessAlert();
            System.out.println("Items from cart ");
            System.out.println(cartItems);
            System.out.println(itemQuantities);

            Cart cart = new Cart();
            cart.setUser(currentUser);
            cart.setItems(cartItems);
            cartDAO.addData(cart);

            CartItems cartItems1 = new CartItems();


//            CartItems cartItems1 = new CartItems();
//            cartItems1.setQuantity(cartItems);
//            System.out.println("cart items: " + cartItems1);
//            cartItemsDAO.addData(cartItems1);



            cartItemsBeforePaying.addAll(cartItems);

            itemQuantitiesBeforePaying.putAll(itemQuantities);

//            clearCart();
        }  else {
            System.out.println("Failed to place the order.");
            showErrorAlert("Failed to place the order");
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Successful");
        alert.setHeaderText(null);
        alert.setContentText("Your order has been placed successfully!");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearCart() {
        cartItems.clear();
        itemQuantities.clear();
        updateTotalSum();
    }

    private void updateTotalSum() {
        BigDecimal sum = BigDecimal.ZERO; // Start with a sum of zero
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            BigDecimal price = entry.getKey().getPrice(); // This is a BigDecimal
            BigDecimal quantity = new BigDecimal(entry.getValue()); // Convert quantity to BigDecimal
            BigDecimal totalPrice = price.multiply(quantity); // Multiply price by quantity
            sum = sum.add(totalPrice); // Add the total price to the sum
        }
        totalSum.set(sum.doubleValue()); // Update the total sum property, convert BigDecimal to double
    }

    public static String returnOrderDetails() {
        return orderDetails.toString();
    }

    public static List<Item> returnCartItems() {
        return cartItemsBeforePaying;
    }
    public static Map<Item, Integer> returnCartQuantities() {
        return itemQuantitiesBeforePaying;
    }


}
