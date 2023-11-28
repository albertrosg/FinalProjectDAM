/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Albert
 */
@Entity
@Table (name="product")
public class Product {
    
    @Id
    @Column(name="product_id")
    private String productId;
    
    @Column (name="name")
    private String name;
    
    @Column (name="price")
    private double price;
    
    @Column (name="stock")
    private int stock;
    
    @Column (name="critical_stock")
    private int criticalStock;
    
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "product", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})    
    private List<Order> order; 

    public Product() {
    }

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }   

    public Product(String productId, String name, double price, int stock, int criticalStock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.criticalStock = criticalStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCriticalStock() {
        return criticalStock;
    }

    public void setCriticalStock(int criticalStock) {
        this.criticalStock = criticalStock;
    }
    
    
    
    
}
