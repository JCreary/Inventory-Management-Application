package com.example.c482;

import com.example.c482.Model.Inventory;
import com.example.c482.Model.Part;
import com.example.c482.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.c482.Model.Product.*;

/**
 * This class creates a controller that allows for the user to add products.
 *
 * @author Jamal Creary
 */


public class AddProductMenu implements Initializable {

    /**
     * This variable represents the starting value for generating unique identifiers
     * for new products in the inventory management system.
     * The value is initialized to 1003 as a starting point.
     */
    private static int productId = 1003;
    public TableView addProductRemovePartTableview;
    @FXML
    private TableView addProductAddPartTableview;
    @FXML
    private TableColumn removeProductPartIDCol;
    @FXML
    private TableColumn removeProductPartNameCol;
    @FXML
    private TableColumn removeProductInventoryLevelCol;
    @FXML
    private TableColumn removeProductPriceCol;
    @FXML
    private TableColumn addProductPartIDCol;
    @FXML
    private TableColumn addProductPartNameCol;
    @FXML
    private TableColumn addProductInventoryLevelCol;
    @FXML
    private TableColumn addProductPriceCol;
    @FXML
    private TextField addProductIdText;
    @FXML
    private TextField addProductNameText;
    @FXML
    private TextField addProductInvText;
    @FXML
    private TextField addProductPriceText;
    @FXML
    private TextField addProductMaxText;
    @FXML
    private TextField addProductMinText;
    @FXML
    private TextField searchForPartInAddProductMenu;
    Stage stage;
    Parent scene;

    /**
     * This global variable serves as a starting point/ default container
     * to add products/parts to list.
     */
    private Product product = new Product();

    /**
     * Removes the associated part from the observable list/table.
     *
     * @param actionEvent
     */
    public void onClickRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) addProductRemovePartTableview.getSelectionModel().getSelectedItem();

        //If an associated part is selected, the user will receive a confirmation dialog box.
        if (selectedAssociatedPart != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Associated Part Removal");
            alert.setHeaderText("Remove Part");
            alert.setContentText("Select Ok to remove the selected associated part?");
            Optional<ButtonType> result = alert.showAndWait();

            //If user selects OK in the confirmation dialog, the selected associated part is removed from the table.
            if (result.isPresent() && result.get() == ButtonType.OK) {
                addProductRemovePartTableview.getItems().remove(selectedAssociatedPart);
                product.deleteAssociatedPart(selectedAssociatedPart);
            } else {
                alert.close();
            }
            //If no associated part is selected, the user will receive an error message.
        } else {
            Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
            associatedPartExist.setTitle("Error Message");
            associatedPartExist.setContentText("No associated part was selected. Please select the associated part you wish to remove: ");
            associatedPartExist.show();
        }
    }

    /**
     * Adds the associated part from the observable list/table.
     *
     * @param actionEvent
     */
    public void onClickAddAssociatedPart(ActionEvent actionEvent) {
        Part partSelected = (Part) addProductAddPartTableview.getSelectionModel().getSelectedItem();
        //If an associated part is selected and the valid product fields are completed, the associated part will be added to the product.
        if (partSelected != null && product != null) {
            addProductRemovePartTableview.getItems().add(partSelected);
            product.addAssociatedPart(partSelected);
            //If no associated part is selected, the user will receive an error message.
        } else {
            Alert associatedPartExist = new Alert(Alert.AlertType.ERROR);
            associatedPartExist.setTitle("Error Message");
            associatedPartExist.setContentText("No associated part was selected. Please select an associated part to add: ");
            associatedPartExist.show();
        }
    }

    /**
     * On click of button, the user is redirected to the Main Menu.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickAddProductCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * On click of button, the product is saved.
     * RUNTIME ERROR: A runtime error was received within the initial phases of the project as I did not have
     * any exception handling logic incorporated. Prior to adding the try catch statement, I received NumberFormatExceptions
     * when entering an incorrect number format to the stock, price, max and min fields. The catch statement displays error
     * message if an incorrect number format is entered which prevents further execution in case of an exception.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */

    public void onClickAddProductSave(ActionEvent actionEvent) throws IOException {

        try {
            int id = Integer.parseInt(addProductIdText.getText());
            String name = addProductNameText.getText();
            int stock, max, min;
            double price;
            //If statement checks to see if the name field is empty and displays an error alert if the name is empty then returns to Add Product Menu.
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a Name.");
                alert.show();
                return;
            }
            /**
             *
             * This try-catch block attempts to parse and retrieve integer values for stock, max, and min,
             * and a double value for price from corresponding text fields.
             * If parsing fails, an alert is displayed.
             */
            try {
                stock = Integer.parseInt(addProductInvText.getText());
                price = Double.parseDouble(addProductPriceText.getText());
                max = Integer.parseInt(addProductMaxText.getText());
                min = Integer.parseInt(addProductMinText.getText());
            } catch (NumberFormatException ee) {
                showAlert("Error", "Inv, Max, and Min must be integers. In addition, the Price field must be a double.");
                return;
            }
            //If statement checks if max value is less than min value and displays an error alert if min value is greater than max value.
            if (max < min) {
                showAlert("Error", "Min value should be less than Max value.");
                return;
            }
            //If statement checks if inv value is between min and max value and displays an error alert if inv value is outside threshold.
            if (stock < min || stock > max) {
                showAlert("Error", "Value of Inv should be in between the Min and Max value.");
                return;
            }
            //The pr variable represents a newly instantiated instance of the Product class. The specific parameters (id, name, price, stock, min, max) is added to the inventory.
            Product pr = new Product(id, name, price, stock, min, max);
            Inventory.addProduct(pr);

            //Enhanced for loop iterates through associated parts of the default Product then adds each associated part to the newly created Product.
            for (Part part : product.getAllAssociatedParts()) {
                pr.addAssociatedPart(part);
            }
            //Returns to main menu.
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid value for each Text Field!");
        }
    }

    /**
     * Initializes the controller and populates table views.
     *
     * @param url            The location used to resolve relative paths for the root object or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductAddPartTableview.setItems(Inventory.getAllParts());

        removeProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeProductInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removeProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Gets the product ID and sets the product ID in the corresponding text field.
        productId = getProductId();
        addProductIdText.setText(String.valueOf(productId));

    }

    /**
     * Initiates a search based on the partId or part name. In addition, the search result is returned in the table.
     *
     * @param actionEvent Initiates the start of the search.
     */
    public void partIDNameSearch(ActionEvent actionEvent) {
        //Gets all parts from the inventory.
        ObservableList<Part> allParts = Inventory.getAllParts();
        //Observable list stores parts returned from search.
        ObservableList<Part> partsReturned = FXCollections.observableArrayList();
        String searchString = searchForPartInAddProductMenu.getText();
        //Enhanced for loop iterates through all parts and checks if the part's name contains the specified non-case-sensitive part name.
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).toLowerCase().contains(searchString) ||
                    part.getName().toLowerCase().contains(searchString)) {
                partsReturned.add(part);
            }
        }
        addProductAddPartTableview.setItems(partsReturned);

        //If the part search has 0 results then an alert message is shown.
        if (partsReturned.size() == 0) {
            showAlert("No Match", "Unable to locate part");
        }
        //If search field is empty, all parts will be shown.
        if (searchForPartInAddProductMenu.getText().isEmpty()) {
            addProductAddPartTableview.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method creates and shows an error alert with the given title
     * and header text.
     *
     * @param title      The title of the error alert.
     * @param headerText The header text of the error alert.
     */
    private void showAlert(String title, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    /**
     * This method increments the productId by one and returns the updated value.
     * It provides a unique identifier for the new part in the inventory.
     *
     * @return Returns the unique partId representing the new part.
     */
    public static int getProductId() {
        productId++;
        return productId;
    }
}