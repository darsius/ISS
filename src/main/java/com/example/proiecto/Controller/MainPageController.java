package com.example.proiecto.Controller;

import com.example.proiecto.DAO.ItemDAO;
import com.example.proiecto.Model.Item;
import com.example.proiecto.Model.UserAccount;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
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
    private Button selectDoughnutImageButton;
    @FXML
    private Button addButton;

    private final LinkedHashMap<ImageView, Item> imageViewItemMap = new LinkedHashMap<>();
    private Item selectedItem;

    private ItemDAO itemDAO = new ItemDAO();

    public MainPageController() {
    }

    public MainPageController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void initialize() {
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

        addButton.setOnAction(event -> {
            if (selectedItem != null) {
                System.out.println("Added: " + selectedItem.getName());
                // You can perform additional actions here, such as adding the item to the cart
            } else {
                System.out.println("No item selected.");
            }
        });
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
}
