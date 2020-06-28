/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.Inventory;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Model.InhousePart;
import Model.OutsourcedPart;
import Model.Inventory;
import Model.Part;

/**
 * FXML Controller class
 *
 * @author homie
 */
public class AddPartMenuController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private RadioButton IHRadioBtn;
    @FXML
    private ToggleGroup radioButtons;
    @FXML
    private RadioButton OSRadioBtn;
    @FXML
    private Label addPartBoolLabel;
    @FXML
    private TextField addPartMaxTxt;
    @FXML
    private TextField addPartMinTxt;
    @FXML
    private TextField addPartIdTxt;
    @FXML
    private TextField addPartNameTxt;
    @FXML
    private TextField addPartInvTxt;
    @FXML
    private TextField addPartPriceTxt;
    @FXML
    private TextField addPartCompanyNameTxt;
    
    private boolean isIH;
    private int counter;
    private int id;
    Stage stage;
    Parent scene;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                //Toggle Group
        radioButtons = new ToggleGroup();
        this.IHRadioBtn.setToggleGroup(radioButtons);
        this.OSRadioBtn.setToggleGroup(radioButtons);
        
        //Selects InHouse as defualt and sets value to true
        this.IHRadioBtn.setSelected(true);
        isIH = true;
        addPartBoolLabel.setText("Machine ID");
        
        //Autogenerates Part ID
        counter = Inventory.getPartCounter();
        addPartIdTxt.setText(Integer.toString(counter));
    }    

    @FXML
    private void onActionSaveAddPart(ActionEvent event) throws IOException {
        String name = addPartNameTxt.getText();
        double price = Double.parseDouble(addPartPriceTxt.getText());
        int stock = Integer.parseInt(addPartInvTxt.getText());
        int min = Integer.parseInt(addPartMinTxt.getText());
        int max = Integer.parseInt(addPartMaxTxt.getText());
        
        try{
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Min is greater than max!");
                alert.showAndWait();
                return;
            }
            else{
                if(isIH){
                    int machineID = Integer.parseInt(addPartCompanyNameTxt.getText());
                    InhousePart inhousePart = new InhousePart(counter, name, price, stock, min, max, machineID);
                    Inventory.addPart(inhousePart);
                    
                }
                else{
                    String companyName = addPartCompanyNameTxt.getText();
                    OutsourcedPart outsourcedPart = new OutsourcedPart(counter, name, price, stock, min, max, companyName);
                    Inventory.addPart(outsourcedPart);
                    
                }
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please make sure all fields are valid!");
            alert.showAndWait();
            return;
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void onActionCancelAddPart(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all added parts. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()&& result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ViewController/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    /**
     *
     */
    /** public void radioButtonBool(){
        if (this.radioButtons.getSelectedToggle().equals(this.IHRadioBtn)){
            addPartBoolLabel.setText("Machine ID");
            isIH = true;
        }
        if (this.radioButtons.getSelectedToggle().equals(this.OSRadioBtn)){
            addPartBoolLabel.setText("Company Name");
            isIH = false;
        }
    } */
    @FXML
    private void onActionIHRB(ActionEvent event){
        isIH = true;
        addPartBoolLabel.setText("Machine ID");
        OSRadioBtn.setSelected(false);
        addPartCompanyNameTxt.clear();

    }
    
    @FXML
    private void onActionOSRB(ActionEvent event){
        isIH = false;
        addPartBoolLabel.setText("Comany Name");
        IHRadioBtn.setSelected(false);
        addPartCompanyNameTxt.clear();

    }
}

