package com.example.c482;

import com.example.c482.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import com.example.c482.Model.Product.*;

/**
 * This class allows for the user to modify products.
 *
 * @author Jamal Creary
 */

public class ModifyProductMenu implements Initializable {
    public TableColumn removePartIdColMp;
    public TableColumn modifyPartNameColMp;
    public TableColumn modifyPartInventoryLevelColMp;
    public TableColumn modifyPartPriceColMp;
    public TextField searchForPartInModifyProductMenu;
    public TableColumn addPartIdColMp;
    public TableColumn addPartNameColMp;
    public TableColumn addPartInventoryLevelColMp;
    public TableColumn addPartPriceColMp;
    public TableView modifyProductRemoveAssociatedPartTableview;
    public TextField modifyProductIdText;
    public TextField modifyProductNameText;
    public TextField modifyProductInvText;
    public TextField modifyProductPriceText;
    public TextField modifyProductMaxText;
    public TextField modifyProductMinText;
    public TableView modifyProductAddPartTableview;
    Stage stage;
    Parent scene;
    private Product product = new Product();

    /**
     * On click of button, the modified product is saved.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickModifyProductSave(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(modifyProductIdText.getText());
            String name = modifyProductNameText.getText();
            int stock, max, min;
            double price;
            //If statement checks to see if the name field is empty and displays an error alert if the name is empty then returns to Modify Product Menu.
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a Name.");
                alert.show();
                return;
            }
            /**
             *
             * This try-catch block attempts to parse and retrieve integer values for stock, max, and min,
             * and a double value for price from corresponding text fields.
             * If parsing fails an error alert is displayed.
             */
            try {
                stock = Integer.parseInt(modifyProductInvText.getText());
                price = Double.parseDouble(modifyProductPriceText.getText());
                max = Integer.parseInt(modifyProductMaxText.getText());
                min = Integer.parseInt(modifyProductMinText.getText());
            } catch (NumberFormatException ee) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv, Max, and Min must be integers. In addition, the Price field must be a double.");
                alert.show();
                return;
            }
            //If statement checks to see if the max value is less than min value and displays an error alert if min value is greater than max value.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value should be less than the Max value.");
                alert.show();
                return;
                //Else if statement checks if inv value is between min and max value and displays an error alert if inv value is outside threshold.
            } else if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Value of Inv should be in between the Min and Max value.");
                alert.showAndWait();
                return;
            }

            //The pr variable represents a newly instantiated instance of the Product class. The specific parameters (id, name, price, stock, min, max) is added/updated to the inventory.
            Product pr = new Product(id, name, price, stock, min, max);
            Inventory.updateProduct(pr);

            //Enhanced for loop iterates through associated parts of the default Product then adds each associated part to the newly created Product.
            for (Part part : product.getAllAssociatedParts()) {
                pr.addAssociatedPart(part);
            }
            //Returns to main menu controller.
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid value for each Text Field!");
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
     * Initiates a search based on the partId or part name. In addition, the search result is returned in the table.
     *
     * @param actionEvent Initiates the start of the search.
     */
    public void partIDNameSearchMP(ActionEvent actionEvent) {
        //Gets all parts from the inventory.
        ObservableList<Part> allParts = Inventory.getAllParts();
        //Observable list stores parts returned from search.
        ObservableList<Part> partsReturned = FXCollections.observableArrayList();
        String searchString = searchForPartInModifyProductMenu.getText();
        //Enhanced loop iterates through all parts and checks if the part's name contains the specified non-case-sensitive part name.
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).toLowerCase().contains(searchString) ||
                    part.getName().toLowerCase().contains(searchString)) {
                partsReturned.add(part);
            }
        }
        //If the part search has 0 results then an alert message is shown.
        modifyProductAddPartTableview.setItems(partsReturned);
        if (partsReturned.size() == 0) {
            showAlert("No Match", "Unable to locate part");
        }
        //If search field is empty, all parts will be shown.
        if (searchForPartInModifyProductMenu.getText().isEmpty()) {
            modifyProductAddPartTableview.setItems(Inventory.getAllParts());
        }
    }

    /**
     * On click of button, the user is redirected to the Main Menu.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickModifyProductCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes the associated part from the observable list/table.
     *
     * @param actionEvent
     */
    public void onClickRemoveAssociatedPartMP(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) modifyProductRemoveAssociatedPartTableview.getSelectionModel().getSelectedItem();

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
                modifyProductRemoveAssociatedPartTableview.getItems().remove(selectedAssociatedPart);
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
    public void onClickAddAssociatedPartMP(ActionEvent actionEvent) {
        Part partSelected = (Part) modifyProductAddPartTableview.getSelectionModel().getSelectedItem();
        //If an associated part is selected and the valid product fields are completed, the associated part will be added to the product.
        if (partSelected != null && product != null) {
            modifyProductRemoveAssociatedPartTableview.getItems().add(partSelected);
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
     * Obtains product information from the main menu screen.
     *
     * @param productModified
     */
    public void sendProduct(Product productModified) {
        modifyProductIdText.setText(String.valueOf(productModified.getId()));
        modifyProductNameText.setText(productModified.getName());
        modifyProductInvText.setText(String.valueOf(productModified.getStock()));
        modifyProductPriceText.setText(String.valueOf(productModified.getPrice()));
        modifyProductMaxText.setText(String.valueOf(productModified.getMax()));
        modifyProductMinText.setText(String.valueOf(productModified.getMin()));
        modifyProductRemoveAssociatedPartTableview.setItems(productModified.getAllAssociatedParts());

        //Enhanced for loop iterates through associated parts of the specified Product then adds each associated part to the current Product.
        for (Part part : productModified.getAllAssociatedParts()) {
            product.addAssociatedPart(part);
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
        addPartIdColMp.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColMp.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInventoryLevelColMp.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceColMp.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAddPartTableview.setItems(Inventory.getAllParts());

        removePartIdColMp.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameColMp.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyPartInventoryLevelColMp.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyPartPriceColMp.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

