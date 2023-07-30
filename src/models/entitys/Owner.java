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
import javax.persistence.Table;

/**
 *
 * @author Albert
 */

@Entity
@Table (name="owner")
public class Owner implements Serializable{
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ownerId")
    private int ownerId;        
    
    @Column (name="dni", unique=true)    
    private String dni;    
    
    @Column (name="name")
    private String name;    
    
    @Column (name="surname")
    private String surname;    
    
    @Column (name="email")
    private String email;
    
    
    @Column (name="phoneNumber")
    private String phoneNumber;    
    
    @Column (name="address")
    private String address;
    
    @OneToMany (mappedBy = "owner", cascade = CascadeType.ALL) 
    private List<Pet> pet;    
    
    @OneToMany (mappedBy = "owner", cascade = CascadeType.ALL) 
    private List<Visit> visit;   

    public Owner() {
    }   

    public Owner(String dni, String name, String surname) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
    }
    
    

    public Owner(String dni, String name, String surname, String phoneNumber, String email) {        
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    

    public Owner(String dni, String name, String surname, String email, String phoneNumber, String address, List<Pet> pet, List<Visit> visit) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pet = pet;
        this.visit = visit;
    }
    
    

    public Owner(String dni, String name, String surname, String phoneNumber, String email, String address) {        
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;        
    }    

    public Owner(int ownerId, String dni, String name, String surname, String email, String phoneNumber, String address, List<Pet> pet) {
        this.ownerId = ownerId;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pet = pet;
    }
    
    
    
    public int getOwnerId(){
        return ownerId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Owner{" + "dni=" + dni + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + ", pet=" + pet + '}';
    }   
    
    /*public void addPet(Pet pet){
        
        if (this.pet==null) {
            this.pet = new ArrayList<>();
        }
        
        this.pet.add(pet);
        pet.setOwner(this);        
    }*/

    public List<Visit> getVisit() {
        return visit;
    }

    public void setVisit(List<Visit> visit) {
        this.visit = visit;
    }
    
    
    
}
