/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.text.DecimalFormat;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Albert
 */
public class Pay {
    
     private final StringProperty code;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty amount;
    private final DoubleProperty total;

    public Pay(String code, String name, double price, int amount) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.total = new SimpleDoubleProperty(price * amount);
        
        // Agregar listeners para actualizar el total cuando cambien el precio o la cantidad
        this.price.addListener((observable, oldValue, newValue) -> updateTotal());
        this.amount.addListener((observable, oldValue, newValue) -> updateTotal());
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }
    
    public void setAmount(int amount){
        this.amount.set(amount);
    }

    private void updateTotal() {
        double newTotal = Math.round(getPrice() * getAmount() * 100.0) / 100.0;
        total.set(newTotal);
    }   
    
    
}
