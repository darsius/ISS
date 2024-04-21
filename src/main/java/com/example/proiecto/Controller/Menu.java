package com.example.proiecto.Controller;

import com.example.proiecto.DAO.ItemDAO;
import com.example.proiecto.Model.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.util.List;

public class Menu {

    @FXML
    TableView<Item> allDoughnutsTable;
    @FXML
    TableView<Item> doughnutsMenuTable;

    @FXML private TextField doughnutNameField;
    @FXML private TextField doughnutPriceField;
    @FXML private TextArea doughnutDescriptionField;

    @FXML private Button addItem;
    @FXML private Button addToMenuButton;
    private ObservableList<Item> doughnutsList = FXCollections.observableArrayList();

    private ObservableList<Item> doughnutsMenuList = FXCollections.observableArrayList();

    private ItemDAO itemDAO = new ItemDAO();

    public Menu() {
    }

    public Menu(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void initialize() {
        // Load items only meant for allDoughnutsTable and doughnutsMenuTable
        loadItems();

        setupTableColumns(allDoughnutsTable);
        setupTableColumns(doughnutsMenuTable);

        allDoughnutsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            addToMenuButton.setDisable(newSelection == null);
            if (newSelection != null) {
                updateTextFields(newSelection);
            }
        });

        // Handle mouse click to detect and clear selection if clicking outside of rows
        allDoughnutsTable.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> clearSelectionOnClickOutside(evt));
        doughnutsMenuTable.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> clearSelectionOnClickOutside(evt));
    }

    private void loadItems() {
        doughnutsList.clear();
        doughnutsMenuList.clear();

        List<Item> allItems = itemDAO.getAllItems();

        doughnutsList = FXCollections.observableArrayList();
        doughnutsMenuList = FXCollections.observableArrayList();

        for (Item item : allItems) {
            if (!item.isInMenu()) {
                doughnutsList.add(item);
            } else {
                doughnutsMenuList.add(item);
            }
        }

        allDoughnutsTable.setItems(doughnutsList);
        doughnutsMenuTable.setItems(doughnutsMenuList);
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

    private void updateTextFields(Item selectedDoughnut) {
        doughnutNameField.setText(selectedDoughnut.getName());
        doughnutPriceField.setText(selectedDoughnut.getPrice().toString());
        doughnutDescriptionField.setText(selectedDoughnut.getDescription());
    }

    private void clearSelectionOnClickOutside(MouseEvent evt) {
        Node node = evt.getPickResult().getIntersectedNode();
        while (node != null && !(node instanceof TableRow)) {
            node = node.getParent();
        }
        if (node == null || (node instanceof TableRow && ((TableRow<?>) node).isEmpty())) {
            allDoughnutsTable.getSelectionModel().clearSelection();
            doughnutsMenuTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void addItemToAllDoughnutsTable(ActionEvent event) {
        try {
            String name = doughnutNameField.getText();
            BigDecimal price = new BigDecimal(doughnutPriceField.getText());
            String description = doughnutDescriptionField.getText();

            Item newItem = new Item();
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setDescription(description);
            newItem.setInMenu(false); // Ensure this is set appropriately

            if (validateNewItem(newItem)) {
                int addResult = itemDAO.addData(newItem);

                if (addResult > 0) {
                    doughnutsList.add(newItem); // Make sure doughnutsList is the ObservableList linked to allDoughnutsTable
                    allDoughnutsTable.refresh(); // This should not be necessary if items are properly observed
                    System.out.println("Item added successfully to the database.");
                } else {
                    System.out.println("Error: Item could not be added to the database.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Price must be a valid number.");
        } catch (Exception e) {
            System.out.println("Error: Unable to add the item to the database.");
            e.printStackTrace();
        }
    }


    @FXML
    private void addItemToMenu(ActionEvent event) {

        Item selectedItem = allDoughnutsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setInMenu(true);
                int addResult = itemDAO.updateData(selectedItem);
                if (addResult > 0) {
                    doughnutsMenuList.add(selectedItem);
                    doughnutsList.remove(selectedItem);
                    allDoughnutsTable.refresh(); // This should not be necessary if items are properly observed
                    clearTextFields();
                    System.out.println("Item updated successfully to the database.");
                } else {
                    System.out.println("Error: Item could not be added to the database.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Price must be a valid number.");
            } catch (Exception e) {
                System.out.println("Error: Unable to add the item to the database.");
                e.printStackTrace();
            }
        }
    }


    private boolean validateNewItem(Item item) {
        return true;
//        return item.getName() != null && !item.getName().isEmpty() && item.getPrice() != null && item.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    @FXML
    private void updateItem(ActionEvent event) {
        Item selectedItem = allDoughnutsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setName(doughnutNameField.getText());
                selectedItem.setPrice(new BigDecimal(doughnutPriceField.getText()));
                selectedItem.setDescription(doughnutDescriptionField.getText());

                int updateResult = itemDAO.updateData(selectedItem);

                if (updateResult > 0) {
                    System.out.println("Item updated successfully.");
                    allDoughnutsTable.refresh(); // Refresh the TableView
                    // Clear the selection or the text fields after update
                    allDoughnutsTable.getSelectionModel().clearSelection();
                    clearTextFields();
                } else {
                    System.out.println("Error: Item could not be updated.");
                }
            } catch (NumberFormatException e) {
                // Handle invalid number format for price
                System.out.println("Error: Price must be a valid number.");
            } catch (Exception e) {
                // Handle other exceptions
                System.out.println("Error: Unable to update the item.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void removeItem() {
        Item selectedItem = allDoughnutsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Platform.runLater(() -> {
                int updateResult = itemDAO.deleteData(selectedItem);
                if (updateResult > 0) {
                    doughnutsList.remove(selectedItem);
                    doughnutsMenuList.removeIf(item -> item.getId() == selectedItem.getId());

                    System.out.println("Item deleted successfully.");
                } else {
                    System.out.println("Error: Item could not be deleted.");
                }
                loadItems(); // Refresh the table views with updated data
                clearTextFields();
            });
        }
    }

    @FXML
    private void clearTextFields() {
        doughnutNameField.clear();
        doughnutPriceField.clear();
        doughnutDescriptionField.clear();
    }

    @FXML
    private void removeItemFromMenu(ActionEvent event) {
        Item selectedItem = doughnutsMenuTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setInMenu(false);
            int updateResult = itemDAO.updateData(selectedItem);
            if (updateResult > 0) {
                doughnutsMenuList.remove(selectedItem);
                doughnutsList.add(selectedItem);

                allDoughnutsTable.refresh();
                doughnutsMenuTable.refresh();
                clearTextFields();
                System.out.println("Item removed from the menu.");
            } else {
                System.out.println("Failed to update item status.");
            }
        }
    }
}