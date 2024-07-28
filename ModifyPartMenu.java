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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class allows for the user to modify parts.
 *
 * @author Jamal Creary
 */

public class ModifyPartMenu implements Initializable {
    public TextField modifyPartMinText;
    public TextField modifyPartMacCompText;
    public TextField modifyPartMaxText;
    public TextField modifyPartPriceText;
    public TextField modifyPartInvText;
    public TextField modifyPartNameText;
    public TextField modifyPartIdText;
    public RadioButton modifyPartInHouseRbutton;
    public ToggleGroup modifyPartTg;
    public RadioButton modifyPartOutsourcedRbutton;

    Stage stage;
    Parent scene;
    public Text macCompLbl;

    /**
     * On click of radio button, the text is changed to Company Name.
     *
     * @param actionEvent
     */
    public void onClickCompany(ActionEvent actionEvent) {
        macCompLbl.setText("Company Name");
    }

    /**
     * On click of radio button, the text is changed to Machine ID.
     *
     * @param actionEvent
     */

    public void onClickMachine(ActionEvent actionEvent) {
        macCompLbl.setText("Machine ID");
    }

    /**
     * Initializes the controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Obtains part information from the main menu screen.
     *
     * @param partModified
     */
    public void sendPart(Part partModified) {
        modifyPartIdText.setText(String.valueOf(partModified.getId()));
        modifyPartNameText.setText(partModified.getName());
        modifyPartInvText.setText(String.valueOf(partModified.getStock()));
        modifyPartPriceText.setText(String.valueOf(partModified.getPrice()));
        modifyPartMaxText.setText(String.valueOf(partModified.getMax()));
        modifyPartMinText.setText(String.valueOf(partModified.getMin()));

        //If the Inhouse radio button was initially selected the machineID will be retrieved.
        if (partModified instanceof InHouse) {
            modifyPartInHouseRbutton.setSelected(true);
            macCompLbl.setText("Machine ID");
            modifyPartMacCompText.setText(String.valueOf(((InHouse) partModified).getMachineId()));
            //If the Outsourced radio button was initially selected the Company Name will be retrieved.
        } else if (partModified instanceof Outsourced) {
            modifyPartOutsourcedRbutton.setSelected(true);
            macCompLbl.setText("Company Name");
            modifyPartMacCompText.setText(((Outsourced) partModified).getCompanyName());
        }
    }

    /**
     * On click of button, the part is saved if formatted values are valid.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    @FXML
    public void onClickAddPartSave(ActionEvent actionEvent) throws IOException {
        //This try-catch block attempts to parse and retrieve valid values for int, name, stock, max, min, price, machineID/ Company Name.
        try {
            int id = Integer.parseInt(modifyPartIdText.getText());
            String name = modifyPartNameText.getText();
            int stock, max, min;
            double price;
            int machineId;
            String companyName;

            //If statement checks to see if the name is empty and displays an error alert if it is empty then returns user to the modify part menu.
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a Name.");
                alert.show();
                return;
            }
            try {
                stock = Integer.parseInt(modifyPartInvText.getText());
                price = Double.parseDouble(modifyPartPriceText.getText());
                max = Integer.parseInt(modifyPartMaxText.getText());
                min = Integer.parseInt(modifyPartMinText.getText());
            } catch (NumberFormatException ee) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv, Max, and Min must be integers. In addition, the Price field must be a double.");
                alert.show();
                return;
            }
            //If statement checks if max value is less than min value and displays an error alert if min value is greater than max value.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value should be less than Max value.");
                alert.show();
                return;
                //If statement checks if inv value is between min and max value and displays an error alert if inv value is outside threshold.
            } else if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Value of Inv should be in between the Min and Max value.");
                alert.showAndWait();
                return;
                //Else if statement checks if name is empty and displays an error alert if the name is empty or has no input then returns to add part menu.
            } else if (name == null && name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid name.");
                alert.showAndWait();
                return;
            }
            //Try catch block created to check if the InHouse radio button is selected and updates the modified part in the inventory in the proper number format.
            if (modifyPartInHouseRbutton.isSelected()) {
                try {
                    machineId = Integer.parseInt(modifyPartMacCompText.getText());
                    InHouse inHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(inHousePart);
                    //Alert is displayed if the machine Id is not entered/valid.
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Machine ID. The Machine ID should be an integer.");
                    alert.showAndWait();
                    return;
                }
                //Try catch block created to check if the Outsourced radio button is selected and updates modified part to the inventory. Displays error if Company Name is empty.
            } else if (modifyPartOutsourcedRbutton.isSelected()) {
                try {
                    companyName = String.valueOf(modifyPartMacCompText.getText());
                    //Alert is created if the company name is empty.
                    if (companyName.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Company Name.");
                        alert.show();
                        return;
                    }
                    Outsourced outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(outsourcedPart);
                } catch (Exception ee) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Company Name.");
                    alert.show();
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
     * On click of button, the user is redirected to the Main Menu.
     *
     * @param actionEvent
     * @throws IOException from FXMLLoader
     */
    public void onClickModifyPartCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
}
