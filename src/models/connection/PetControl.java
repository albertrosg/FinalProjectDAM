/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import models.entitys.Pet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Albert
 */
public class PetControl {    
    
    private Session session;
    private Transaction tx;    
    private List<Pet> list = null;
    private ObservableList<Pet> listView = FXCollections.observableArrayList();    
    private SessionControl sessionControl = new SessionControl();
    
    public boolean addPet(Pet pet, Label label){
        
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();           
            
            session.save(pet);            
            
            tx.commit();
            
            return true;
            
        }catch (javax.persistence.PersistenceException e){
            
            label.setText("El numero de licencia pertence a otra mascota"); 
            e.printStackTrace();
            tx.rollback();
            return false;
        
        }catch(Exception e){
            e.printStackTrace();
            label.setText("Ha ocurrido un error");
            tx.rollback();
            return false;
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }
    
    public ObservableList<Pet> getPet(int id){
        
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            list = session.createQuery("from Pet where owner_id=" + id).getResultList();
            
            for (Pet pet : list){
                
                String name = pet.getName();
                
                String type = pet.getType();
                
                String chipNumber = pet.getChipNumber();               
                
                Pet newPet = new Pet(chipNumber, name, type);
                
                listView.add(newPet);
                
            }
            
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally{
            sessionControl.closeConnection(session);
        }
        
        return listView;
    }
    
    public Pet getPet(String chipNumber){
        
        session = sessionControl.createConnection();       
        
        try{
            
            tx = session.beginTransaction();
            
            return (Pet) session.createQuery("from Pet where chip_number='" + chipNumber + "'").getSingleResult(); 
            
            
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }
    
     public boolean removePet(Pet pet) {

        session = sessionControl.createConnection();

        try {
            tx = session.beginTransaction();

            session.delete(pet);

            tx.commit();
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        } finally {
            sessionControl.closeConnection(session);
        }

    }
     
    public void updatePet(Pet pet, String type, Double weight){
        
        session = sessionControl.createConnection();
        
        try{
        
            tx = session.beginTransaction();

            String id = pet.getChipNumber();

            Query query = session.createQuery("from Pet where chip_number = :id");

            query.setParameter("id", id);

            Pet returnPet = (Pet) query.getSingleResult();

            returnPet.setWeight(weight);

            session.save(returnPet);

            tx.commit();
        
        }catch (Exception e){
            
            e.printStackTrace();
            tx.rollback();
            
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }
    
}
