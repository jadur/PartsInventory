/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author homie
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
    private static int partCount = 6;
    private static int productCount = 3;
    
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static Part lookupPart(int partID){
        int index = -1;
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partID){
                index = i;
            }
        }
        return allParts.get(index);
    }
    public static Product lookupProduct(int productID){
        int index = -1;
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productID){
                index = i;
            }
        }
        return allProducts.get(index);
        
    }
    public static ObservableList<Part> lookupPart(String partName){
        searchedParts.clear();
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName().equals(partName)){
                searchedParts.add(allParts.get(i));
                
            }
        }
        return searchedParts;
    }
    public static ObservableList<Product> lookupProduct(String productName){
        searchedProducts.clear();
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName().equals(productName)){
                searchedProducts.add(allProducts.get(i));
                
            }
        }
        return searchedProducts;
    }
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
        
    }
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        
    }
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public static int getPartCounter() {
        partCount++; //To change body of generated methods, choose Tools | Templates.
        return partCount;
    }
    
    public static int getProductCounter(){
        productCount++;
        return productCount;
    }
}
