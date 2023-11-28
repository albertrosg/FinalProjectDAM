/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Albert
 */
@Entity
@Table(name="visit")
public class Visit implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visit_id")
    private int visitId;
    
    @Column(name="type")
    private String type;
    
    @Column(name="hour")
    private LocalTime hour;
    
    @Column(name="date")
    private LocalDate date;
    
    @Column(name="comment")
    private String comment;
    
    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;
    
    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="chip_number")
    private Pet pet;
    
    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="owner_id")
    private Owner owner;

    public Visit() {
    }

    public Visit(int visitId, String type, LocalTime hour, LocalDate date, String comment, User user, Pet pet, Owner owner) {
        this.visitId = visitId;
        this.type = type;
        this.hour = hour;
        this.date = date;
        this.comment = comment;
        this.user = user;
        this.pet = pet;
        this.owner = owner;
    }
    
    

    public Visit(String type, LocalTime hour, LocalDate date, String comment, User user, Pet pet, Owner owner) {
        this.type = type;
        this.hour = hour;
        this.date = date;
        this.comment = comment;
        this.user = user;
        this.pet = pet;
        this.owner = owner;
    }
    
    

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {        
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    
    
}
