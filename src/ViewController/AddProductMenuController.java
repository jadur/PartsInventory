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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author homie
 */
public class AddProductMenuController implements Initializable {

    @FXML
    private TextField addProductIDTxt;
    @FXML
    private TextField addProductNameTxt;
    @FXML
    private TextField addProductInvTxt;
    @FXML
    private TextField addProductPriceTxt;
    @FXML
    private TextField addProductMaxTxt;
    @FXML
    private TextField addProductMinTxt;
    @FXML
    private TextField addProductSearchTxt;
    @FXML
    private TableView<Part> addProductTableView;
    @FXML
    private TableColumn<Part, Integer> addPartIDCol;
    @FXML
    private TableColumn<Part, String> addPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addInvLvlCol;
    @FXML
    private TableColumn<Part, Double> addPPUCol;
    @FXML
    private TableColumn<Part, Integer> delPartIDCol;
    @FXML
    private TableColumn<Part, String> delPartNameCol;
    @FXML
    private TableColumn<Part, Integer> delInvLvlCol;
    @FXML
    private TableColumn<Part, Double> delPPUCol;
    @FXML
    private TableView<Part> deleteProductTableView;
    
    Stage stage;
    Parent scene;
    private int counter;

    
    ObservableList<Part> search = FXCollections.observableArrayList();
    ObservableList<Part> associated = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add table columns
        addPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //del table columns
        delPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        delPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        delInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        delPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //table views
        addProductTableView.setItems(Inventory.getAllParts());
        deleteProductTableView.setItems(associated);
        
        counter = Inventory.getProductCounter();
        addProductIDTxt.setText(Integer.toString(counter));
        
    }    

    @FXML
    private void onActionSearchAddProduct(ActionEvent event) {
        try{
            try{
                int id = Integer.parseInt(addProductSearchTxt.getText());
                search.add(Inventory.lookupPart(id));
                
            }
            catch(Exception e){
                String name = addProductSearchTxt.getText();
                search = Inventory.lookupPart(name);
            }
            if(search.size() == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Missing Part");
                
            }
            else{
                addProductTableView.setItems(search);
                addPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                addInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                addPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Missing part!");
        }
        
        if(addProductSearchTxt.getText().equals("")){
            addProductTableView.setItems(Inventory.getAllParts());
        }
    }

    @FXML
    private void onActionAddAddProduct(ActionEvent event) {
        Part part = addProductTableView.getSelectionModel().getSelectedItem();
        associated.add(part);
        deleteProductTableView.setItems(associated);
    }

    @FXML
    private void onActionDeleteAddProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "These items will be deleted. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            associated.remove(deleteProductTableView.getSelectionModel().getSelectedItem());
            
        }
    }

    @FXML
    private void onActionSaveAddProduct(ActionEvent event) throws IOException {
        String name = addProductNameTxt.getText();
        double price = Double.parseDouble(addProductPriceTxt.getText());
        int stock = Integer.parseInt(addProductMinTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());
        
        Product product = new Product(counter, name, price, stock, min, max);
        try{
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Min is greater than max!");
                alert.showAndWait();
                
            }
            else{
                for(Part part:associated){
                    product.addAssociatedParts(part);
                    
                }
                Inventory.addProduct(product);
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Missing!");
            alert.showAndWait();
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionCancelAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Everything will be clear. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
}
