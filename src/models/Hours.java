/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Albert
 */
public class Hours {
    
    private ObservableList<LocalTime> hours = FXCollections.observableArrayList();
    
    public Hours(){
        
        LocalTime startMorning = LocalTime.of(8, 0);
        LocalTime finishMorning = LocalTime.of(14, 0);
        LocalTime startAfternoon = LocalTime.of(17, 0);
        LocalTime finishAfternoon = LocalTime.of(20, 0);
                
        for (LocalTime hour = startMorning; hour.isBefore(finishMorning); hour = hour.plusMinutes(30)){
            hours.add(hour);
        }  
        
        for (LocalTime hour = startAfternoon; hour.isBefore(finishAfternoon); hour = hour.plusMinutes(30)){
            hours.add(hour);
        }       
    }

    public ObservableList<LocalTime> getHours() {
        return hours;
    }

    public void setHours(ObservableList<LocalTime> hours) {
        this.hours = hours;
    }   
}
