#  Object-Oriented Inventory Management Application 
Inventory Management System

This project is an Inventory Management System developed using Java and JavaFX. It allows users to manage parts and products, including adding, modifying, deleting, and searching for parts and products.

Features

Add, modify, and delete parts and products
Search functionality for parts and products
Confirmation dialogs for delete and exit actions
Error handling for invalid actions
Getting Started

Prerequisites
Java Development Kit (JDK) 8 or later
JavaFX SDK
Integrated Development Environment (IDE) such as IntelliJ IDEA, Eclipse, or NetBeans
Installation
Clone the repository to your local machine:
bash
Copy code
git clone https://github.com/your-username/inventory-management-system.git
Open the project in your preferred IDE.
Add the JavaFX library to your project. Refer to your IDE's documentation for instructions.
Usage

Run the Main class to start the application.
The main menu will display tables for parts and products.
Use the buttons to add, modify, or delete parts and products.
Use the search fields to find specific parts or products.
Code Overview

MainMenu.java
This class serves as the controller for the main menu. It handles the user interactions and manages the data displayed in the tables.

Methods

onClickExit(ActionEvent actionEvent): Exits the application after user confirmation.
onClickModifyPart(ActionEvent actionEvent): Loads the modify part controller.
onClickAddPart(ActionEvent actionEvent): Loads the add part controller.
onClickDeletePart(ActionEvent actionEvent): Deletes the selected part after user confirmation.
onClickDeleteProduct(ActionEvent actionEvent): Deletes the selected product after user confirmation.
onClickModifyProduct(ActionEvent actionEvent): Loads the modify product controller.
onClickAddProduct(ActionEvent actionEvent): Loads the add product controller.
initialize(URL url, ResourceBundle rb): Initializes the main menu controller and populates the part and product tables.
initialize(): Sets up listeners for the search fields.
OnActionSearchProductField(ActionEvent actionEvent): Handles the search for products.
OnActionSearchPartsField(ActionEvent actionEvent): Handles the search for parts.
searchPartTxtInput(String newText): Handles the input from the search part field.
searchProductTxtInput(String newText): Handles the input from the search product field.
showAlert(String title, String headerText): Displays an error alert.







