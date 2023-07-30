/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.connection.UserControl;
import models.connection.VisitControl;
import models.entitys.User;
import models.entitys.encryption.SHAEncryption;
import models.entitys.persistence.SaveOwner;
import models.entitys.persistence.SavePet;
import models.entitys.persistence.SaveUser;
import org.hibernate.service.spi.ServiceException;
import views.Alerts;


/**
 * Controller for manage the view login
 */
public class LoginController extends KeyAdapter implements Initializable{   
    
    @FXML
    private Label lblError;    
    @FXML
    private Button btAccess;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUser;   
    @FXML
    private ImageView vetImg, imgLogin;
    
    private User user;
    
    private WindowControl windowControl;     
    
    /**
     * Initializes the controller class.
     * @param url The URL of the view
     * @param rb The resource of the RecourceBundle
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {         
        
        try{
            
            txtUser.setText("albros");
            txtPassword.setText("1234");
        
            new VisitControl().deleteVisitPrevious();

            windowControl = new WindowControl();
            btAccess.setDisable(false);
            Platform.runLater(() -> {
                        txtUser.requestFocus();
                        loadImage();
                    });                    
            txtUser.setOnKeyReleased(e -> enableButton());
            txtPassword.setOnKeyReleased(e-> enableButton());        
        
        }catch (ServiceException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No ha sido posible conectarse a la base de datos.\nRevise su conexión.");
            alert.setTitle("Error de conexión");
            alert.setHeaderText("No hay acceso a la base de datos");
            alert.showAndWait();
            System.exit(0);
        }   
    }    

    /**
     * Method for exit the aplicattion when cliked in the exit button
     * @param event The event of the button
     */
    @FXML
    private void exit(ActionEvent event) {
        
        new SaveUser().removeUser();
        new SavePet().removePet();
        new SaveOwner().removeOwner();
        System.exit(0);
    }

    /**
     * Method that check the user name and password.
     * Load mainWindow view
     * @param event The event of the button
     * @throws IOException Exception throw if there is a problem when loading the view
     */
    @FXML
    private void access(ActionEvent event) throws IOException {
        try{
            String userName = txtUser.getText().trim();
            String password = new SHAEncryption().Sha256(txtPassword.getText().trim());  
            user = new UserControl().getUser(userName, password, lblError);
            new SaveUser().saveUser(user);
            if (user!=null){     
                windowControl.openMainWindow();
                windowControl.close(event);       
            }else{            
                lblError.setText("Usuario o contraseña no valido");
                txtUser.requestFocus();
            }
        }catch (IllegalStateException e){
            lblError.setText("Ha ocurrido un fallo con la conexión");
        }
    }     
   
    /**
     * Method for enable acces button
     */
    public void enableButton(){
        
        lblError.setText("");
        if (!txtUser.getText().isEmpty()){
            if (!txtPassword.getText().isEmpty()){
                btAccess.setDisable(false);
            } else btAccess.setDisable(true);
        }
    }    
      
    /**
     * Method for load the images
     */
    public void loadImage(){
        
        Image imgVet = new Image("/images/VetImage.png");
        Image loginImg = new Image("/images/LoginIcon.png");
        vetImg.setImage(imgVet);
        imgLogin.setImage(loginImg);
        
    }
}
