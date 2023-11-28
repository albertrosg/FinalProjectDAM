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
import models.entitys.Owner;
import models.entitys.Pet;
import models.entitys.User;
import models.entitys.Visit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Albert
 */
public class VisitControl {

    private Session session;
    private Transaction tx;
    private SessionControl sessionControl = new SessionControl();

    public boolean createVisit(Visit visit) {

        session = sessionControl.createConnection();

        try {

            tx = session.beginTransaction();

            session.save(visit);

            tx.commit();

            return true;

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
            sessionControl.closeConnection(session);
        }

    }
    
    
    public ObservableList<Visit> getVisit(int id, LocalDate date, List<LocalTime> hours) {

        session = sessionControl.createConnection();

        ObservableList<Visit> obList = FXCollections.observableArrayList();

        try {

            tx = session.beginTransaction();
            
            String hql = "from Visit where user_id = :id and hour = :hour and date = :date order by date, hour";
            
            for (LocalTime busyHour : hours){   
                
                Query query = session.createQuery(hql);

                query.setParameter("id", id);
                query.setParameter("hour", busyHour);
                query.setParameter("date", date);

                List<Visit> list = query.getResultList();

                for (Visit visit : list) {

                    int newId = visit.getVisitId();
                    String type = visit.getType();
                    LocalTime hour = visit.getHour();
                    LocalDate newDate = visit.getDate();
                    String comment = visit.getComment();
                    User user = visit.getUser();
                    Pet pet = visit.getPet();
                    Owner owner = visit.getOwner();

                    obList.add(new Visit(newId, type, hour, newDate, comment, user, pet, owner));

                }
            }

            return obList;

        } finally {
            session.close();
            sessionControl.closeConnection(session);
        }

    }
    
    public Visit getTableVisit(int id){
        
        Visit visit = null;
        
        session = sessionControl.createConnection();
                
        try{
        
            tx = session.beginTransaction();
            
            Query query = session.createQuery("from Visit where visit_id = :id");
            
            query.setParameter("id", id);
            
            visit = (Visit) query.getSingleResult();
            
            tx.commit();
            
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally{
            session.close();
            sessionControl.closeConnection(session);
        }
        
        return visit;
        
    }

    public List<LocalTime> getHours(LocalDate date, int id) {

        session = sessionControl.createConnection();

        try {

            tx = session.beginTransaction();

            String hql = "select hour from Visit where date = :date and user_id = :userId";

            Query query = session.createQuery(hql);

            query.setParameter("date", date);
            query.setParameter("userId", id);

            return query.getResultList();

        } finally {

            session.close();
            sessionControl.closeConnection(session);

        }

    }

    public void deleteVisitPrevious() {

        session = sessionControl.createConnection();

        try {

            tx = session.beginTransaction();

            String hql = "delete from Visit where date < :currentDate";
            Query query = session.createQuery(hql);
            query.setParameter("currentDate", LocalDate.now());

            query.executeUpdate();

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionControl.closeConnection(session);
        }
    }
    
    public boolean removeVisit(int id) {

        session = sessionControl.createConnection();

        try {

            tx = session.beginTransaction();
            
            String hql = "delete from Visit where visit_id = :id";
            
            Query query = session.createQuery(hql);
            query.setParameter("id", id);            
            query.executeUpdate();
            
            tx.commit();
            
            return true;

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
            sessionControl.closeConnection(session);
        }
    }

}
