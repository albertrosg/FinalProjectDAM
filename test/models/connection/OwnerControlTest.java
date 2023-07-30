/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.entitys.Owner;
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
public class OwnerControlTest {
    
    public OwnerControlTest() {
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
     * Test of getOwner method, of class OwnerControl.
     */
    @Test
    public void testGetOwner_0args() {
        System.out.println("getOwner");
        OwnerControl instance = new OwnerControl();
        assertNotNull(instance.getOwner());
        System.out.println("GetOwner OK");
    }

    /**
     * Test of getOwner method, of class OwnerControl.
     */
    @Test
    public void testGetOwner_String() {
        System.out.println("getOwner");
        String name = "Pedro";
        OwnerControl instance = new OwnerControl();
        assertNotNull(instance.getOwner(name));
        System.out.println("GetOwner String OK");
    }

    /**
     * Test of getOwnerTable method, of class OwnerControl.
     */
    @Test
    public void testGetOwnerTable() {
        System.out.println("getOwnerTable");
        String dni = "11111111A";
        OwnerControl instance = new OwnerControl();
        assertNotNull(instance.getOwner(dni));
        System.out.println("GetOwner Table OK");
    }

    /**
     * Test of getNameOwner method, of class OwnerControl.
     */
    @Test
    public void testGetNameOwner() {
        System.out.println("getNameOwner");
        int id = 19;
        OwnerControl instance = new OwnerControl();
        assertNotNull(instance.getNameOwner(id));
        System.out.println("GetNameOwner Table OK");
    }

    /**
     * Test of createOwner method, of class OwnerControl.
     */
    @Test
    public void testCreateOwner() {
        
        System.out.println("createOwner");
        Owner owner = new Owner("22222222A", "Maria", "perez",  "maria@perez.com", "666666666", "  sad  ", null, null);
        OwnerControl instance = new OwnerControl();
        assertTrue(instance.createOwner(owner, null, null));
        System.out.println("CreateOwner OK");
    }
    
}
