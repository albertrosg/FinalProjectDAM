/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Albert
 */
public class SessionControlTest {
    
    public SessionControlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createConnection method, of class SessionControl.
     */
    @Test
    public void testCreateConnection() {
        System.out.println("createConnection");
        SessionControl instance = new SessionControl();
        assertNotNull(instance.createConnection());
        System.out.println("CreateConnection OK");
    }

    /**
     * Test of closeConnection method, of class SessionControl.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        SessionControl instance = new SessionControl();        
        Session session = instance.createConnection();
        instance.closeConnection(session);
        assertFalse(session.isConnected());
        assertTrue(session.getSessionFactory().isClosed());
        System.out.println("CloseConnection OK");
    }
    
}
