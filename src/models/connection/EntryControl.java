/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.entitys.Entry;
import models.entitys.Pet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Albert
 */
public class EntryControl {
    
    private SessionControl sessionControl = new SessionControl();
    private Session session = null;
    private Transaction tx = null;
    
    public boolean createEntry(Entry entry){
        
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            session.save(entry);
            
            tx.commit();
            
            return true;
            
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
            return false;
        }finally{
            session.close();
            sessionControl.closeConnection(session);
        }
        
    }
    
    public ObservableList<Entry> getEntry(Pet pet){
        
        session = sessionControl.createConnection();
        
        ObservableList<Entry> ol = FXCollections.observableArrayList();
        
        try{
           
            tx = session.beginTransaction();
            
            Query query = session.createQuery("from Entry where pet = :pet order by date desc, hour desc");
            
            query.setParameter("pet", pet);
            
            List<Entry> list = query.getResultList();
            
            for (Entry entryList : list){
                
                Entry entry = new Entry();
                entry.setEntryId(entryList.getEntryId());
                entry.setDate(entryList.getDate());
                entry.setComment(entryList.getComment());
                entry.setHour(entryList.getHour());
                entry.setPet(entryList.getPet());
                
                ol.add(entry);
                
            }
            
            tx.commit();
            
            
        }catch(Exception e){
            e.printStackTrace();
            
        }finally{
            session.close();
            sessionControl.closeConnection(session);
        }
        
        return ol;
    }    
    
}
