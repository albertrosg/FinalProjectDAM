/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.entitys.Entry;

/**
 *
 * @author Albert
 */
public class WindowControl {   
    
    public void openMainWindow() throws IOException{
        
        Toolkit t = Toolkit.getDefaultToolkit();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        double width = screenSize.width;
        double heigth = screenSize.height;
        
        Stage stage = new Stage();    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainWindow.fxml"));
        Parent root = (Parent) loader.load();        
        Scene scene = new Scene(root);
        stage.setHeight(heigth*0.95);
        stage.setWidth(width);
        stage.setScene(scene);        
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/WindowIcon.png"));
        stage.setTitle("VetPro");
        stage.show();
        
    }
    
    public void openOtherWindow(String view, String title) throws IOException{
        
        Stage stage = new Stage();        
        Parent root = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));        
        Scene scene = new Scene(root);        
        stage.setScene(scene);  
        stage.setTitle(title);
        stage.showAndWait();
        
    }
    
    
    
    public void openLogin(){
        
        try {
            Stage stage = new Stage();
            
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openWindowShowEntry(Entry entry){
        
        Stage stage = new Stage();        
        AnchorPane root = new AnchorPane();
        TextArea textArea = new TextArea(entry.getComment());        
        Scene scene = new Scene(root, 800, 400);   
        
        textArea.setWrapText(true);
        textArea.setPrefHeight(scene.getHeight());
        textArea.setPrefWidth(scene.getWidth());
        textArea.setEditable(false);
        
        root.getChildren().add(textArea);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);  
        stage.setTitle("Entrada del " + entry.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " a las " + entry.getHour());
        stage.getIcons().add(new Image("/images/WindowIcon.png"));
        stage.toFront();
        stage.showAndWait();
        
    }
    
    public void close(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
