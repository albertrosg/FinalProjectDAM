/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Albert
 */

@Entity
@Table (name="user")
public class User implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="surname")
    private String surname;
    
    @Column(name="license_number", unique = true)
    private String licenseNumber;
    
    @Column(name="user_name", unique = true)
    private String userName;
    
    @Column(name="password")
    private String password;
    
    @Column(name="admin")
    private boolean admin;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Visit> visit;
    
    public User() {
    }

    public User(int id, String name, String surname, String licenseNumber, String userName, boolean admin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
        this.userName = userName;
        this.admin=admin;
    }
    
    

    public User(String name, String surname, String licenseNumber, String userName, String password, boolean admin) {
        this.name = name;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
        this.userName = userName;
        this.password = password;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }   
    
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }   

    public List<Visit> getVisit() {
        return visit;
    }

    public void setDiary(List<Visit> visit) {
        this.visit = visit;
    }    
    
}