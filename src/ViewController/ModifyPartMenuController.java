/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author homie
 */
public class ModifyPartMenuController implements Initializable {

    @FXML
    private RadioButton IHRadioButtonMP;
    @FXML
    private RadioButton OSRadioButtonMP;
    @FXML
    private TextField modifyPartMaxTxt;
    @FXML
    private TextField modifyPartMinTxt;
    @FXML
    private TextField modifyPartIDTxt;
    @FXML
    private TextField modifyPartNameTxt;
    @FXML
    private TextField modifyPartInvTxt;
    @FXML
    private TextField modifyPartPriceTxt;
    @FXML
    private TextField modifyPartCoNameTxt;
    @FXML
    private Label companyNameLabel;
    
    Stage stage;
    Parent scene;
    private Part part;
    private static int index;
    private boolean isIH;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        part = MainMenuController.selectedPart;
        index = MainMenuController.selectedPartIndex;
        modifyPartIDTxt.setText(Integer.toString(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyPartInvTxt.setText(Integer.toString(part.getStock()));
        modifyPartPriceTxt.setText(Double.toString(part.getPrice()));
        modifyPartMinTxt.setText(Integer.toString(part.getMin()));
        modifyPartMaxTxt.setText(Integer.toString(part.getMax()));
        
        if(part instanceof InhousePart){
            companyNameLabel.setText("Machine ID");
            modifyPartCoNameTxt.setText(Integer.toString(((InhousePart)Inventory.getAllParts().get(index)).getMachineId()));
            IHRadioButtonMP.setSelected(true);
            isIH = true;
        }
        if(part instanceof OutsourcedPart){
            companyNameLabel.setText("Company Name");
            modifyPartCoNameTxt.setText((((OutsourcedPart)Inventory.getAllParts().get(index)).getCompanyName()));
            OSRadioButtonMP.setSelected(true);
            isIH = false;
            
        }
    }    

    @FXML
    private void onActionSaveModPart(ActionEvent event) throws IOException {
        int id = Integer.parseInt(modifyPartIDTxt.getText());
        String name = modifyPartNameTxt.getText();
        double price = Double.parseDouble(modifyPartPriceTxt.getText());
        int stock = Integer.parseInt(modifyPartInvTxt.getText());
        int min = Integer.parseInt(modifyPartMinTxt.getText());
        int max = Integer.parseInt(modifyPartMaxTxt.getText());
        
        if(min > max){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Min is greater than max!");
            alert.showAndWait();
        }
        else{
            try{
                if(isIH){
                    int machineID = Integer.parseInt(modifyPartCoNameTxt.getText());
                    InhousePart ihPart = new InhousePart(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(index, ihPart);
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setContentText("Mod success!");
                    alert.showAndWait();
                    
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
                    scene = loader.load();
                    Scene view = new Scene(scene);
                    stage.setScene(view);
                    stage.show();
                    
                }
                else{
                    String companyName = modifyPartCoNameTxt.getText();
                    OutsourcedPart osPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(index, osPart);
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setContentText("Mod success!");
                    alert.showAndWait();
                    
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
                    scene = loader.load();
                    Scene view = new Scene(scene);
                    stage.setScene(view);
                    stage.show();
                    
                }
            }
            catch(IOException | NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error with part mod!");
                alert.showAndWait();
                
            }
        }
    }

    @FXML
    private void onActionCancelModPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Everything will be cleared. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            
        }
        
    }
    
    @FXML
    private void onActionIHRB(ActionEvent event){
        isIH = true;
        companyNameLabel.setText("Machine ID");
        OSRadioButtonMP.setSelected(false);
        modifyPartCoNameTxt.clear();

    }
    
    @FXML
    private void onActionOSRB(ActionEvent event){
        isIH = false;
        companyNameLabel.setText("Company Name");
        IHRadioButtonMP.setSelected(false);
        modifyPartCoNameTxt.clear();
    }
}
