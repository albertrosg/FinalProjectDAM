/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Albert
 */
public class SessionControl {
    
    public Session createConnection() {

        SessionFactory sessionFactory = new Configuration().configure("models/connection/hibernate.cfg.xml").buildSessionFactory();

        return sessionFactory.openSession();

    }
    
    public void closeConnection(Session session) {
        SessionFactory sessionFactory = session.getSessionFactory();        
        session.close();
        sessionFactory.close();
    }
    
}
