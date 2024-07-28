package com.example.c482;

import com.example.c482.Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the main menu controller that allows for the user to interact with the other controllers and models created.
 *
 * @author Jamal Creary
 */

public class MainMenu implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TextField searchPartTxt;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField searchProductTxt;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * On click and selecting the OK in the confirmation screen, the user exits the application.
     *
     * @param actionEvent
     */
    public void onClickExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm that you would like to exit program:");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Method loads the modify part controller.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickModifyPart(ActionEvent actionEvent) throws IOException {

        Part partSelectedToModify = partTableView.getSelectionModel().getSelectedItem();
        //If no part is selected, an error message is displayed.
        if (partSelectedToModify == null) {
            Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
            associatedPartExist.setTitle("Error Message");
            associatedPartExist.setContentText("No part was selected to modify. Please select a part to modify: ");
            associatedPartExist.show();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/c482/ModifyPartMenu.fxml"));
        loader.load();

        //Loads modify part menu controller.
        ModifyPartMenu ModifyPartMenuController = loader.getController();
        ModifyPartMenuController.sendPart(partTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method loads the add part controller.
     *
     * @param actionEvent
     * @throws IOException From FXMLLoader.
     */
    public void onClickAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method deletes the part selected by the user in the table.
     *
     * @param actionEvent
     */
    public void onClickDeletePart(ActionEvent actionEvent) {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        //If no part is selected, an error message is displayed.
        if (part == null) {
            Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
            associatedPartExist.setTitle("Error Message");
            associatedPartExist.setContentText("No part was selected to delete. Please select a part to delete: ");
            associatedPartExist.show();
            //If a part is selected, a confirmation message is displayed. Once the user selects OK, the part is deleted from the inventory.
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Part Removal");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
            } else {
                alert.close();
            }
        }
    }

    /**
     * Method deletes the product selected by the user in the table.
     *
     * @param actionEvent
     */
    public void onClickDeleteProduct(ActionEvent actionEvent) {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        //If no part is selected, an error message is displayed.
        if (product == null) {
            Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
            associatedPartExist.setTitle("Error Message");
            associatedPartExist.setContentText("No product was selected to delete. Please select a product to delete: ");
            associatedPartExist.show();
            //If a product is selected with no associated parts, a confirmation message is displayed. Once the user selects OK, the product is deleted from the inventory.
        } else {
            //if the product has associated parts, an error message is displayed.
            if (product.getAllAssociatedParts().size() > 0) {
                Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
                associatedPartExist.setTitle("Error Message");
                associatedPartExist.setContentText("There are associated parts attached to the selected product. Please remove the associated parts before you attempt to delete the product.");
                associatedPartExist.show();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Product Removal");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(product);
            }
        }
    }

    /**
     * Method loads the modify product that was selected by the user in the table.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickModifyProduct(ActionEvent actionEvent) throws IOException {
        Product productSelectedToModify = productTableView.getSelectionModel().getSelectedItem();
        //If no product is selected, an error message is displayed.
        if (productSelectedToModify == null) {
            Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
            associatedPartExist.setTitle("Error Message");
            associatedPartExist.setContentText("No product was selected to modify. Please select a product to modify: ");
            associatedPartExist.show();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/c482/ModifyProductMenu.fxml"));
        loader.load();

        //Loads modify product menu controller.
        ModifyProductMenu ModifyProductMenuController = loader.getController();
        ModifyProductMenuController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method loads the add product controller.
     *
     * @param actionEvent
     * @throws IOException From FXMLLoader.
     */
    public void onClickAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method initializes the main menu controller and populates the part and product table.
     *
     * @param url The location used to resolve relative paths for the root object or null if the location is not known.
     * @param rb  The resources used to localize the root object or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method initializes the search functionality.
     */
    public void initialize() {
        // Code sets up listener for searchPartTxt.
        searchPartTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            searchPartTxtInput(newValue);
        });
        // Code sets up listener for searchProductTxt.
        searchProductTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            searchProductTxtInput(newValue);
        });
    }

    /**
     * Method retrieves the text from the searchProductTxt field and invokes the searchProductTxtInput method.
     *
     * @param actionEvent
     */
    public void OnActionSearchProductField(ActionEvent actionEvent) {
        String searchProducts = searchProductTxt.getText();
        searchProductTxtInput(searchProducts);
    }

    /**
     * Method retrieves the text from the searchPartsTxt field and invokes the searchPartTxtInput method.
     *
     * @param actionEvent
     */
    public void OnActionSearchPartsField(ActionEvent actionEvent) {
        String searchParts = searchPartTxt.getText();
        searchPartTxtInput(searchParts);
    }

    /**
     * Method handles the input from the searchPartTxt field and displays an alert if no parts are found.
     *
     * @param newText
     */
    private void searchPartTxtInput(String newText) {
        //Retrieves all parts from Inventory.
        ObservableList<Part> allParts = Inventory.getAllParts();
        //If newText is empty all parts in the table is returned.
        if (newText.isEmpty()) {
            partTableView.setItems(allParts);
            return;
        }
        // Makes the search non-case-sensitive and creates a list to store the parts search results.
        String partSearchText = newText.toLowerCase();
        ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();

        // Used to search for the Part using the part ID.
        try {
            int partIdSearch = Integer.parseInt(partSearchText);
            Part partById = Inventory.lookupPart(partIdSearch);
            if (partById != null) {
                partsSearchResults.add(partById);
            }
            // Ignore the exception if the input is not a valid integer.
        } catch (NumberFormatException ignored) {
        }

        // Add results from lookupPartName and updates the table based on the search result.
        partsSearchResults.addAll(Inventory.lookupPart(partSearchText));
        partTableView.setItems(partsSearchResults);

        //if no parts is found, an alert is displayed.
        if (partsSearchResults.isEmpty()) {
            showAlert("No Match", "Unable to locate part");
        }
    }

    /**
     * Method handles the input from the searchProductTxt field and displays an alert if no parts are found.
     *
     * @param newText
     */
    private void searchProductTxtInput(String newText) {
        //Retrieves all products from Inventory.
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        //If newText is empty all products in the table is returned.
        if (newText.isEmpty()) {
            productTableView.setItems(allProducts);
            return;
        }
        // Makes the search non-case-sensitive and creates a list to store the products search results.
        String productSearchText = newText.toLowerCase();
        ObservableList<Product> productsSearchResults = FXCollections.observableArrayList();

        // Used to search for the Product using the product ID.
        try {
            int productIdSearch = Integer.parseInt(productSearchText);
            Product productById = Inventory.lookupProduct(productIdSearch);
            if (productById != null) {
                productsSearchResults.add(productById);
            }
            // Ignores the exception if the input is not a valid integer.
        } catch (NumberFormatException ignored) {
        }
        // Add results from lookupProductName and updates the table based on the search result.
        productsSearchResults.addAll(Inventory.lookupProduct(productSearchText));
        productTableView.setItems(productsSearchResults);

        //If no parts is found, an alert is displayed.
        if (productsSearchResults.isEmpty()) {
            showAlert("No Match", "Unable to locate product");
        }
    }

    /**
     * This method creates and shows an error alert with the given title and header text.
     *
     * @param title      The title of the error alert.
     * @param headerText The header text of the error alert.
     */
    public void showAlert(String title, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}

