/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.entitys.User;
import models.entitys.persistence.SaveOwner;
import models.entitys.persistence.SavePet;
import models.entitys.persistence.SaveUser;

/**
 * Controller for manage the view mainWindow
 *
 * @author Albert
 */
public class MainWindowController implements Initializable {

    @FXML
    private FlowPane flowPane;
    @FXML
    private Label lblWelcome;
    @FXML
    private ImageView imgIcon;
    @FXML
    private VBox vbMainLeft;
    @FXML
    private Button btLogout, btExit;
    @FXML 
    private BorderPane mainBorderPane;
    @FXML
    private AnchorPane topAnchorPane, leftAnchorPane;

    private AnchorPane manageUsers;

    private WindowControl windowControl;

    private User user;

    private LoginController loginController;

    private Button btOwnerPets = new Button("Clientes");
    private Button btUser = new Button("Usuarios");
    private Button btProduct = new Button("Productos");
    private Button btDiary = new Button("Agenda");
    private Button btPay = new Button("Cobrar");

    /**
     * Initializes the controller class.
     * @param url The URL of the view
     * @param rb The resource of the RecourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        windowControl = new WindowControl();
        Platform.runLater(() -> {
            try {
                initializeWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        user = new SaveUser().loadUser();
        
    }

    /**
     * Method for exit the aplicattion when cliked in the exit button
     * @param event The event of the button
     */
    @FXML
    private void exit(ActionEvent event) throws IOException {

        new SaveUser().removeUser();
        new SavePet().removePet();
        new SaveOwner().removeOwner();
        System.exit(0);

    }

    /**
     * Method for exit the mainWindowView al load loginView
     * @param event The event of the button
     */
    @FXML
    private void logout(ActionEvent event) {

        user = null;
        new SaveUser().removeUser();
        windowControl.openLogin();
        windowControl.close(event);

    }   

    /**
     * Method for load the diary view.
     * Also load the buttons. If user is admin load user button and product button.
     * @throws IOException Exception throw if there is a problem when loading the view 
     */
    private void initializeWindow() throws IOException {
        
        lblWelcome.setText("Bienvenido " + user.getName() + " " + user.getSurname());
        
        imgIcon.setImage(new Image("/images/IconMainWindow.png"));

        btLogout.setMinWidth(vbMainLeft.getWidth());
        btExit.setMinWidth(vbMainLeft.getWidth());
        
        manageUsers = FXMLLoader.load(getClass().getResource("/views/diary.fxml"));
        flowPane.getChildren().add(manageUsers);
        
        vbMainLeft.setSpacing(10);
        vbMainLeft.setPadding(new Insets(20));

        if (user.isAdmin()) {            
            btUser.setMinWidth(vbMainLeft.getWidth());
            btUser.setOnMouseClicked(event -> panelControl(event));
            btProduct.setMinWidth(vbMainLeft.getWidth());
            btProduct.setOnMouseClicked(event -> panelControl(event));
            vbMainLeft.getChildren().addAll(btUser, btProduct);
        }      
        
        btOwnerPets.setOnMouseClicked(event -> panelControl(event));
        btOwnerPets.setMinWidth(vbMainLeft.getWidth());
        vbMainLeft.getChildren().add(btOwnerPets);
                
        btDiary.setOnMouseClicked(event -> panelControl(event));
        btDiary.setMinWidth(vbMainLeft.getWidth());
        vbMainLeft.getChildren().add(btDiary);
        
        btPay.setOnMouseClicked(event -> panelControl(event));
        btPay.setMinWidth(vbMainLeft.getWidth());
        vbMainLeft.getChildren().add(btPay);

    }

    /**
     * Method for load the view that corresponds with the clicked button
     * @param event The event of the button
     */
    private void panelControl(MouseEvent event) {
        try {
            if (event.getSource().equals(btOwnerPets)) {                             
                manageUsers = FXMLLoader.load(getClass().getResource("/views/manageOwners.fxml"));
            } else if (event.getSource().equals(btUser)) {
                manageUsers = FXMLLoader.load(getClass().getResource("/views/manageUsers.fxml"));
            }else if (event.getSource().equals(btProduct)) {
                manageUsers = FXMLLoader.load(getClass().getResource("/views/product.fxml"));
            }else if (event.getSource().equals(btDiary)){
                manageUsers = FXMLLoader.load(getClass().getResource("/views/diary.fxml"));
            }else if (event.getSource().equals(btPay)){
                manageUsers = FXMLLoader.load(getClass().getResource("/views/pay.fxml"));
            }
            flowPane.getChildren().clear();
            flowPane.getChildren().add(manageUsers);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }        
    
}


