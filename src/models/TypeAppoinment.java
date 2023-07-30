/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Albert
 */
public class TypeAppoinment {
    
    private ObservableList<String> types = FXCollections.observableArrayList();
    
    public TypeAppoinment(){
        types.add("Urgencia");
        types.add("Seguimiento");
        types.add("Vacunación");
        types.add("Intervención");
    }

    public ObservableList<String> getTypes() {
        return types;
    }

    public void setTypes(ObservableList<String> types) {
        this.types = types;
    }
    
    
    
}
