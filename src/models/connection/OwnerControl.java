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
import javafx.scene.control.TextArea;
import models.entitys.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author Albert
 */
public class OwnerControl {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;
    private boolean saved;
    private List<Owner> list = null;
    private ObservableList<Owner> listView = FXCollections.observableArrayList();  
    private SessionControl sessionControl = new SessionControl();

    public ObservableList<Owner> getOwner() {

        listView.clear();
        session = sessionControl.createConnection();

        try {

            list = session.createQuery("from Owner order by surname").getResultList();

            for (Owner owner : list) {

                String recoveredDni = owner.getDni();
                String name = owner.getName();
                String surname = owner.getSurname();

                Owner newOwner = new Owner(recoveredDni, name, surname);

                listView.add(newOwner);
            }

            return listView;

        } finally {

            sessionControl.closeConnection(session);

        }

    }

    public ObservableList<Owner> getOwner(String name) {

        listView.clear();
        session = sessionControl.createConnection();

        try {

            list = session.createQuery("from Owner where name like '" + name + "%' order by surname").getResultList();

            for (Owner owner : list) {

                String recoveredDni = owner.getDni();
                String recoveredName = owner.getName();
                String recoveredSurname = owner.getSurname();

                Owner newOwner = new Owner(recoveredDni, recoveredName, recoveredSurname);

                listView.add(newOwner);
            }

            return listView;

        } finally {
            sessionControl.closeConnection(session);
        }

    }
    
    /**
     *
     * @param dni
     * @return
     */
    public Owner getOwnerTable(String dni) {

        session = sessionControl.createConnection();

        try {

            return (Owner) session.createQuery("from Owner where dni='" + dni + "'").getSingleResult();            
            

        } finally {
            sessionControl.closeConnection(session);
        }

    }
    
    public String getNameOwner(int id){
        
        session = sessionControl.createConnection();
        
        Owner owner = null;
        
        try{
            
            tx = session.beginTransaction();
            
            owner = (Owner) session.createQuery("from Owner where ownerId =" + id).getSingleResult();
            
            tx.commit();
            
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally{
            sessionControl.closeConnection(session);
        }
        
        return owner.getName() + " " + owner.getSurname();
        
    }

    public boolean createOwner(Owner owner, Label label, TextArea txt) {

        session = sessionControl.createConnection();

        try {
            tx = session.beginTransaction();

            session.save(owner);

            tx.commit();

            saved = true;
        }catch (org.hibernate.exception.ConstraintViolationException e){
            
            e.printStackTrace();
            label.setText("El DNI pertence a otro cliente");             
        
        }catch (Exception e) {
            e.printStackTrace();
            label.setText("Ha habido un error");
            saved = false;
            tx.rollback();
        } finally {
            sessionControl.closeConnection(session);
        }

        return saved;
    }

    public boolean removeOwner(Owner owner) {

        session = sessionControl.createConnection();

        try {
            tx = session.beginTransaction();

            session.delete(owner);

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

    
    public void updateOwner(Owner owner, int id) {

        session = sessionControl.createConnection();

        try {
            tx = session.beginTransaction();

            Owner newOwner = (Owner) session.createQuery("from Owner where id=" + id).getSingleResult();

            newOwner.setAddress(owner.getAddress());
            newOwner.setDni(owner.getDni());
            newOwner.setEmail(owner.getEmail());
            newOwner.setName(owner.getName());
            newOwner.setSurname(owner.getSurname());
            newOwner.setPhoneNumber(owner.getPhoneNumber());

            session.update(newOwner);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            sessionControl.closeConnection(session);
        }

    }    

}
