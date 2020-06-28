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
public class ModifyProductMenuController implements Initializable {

    @FXML
    private TextField modifyProductIDTxt;
    @FXML
    private TextField modifyProductNameTxt;
    @FXML
    private TextField modifyProductInvTxt;
    @FXML
    private TextField modifyProductPriceTxt;
    @FXML
    private TextField modifyProductMaxTxt;
    @FXML
    private TextField modifyProductMinTxt;
    @FXML
    private TextField modifyProductSearchTxt;
    @FXML
    private TableView<Part> addModProdTable;
    @FXML
    private TableColumn<Part, Integer> modPartIDCol;
    @FXML
    private TableColumn<Part, String> modPartNameCol;
    @FXML
    private TableColumn<Part, Integer> modPartInvLvlCol;
    @FXML
    private TableColumn<Part, Double> modPartPPUCol;
    @FXML
    private TableView<Part> deleteModProdTable;
    @FXML
    private TableColumn<Part, Integer> delPartIDCol;
    @FXML
    private TableColumn<Part, String> delPartNameCol;
    @FXML
    private TableColumn<Part, Integer> delPartInvLvlCol;
    @FXML
    private TableColumn<Part, Double> delPartPPUCol;
    
    private Product product;
    private static int index;
    Stage stage;
    Parent scene;
    ObservableList<Part> search = FXCollections.observableArrayList();
    ObservableList<Part> associated = FXCollections.observableArrayList();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product = MainMenuController.selectedProduct;
        index = MainMenuController.selectedProductIndex;
        
        modifyProductIDTxt.setText(Integer.toString(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(Integer.toString(product.getStock()));
        modifyProductPriceTxt.setText(Double.toString(product.getPrice()));
        modifyProductMinTxt.setText(Integer.toString(product.getMin()));
        modifyProductMaxTxt.setText(Integer.toString(product.getMax()));
        //set top and bottom tables
        modPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPartPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        delPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        delPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        delPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        delPartPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addModProdTable.setItems(Inventory.getAllParts());
        associated.addAll(product.getAllAssociatedParts());
        deleteModProdTable.setItems(associated);
        
    }    

    @FXML
    private void onActionSearchModProd(ActionEvent event) {
        try{
            try{
                int id = Integer.parseInt(modifyProductSearchTxt.getText());
                search.add(Inventory.lookupPart(id));
            }
            catch(NumberFormatException e){
                String name = modifyProductSearchTxt.getText();
                search = Inventory.lookupPart(name);
            }
            if(search.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Part missing!");
                alert.showAndWait();
                
            }
            else{
                modPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                modPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                modPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                modPartPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Part missing!");
            alert.showAndWait();
        }
        if(modifyProductSearchTxt.getText().equals("")){
            addModProdTable.setItems(Inventory.getAllParts());
            
        }
    }

    @FXML
    private void onActionAddAddProd(ActionEvent event) {
        associated.add(addModProdTable.getSelectionModel().getSelectedItem());
        Part part = addModProdTable.getSelectionModel().getSelectedItem();
        deleteModProdTable.setItems(associated);
        delPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        delPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        delPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        delPartPPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void onActionDeleteModProd(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will be deleted. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            associated.remove(deleteModProdTable.getSelectionModel().getSelectedItem());
            
        }
    }

    @FXML
    private void onActionSaveModProd(ActionEvent event) throws IOException {
        int id = Integer.parseInt(modifyProductIDTxt.getText());
        String name = modifyProductNameTxt.getText();
        int stock = Integer.parseInt(modifyProductInvTxt.getText());
        double price = Double.parseDouble(modifyProductPriceTxt.getText());
        int min = Integer.parseInt(modifyProductMinTxt.getText());
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        
        try{
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Min is greater than max!");
                alert.showAndWait();
            }
            else{
                Product product = new Product(id, name, price, stock, min, max);
                
                for (Part part : associated){
                    product.addAssociatedParts(part);
                    
                }
                Inventory.updateProduct(index, product);
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setContentText("Data missing!");
            alert.showAndWait();
            
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void onActionCancelModProd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes made. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        
    }
    
}
