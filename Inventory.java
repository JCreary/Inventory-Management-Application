package com.example.c482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The inventory class manages the parts and products in the inventory system.
 *
 * @author Jamal Creary
 */

public class Inventory {
    /**
     * The list of all parts in the inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * The list of all products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart Adds part to inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct Adds product to inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Observable List
     *
     * @return Returns all parts in inventory.
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Observable List
     *
     * @return Returns all product in inventory.
     */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Enhanced for loop created to iterate through all parts and return part if found.
     *
     * @param partId
     * @return Returns the part if it is found and null if an item was not found.
     */

    public static Part lookupPart(int partId) {
        Part searchForPartId = null;
        for (Part part : getAllParts()) {
            // If statement checks to see if the part's ID contains the specified part ID.
            if (String.valueOf(part.getId()).contains(String.valueOf(partId))) {
                searchForPartId = part;
                break;
            }
        }
        return searchForPartId;
    }

    /**
     * LookupPartName Creates an ObservableList.
     * Enhanced for loop created to iterate through all parts and return part if found.
     *
     * @param partName
     * @return PartNameSearchOutcome(filtered parts)
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partNameSearchOutcome = FXCollections.observableArrayList();
        for (Part part : allParts) {
            // Checks if the part's name contains the specified part name (case-insensitive).
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                partNameSearchOutcome.add(part);
            }
        }
        return partNameSearchOutcome;
    }

    /**
     * Enhanced for loop created to iterate all products and return product if found.
     *
     * @param productId
     * @return Returns the product if it is found and null if an item was not found.
     */
    public static Product lookupProduct(int productId) {
        Product searchForProductId = null;
        for (Product product : getAllProducts()) {
            if (String.valueOf(product.getId()).contains(String.valueOf(productId))) {
                searchForProductId = product;
                break;
            }
        }
        return searchForProductId;
    }

    /**
     * LookupPartName creates an ObservableList.
     * Enhanced for loop created to iterate through all products and return product if found.
     *
     * @param productName
     * @return ProductNameSearchOutcome(filtered products).
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productNameSearchOutcome = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            // Checks if the product's name contains the specified product name (case-insensitive).
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                productNameSearchOutcome.add(product);
            }
        }
        return productNameSearchOutcome;
    }

    /**
     * Updates the part in Inventory.
     * Enhanced for loop created to iterate through all parts in inventory and update the part.
     *
     * @param selectedPart
     */
    public static void updatePart(Part selectedPart) {
        int index = -1;
        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == selectedPart.getId()) {
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }

    /**
     * Updates the product in Inventory.
     * Enhanced for loop created to iterate through all products in inventory and update the product.
     *
     * @param newProduct
     */
    public static void updateProduct(Product newProduct) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            index++;
            if (product.getId() == newProduct.getId()) {
                Inventory.getAllProducts().set(index, newProduct);
            }
        }
    }

    /**
     * Deletes the selected part from the list.
     *
     * @param selectedPart
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes the selected product from the list.
     *
     * @param selectedProduct
     */

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
}