/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author homie
 */
public class MainMenuController implements Initializable {

    @FXML
    private TextField partSearchBar;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> columnPartID;
    @FXML
    private TableColumn<Part, String> columnPartName;
    @FXML
    private TableColumn<Part, Integer> columnPartInvLvl;
    @FXML
    private TableColumn<Part, Double> columnPartPPU;
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private TextField productsSearchBar;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> columnProdID;
    @FXML
    private TableColumn<Product, String> columnProdName;
    @FXML
    private TableColumn<Product, Integer> columnProdInvLvl;
    @FXML
    private TableColumn<Product, Double> columnProdPPU;
    
    Stage stage;
    Parent scene;
    public static Part selectedPart;
    public static int selectedPartIndex;
    public static Product selectedProduct;
    public static int selectedProductIndex;
    private ObservableList<Part> searchParts = FXCollections.observableArrayList();
    private ObservableList<Product> searchProducts = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //PartTable
        columnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        //ProdTable
        columnProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProdInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProdPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
        //methods
        updatePartsTable();
        updateProductsTable();
        
    }    

    @FXML
    private void onActionSearchParts(ActionEvent event) {
        System.out.println("Search Part");
        searchParts.clear();
        
        try{
            try{
                int id = Integer.parseInt(partSearchBar.getText());
                searchParts.add(Inventory.lookupPart(id));
                
            }
            catch(Exception e){
                String name = partSearchBar.getText();
                searchParts = Inventory.lookupPart(name);
                
            }
            if(searchParts.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Part missing!");
                alert.showAndWait();
                
            }
            else{
                partsTableView.setItems(searchParts);
                columnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
                columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
                columnPartInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
                columnPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
                
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Part Missing!");
            alert.showAndWait();
            
        }
        
    }

    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        System.out.println("Add Part"); 
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();      
        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void onActionModifyPart(ActionEvent event) throws IOException {
        System.out.println("Modify Part");
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        selectedPartIndex = Inventory.getAllParts().indexOf(selectedPart);
        
        if(selectedPart != null){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ModifyPartMenu.fxml"));
            scene = loader.load();
            Scene view = new Scene(scene);
            stage.setScene(view);
            stage.show();
            
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Select a part!");
            alert.showAndWait();
            
        }
    }

    @FXML
    private void onActionDeletePart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
        
    }

    @FXML
    private void onActionSearchProducts(ActionEvent event) {
        searchProducts.clear();
        
        try{
            try{
                int id = Integer.parseInt(productsSearchBar.getText());
                searchProducts.add(Inventory.lookupProduct(id));
                
            }
            catch(Exception e){
                String name = productsSearchBar.getText();
                searchProducts = Inventory.lookupProduct(name);
                
            }
            if(searchProducts.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Product Missing!");
                alert.showAndWait();
            }
            else{
                productsTableView.setItems(searchProducts);
                columnProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
                columnProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
                columnProdInvLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
                columnProdPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
                
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Product or Part Missing!");
            alert.showAndWait();        
        }
        
    }

    @FXML
    private void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
                
    }

    @FXML
    private void onActionModifyProduct(ActionEvent event) throws IOException {
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        selectedProductIndex = Inventory.getAllProducts().indexOf(selectedProduct);
        
        if(selectedProduct != null){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ViewController/ModifyProductMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Please select a part!");
            alert.showAndWait();
            
        }
    }

    @FXML
    private void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
        
    }

    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
        
    }

    public void updatePartsTable() {
        partsTableView.setItems(Inventory.getAllParts());
        
    }

    public void updateProductsTable() {
        productsTableView.setItems(Inventory.getAllProducts());
        
    }
    
}
