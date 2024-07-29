#  Inventory Management Application 

Overview 

This project is an Inventory Management System that provides a user-friendly interface to manage parts and products. The system allows users to add, modify, search, and delete parts and products within an inventory. The application is developed using Java and JavaFX for the front-end interface.

Features

Add Part/Product: Allows the user to add new parts and products to the inventory.
Modify Part/Product: Enables the user to modify existing parts and products.
Delete Part/Product: Permits the user to delete selected parts and products.
Search Part/Product: Provides search functionality to find specific parts and products by ID or name.
Exit Application: Allows the user to exit the application with a confirmation prompt.
Project Structure

The project follows the Model-View-Controller (MVC) design pattern:

Model: Contains the data classes Part, Product, and Inventory.
View: Consists of FXML files for the user interface layout.
Controller: Contains Java classes that handle user interactions and business logic.
Getting Started

Prerequisites
Java Development Kit (JDK) 8 or higher
JavaFX SDK
IDE (e.g., IntelliJ IDEA, Eclipse)
Installation
Clone the repository:
bash
Copy code
git clone https://github.com/your-username/inventory-management-system.git
Open the project in your IDE:
Import the project as a Maven project or configure the JavaFX libraries manually.
Run the application:
Navigate to the Main class and run it as a Java application.
Usage
Launch the application:
The main menu will be displayed, showing the parts and products tables.
Add Part/Product:
Click the "Add" button to open the add part/product form.
Fill in the required fields and click "Save" to add the part/product to the inventory.
Modify Part/Product:
Select a part/product from the table.
Click the "Modify" button to open the modify part/product form.
Make the necessary changes and click "Save" to update the part/product.
Delete Part/Product:
Select a part/product from the table.
Click the "Delete" button and confirm the deletion in the prompt.
Search Part/Product:
Enter the part/product name or ID in the search bar.
The table will update to show the matching results.
Exit Application:
Click the "Exit" button.
Confirm the exit in the prompt to close the application.






