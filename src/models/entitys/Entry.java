/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

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
@Table (name="entry")
public class Entry {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="entryId")
    private int entryId;
    
    @Column (name="date")
    private LocalDate date;
    
    @Column (name="comment")
    private String comment;
    
    @Column (name="hour")
    private LocalTime hour;
    
    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="chipNumber")
    private Pet pet;

    public Entry() {
    }

    public Entry(LocalDate date, String comment, LocalTime hour, Pet pet) {
        this.date = date;
        this.comment = comment;
        this.hour = hour;
        this.pet = pet;
    }

    public Entry(int entryId, LocalDate date, String comment, LocalTime hour, Pet pet) {
        this.entryId = entryId;
        this.date = date;
        this.comment = comment;
        this.hour = hour;
        this.pet = pet;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
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

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }
    
    

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
    
    
}
