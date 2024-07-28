package com.example.c482;

import com.example.c482.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class creates a controller that allows for the user to add parts.
 *
 * @author Jamal Creary
 */

public class AddPartMenu implements Initializable {

    /**
     * This variable represents the starting value for generating unique identifiers
     * for new parts in the inventory management system.
     * <p>
     * The value is initialized to 103 as a starting point.
     */

    private static int partId = 103;
    public Text macCompLbl;
    public TextField addPartIdText;
    public ToggleGroup addPartTg;
    public Text addPartMenuLbl;
    Stage stage;
    Object scene;

    @FXML
    private RadioButton addPartInHouseRbutton;
    @FXML
    private RadioButton addPartOutsourcedRbutton;
    @FXML
    private TextField addPartNameText;
    @FXML
    private TextField addPartInvText;
    @FXML
    private TextField addPartPriceText;
    @FXML
    private TextField addPartMaxText;
    @FXML
    private TextField addPartMacCompText;
    @FXML
    private TextField addPartMinText;


    /**
     * On click of radio button, the text is changed to Machine ID.
     *
     * @param actionEvent
     */
    @FXML
    public void onClickMachine(ActionEvent actionEvent) {
        macCompLbl.setText("Machine ID");
    }

    /**
     * On click of radio button, the text is changed to Company Name.
     *
     * @param actionEvent
     */
    @FXML
    public void onClickCompany(ActionEvent actionEvent) {
        macCompLbl.setText("Company Name");
    }

    /**
     * On click of button, the part is saved.
     * <p>
     * RUNTIME ERROR: A runtime error was received within the initial phases of the project as I did not have
     * any exception handling logic incorporated. Prior to adding the try catch statement, I received NumberFormatExceptions
     * when entering an incorrect number format to the stock, price, max and min fields. The catch statement displays error
     * message if an incorrect number format is entered which prevents further execution in case of an exception.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickAddPartSave(ActionEvent actionEvent) throws IOException {

        try {
            int id = Integer.parseInt(addPartIdText.getText());
            String name = addPartNameText.getText();
            int stock, max, min;
            double price;
            int machineId;
            String companyName;

            //If statement checks to see if the name is empty and displays an error alert if the name is empty then returns to Add part menu.
            if (name.isEmpty()) {
                showAlert("Error", "Please enter a valid Name.");
                return;
            }

            /**
             *
             * This try-catch block attempts to parse and retrieve integer values for stock, max, and min,
             * and a double value for price from corresponding text fields.
             * If parsing fails an alert is displayed.
             */

            try {
                stock = Integer.parseInt(addPartInvText.getText());
                price = Double.parseDouble(addPartPriceText.getText());
                max = Integer.parseInt(addPartMaxText.getText());
                min = Integer.parseInt(addPartMinText.getText());
            } catch (NumberFormatException ee) {
                showAlert("Error", "Inv, Max, and Min must be integers. In addition, the Price field must be a double.");
                return;
            }
            //If statement checks to see if the max value is less than min value and displays an error alert if min value is greater than max value.
            if (max < min) {
                showAlert("Error", "Min value should be less than Max value.");
                return;
            }
            // If statement checks to see if the inv value is between min and max value and displays an error alert if inv value is outside threshold.
            if (stock < min || stock > max) {
                showAlert("Error", "Value of Inv should be in between the Min and Max value.");
                return;
            }
            // Try catch block created to check if the InHouse radio button is selected and adds part to inventory in the proper number format.
            if (addPartInHouseRbutton.isSelected()) {
                try {
                    machineId = Integer.parseInt(addPartMacCompText.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter a valid Machine ID. The Machine ID should be an integer.");
                    return;
                }

                // Try catch block created to check if the Outsourced radio button is selected and adds part to inventory. Displays error if Company Name is empty.
            } else if (addPartOutsourcedRbutton.isSelected()) {
                try {
                    companyName = String.valueOf(addPartMacCompText.getText());

                    if (companyName.isEmpty()) {
                        showAlert("Error", "Please enter a valid Company Name.");
                        return;
                    }
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                } catch (Exception ee) {
                    showAlert("Error", "Please enter a valid Company Name.");
                    return;
                }
            }

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each Text Field!");
            alert.showAndWait();
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
     * On click of button, the user is redirected to the Main Menu.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickAddPartCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }

    /**
     * This method increments the partId by one and returns the updated value.
     * It provides a unique identifier for the new part in the inventory.
     *
     * @return returns the unique partId representing the new part.
     */
    public static int getPartId() {
        partId++;
        return partId;
    }

    /**
     * Initializes the controller and sets in-house radio button to true.
     *
     * @param url            The location used to resolve relative paths for the root object or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartInHouseRbutton.setSelected(true);
        partId = getPartId();
        addPartIdText.setText(String.valueOf(partId));
    }
}
