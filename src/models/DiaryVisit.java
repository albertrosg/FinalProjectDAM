/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import models.entitys.Visit;

/**
 *
 * @author Albert
 */
public class DiaryVisit implements Serializable{
    
    private final LocalDate date;
    private final LocalTime hour;
    private final Visit visit;
    private final String petName;
    private final String ownerName;
    private final String visitType;
    private final String visitComment;
    private final int visitId;

    public DiaryVisit(LocalDate date, LocalTime hour, Visit visit) {
        this.date = date;
        this.hour = hour;
        this.visit = visit;
        
        if(visit!=null){
            this.petName = visit.getPet().getName();
            this.ownerName = visit.getOwner().getName();
            this.visitType = visit.getType();
            this.visitComment = visit.getComment();
            this.visitId = visit.getVisitId();
        }else{
            this.petName = "";
            this.ownerName = "";
            this.visitType = "";
            this.visitComment = "";
            this.visitId = 0;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public Visit getVisit() {
        return visit;
    }

    public String getPetName() {
        return petName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getVisitType() {
        return visitType;
    }

    public String getVisitComment() {
        return visitComment;
    }

    public int getVisitId() {
        return visitId;
    }  
    
    
}
