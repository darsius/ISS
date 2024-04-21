package com.example.proiecto.Controller;

import com.example.proiecto.DAO.ItemDAO;
import com.example.proiecto.Model.Item;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainPageController {
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

    private final LinkedHashMap<ImageView, Item> imageViewItemMap = new LinkedHashMap<>();
    private Item selectedItem;

    private ItemDAO itemDAO = new ItemDAO();

    private ObservableList<Item> cartItems = FXCollections.observableArrayList();

    public MainPageController() {
    }

    public MainPageController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void initialize() {
        addButton.setOnAction(event -> addToCart());

        cartTable.setItems(cartItems);
        setupTableColumns(cartTable);
        cartTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        List<Item> listOfItemsFromMenu = getMenuItemsFromDatabase();
        UserAccount currentUser = LogInController.getCurrentUser();

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

    private void setupTableColumns(TableView<Item> tableView) {
        TableColumn<Item, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, BigDecimal> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.getColumns().setAll(colName, colPrice, colDescription);
    }

    @FXML
    private void addToCart() {
        if (selectedItem != null) {
            if (!cartItems.contains(selectedItem)) {
                cartItems.add(selectedItem);
                System.out.println("Added to cart: " + selectedItem.getName());
            } else {
                System.out.println("Item already in cart.");
            }
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
        if (selectedItem != null) {
            cartItems.remove(selectedItem);  // Remove the selectedItem from the observable list
            System.out.println("Removed from cart: " + selectedItem.getName());
            cartTable.getSelectionModel().clearSelection();  // Clear selection after removal
        } else {
            System.out.println("No item selected to remove.");
        }
    }
}
