/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

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
@Table(name="racepet")
public class PetRace {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="raceId")
    private int raceId;
    
    @Column(name="nameRace")
    private String nameRace;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="typeId")
    private PetType petType;

    public PetRace() {
    }

    public PetRace(String nameRace) {
        this.nameRace = nameRace;
    }

    public int getRaceId() {
        return raceId;
    }

    public String getNameRace() {
        return nameRace;
    }

    public void setNameRace(String nameRace) {
        this.nameRace = nameRace;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }
    
    
    
}
