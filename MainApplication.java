package com.example.c482;

import com.example.c482.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The mainapplication class creates an application for the inventory management system project and adds the sample data below to the
 * parts table and the product table.
 * JAVADOCS folder location: In this project under the src folder.
 * FUTURE ENHANCEMENTS: In the future I would use AutoCompleteTextFields in place of all the regular Textfields within the project.
 * AutoCompleteTextfields would increase the usability for the user. The process of data input would be much more efficient and quicker as the
 * AutoCompleteTextfield offers the user auto-complete suggestions which also increases accuracy especially when searching for an item. In addition
 * to adding the AutoCompleteTextfield, I would implement mySQL to incorporate a database with customer information. As the business grows,
 * a relational database management system will likely be needed.
 *
 * @author Jamal Creary
 */

public class MainApplication extends Application {

    /**
     * The start method of the Main Application class initializes and loads the mainscreen.fxml.
     *
     * @param stage
     * @throws IOException from FXMLLoader
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method loads sample and launches the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        //Three new parts are created.
        Part part1 = new InHouse(101, "Hammer", 9.99, 15, 1, 15, 123456);
        Part part2 = new InHouse(102, "Screwdriver", 4.99, 50, 1, 75, 555555);
        Part part3 = new Outsourced(103, "Wrench", 3.99, 2, 1, 75, "Pro Tools");

        //Three new parts are created.
        Product product1 = new Product(1001, "Toolbox", 199.99, 4, 1, 15);
        Product product2 = new Product(1002, "Spare case", 19.99, 2, 1, 5);
        Product product3 = new Product(1003, "Auto kit", 39.99, 9, 1, 10);

        //Parts are added to the inventory.
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        //Products are added to the inventory.
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch(args);
    }
}