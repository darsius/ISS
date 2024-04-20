package com.example.proiecto.Controller;

import com.example.proiecto.DAO.ItemDAO;
import com.example.proiecto.Model.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;

public class Menu {

    @FXML
    TableView<Item> allDoughnutsTable;

    @FXML private TextField doughnutNameField;
    @FXML private TextField doughnutPriceField;
    @FXML private TextArea doughnutDescriptionField;

    @FXML private Button addItem;
    ObservableList<Item> doughnutsList;
    private ItemDAO itemDAO = new ItemDAO();

    public void initialize() {
        ItemDAO itemDAO = new ItemDAO();
        doughnutsList = itemDAO.getAllItems();

        allDoughnutsTable.setItems(doughnutsList);

        TableColumn<Item, String> colName = (TableColumn<Item, String>) allDoughnutsTable.getColumns().get(0);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, BigDecimal> colPrice = (TableColumn<Item, BigDecimal>) allDoughnutsTable.getColumns().get(1);
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, String> colDescription = (TableColumn<Item, String>) allDoughnutsTable.getColumns().get(2);
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        allDoughnutsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTextFields(newSelection);
            }
        });

        allDoughnutsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            addItem.setDisable(newSelection != null);
        });

        // Handle mouse click to detect and clear selection if clicking outside of rows
        allDoughnutsTable.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // Move up through the node hierarchy until a TableRow or null is found
            while (node != null && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            // Clear selection if clicking on empty space
            if (node == null || (node instanceof TableRow && ((TableRow<?>) node).isEmpty())) {
                allDoughnutsTable.getSelectionModel().clearSelection();
            }
        });
    }

    private void updateTextFields(Item selectedDoughnut) {
        doughnutNameField.setText(selectedDoughnut.getName());
        doughnutPriceField.setText(selectedDoughnut.getPrice().toString());
        doughnutDescriptionField.setText(selectedDoughnut.getDescription());
    }

    @FXML
    private void addItemToMenu(ActionEvent event) {
        try {
            String name = doughnutNameField.getText();
            BigDecimal price = new BigDecimal(doughnutPriceField.getText());
            String description = doughnutDescriptionField.getText();

            Item newItem = new Item();
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setDescription(description);

            if (validateNewItem(newItem)) {
                int addResult = itemDAO.addData(newItem);
                if (addResult > 0) {
                    doughnutsList.add(newItem);
                    allDoughnutsTable.refresh();
                    clearTextFields();
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


    private boolean validateNewItem(Item item) {
        return true;
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
    private void clearTextFields() {
        doughnutNameField.clear();
        doughnutPriceField.clear();
        doughnutDescriptionField.clear();
        allDoughnutsTable.getSelectionModel().clearSelection();
    }






}
