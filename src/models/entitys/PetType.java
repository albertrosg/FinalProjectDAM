/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="typepet")
public class PetType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="typeId")
    private int typeId;
    
    @Column(name="nameType")
    private String nameType;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petType", cascade = CascadeType.ALL)
    private List<PetRace> petRace;

    public PetType() {
    }

    public PetType(String nameType) {
        this.nameType = nameType;
    }

    public int getTypeId() {
        return typeId;
    }   

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public List<PetRace> getPetRace() {
        return petRace;
    }

    public void setPetRace(List<PetRace> petRace) {
        this.petRace = petRace;
    }
    
    
    
}
