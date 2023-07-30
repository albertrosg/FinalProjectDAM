/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.connection.OwnerControl;
import models.entitys.Owner;
import models.entitys.persistence.SaveOwner;

/**
 * FXML Controller class
 *
 * @author Albert
 */
public class ModifyOwnerController implements Initializable {

    @FXML
    private Label lblModifyOwner;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtAddress;
    @FXML
    private Button btModify;
    
    private Owner owner;
    
    private String dni;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        owner = new SaveOwner().loadOwner();
        
        btModify.setDisable(true);
        
        Platform.runLater(() -> initialize());
        
        dni = txtDni.getText();
        name = txtName.getText();
        surname = txtSurname.getText();
        email = txtEmail.getText();
        phoneNumber = txtPhoneNumber.getText();
        address = txtAddress.getText();
        
    }    

    @FXML
    private void cancel(ActionEvent event) {
        
        new WindowControl().close(event);
        
    }

    @FXML
    private void modify(ActionEvent event) {  
        
        dni = txtDni.getText();
        name = txtName.getText();
        surname = txtSurname.getText();
        email = txtEmail.getText();
        phoneNumber = txtPhoneNumber.getText();
        address = txtAddress.getText();        
        
        new OwnerControl().updateOwner(new Owner(dni, name, surname, phoneNumber, email, address), owner.getOwnerId());
        
        new WindowControl().close(event);
        
    }

    private void initialize() {
        
        txtName.setText(owner.getName());
        txtName.setOnKeyReleased(e -> enableButton());
        txtSurname.setText(owner.getSurname());
        txtSurname.setOnKeyReleased(e -> enableButton());
        txtDni.setText(owner.getDni());
        txtDni.setOnKeyReleased(e -> enableButton());
        txtEmail.setText(owner.getEmail());
        txtEmail.setOnKeyReleased(e -> enableButton());
        txtPhoneNumber.setText(owner.getPhoneNumber());
        txtPhoneNumber.setOnKeyReleased(e -> enableButton());
        txtAddress.setText(owner.getAddress());
        txtAddress.setOnKeyReleased(e -> enableButton());
        
    }

    private void enableButton() {
        
        dni = txtDni.getText();
        name = txtName.getText();
        surname = txtSurname.getText();
        email = txtEmail.getText();
        phoneNumber = txtPhoneNumber.getText();
        address = txtAddress.getText();
        
        if (!name.equals(owner.getName())){
            btModify.setDisable(false);
        } else if (!surname.equals(owner.getSurname())){
            btModify.setDisable(false);
        } else if (!dni.equals(owner.getDni())){
            btModify.setDisable(false);
        } else if (!email.equals(owner.getEmail())){
            btModify.setDisable(false);
        } else if (!phoneNumber.equals(owner.getPhoneNumber())){
            btModify.setDisable(false);
        } else if (!address.equals(owner.getAddress())){
            btModify.setDisable(false);
        } else {
            btModify.setDisable(true);
        }
        
        
    }
    
}
//