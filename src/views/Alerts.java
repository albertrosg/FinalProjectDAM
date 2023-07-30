/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Albert
 */
public class Alerts {   
    
    private final Alert.AlertType type;
    private final String title;
    private final String content;
    private final String header;

    public Alerts(Alert.AlertType type, String title, String content, String header) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.header = header;        
    }    
    
    public Alert getAlert(){
        
        
        
        Alert alert = new Alert(type);   
        DialogPane dialogPane = alert.getDialogPane();
        if (type == Alert.AlertType.CONFIRMATION){
            ObservableList<ButtonType> buttons = alert.getButtonTypes();
            for (ButtonType button : buttons){
                if (button == ButtonType.OK){
                    Button removeButton = (Button) dialogPane.lookupButton(button);
                    removeButton.setId("removeButton");
                    removeButton.setText("Eliminar");
                }
            }
        }
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        
        
        
        
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        
        Scene scene = stage.getScene();
        scene.getStylesheets().add(getClass().getResource("/styles/mainWindowStyle.css").toExternalForm());
        
        stage.getIcons().add(new Image("/images/WindowIcon.png"));
        
        return alert;
        
    }
    
}
