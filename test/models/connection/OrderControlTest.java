/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import javafx.collections.ObservableList;
import models.entitys.Order;
import models.entitys.Product;
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
public class OrderControlTest {
    
    public OrderControlTest() {
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
     * Test of createOrder method, of class OrderControl.
     */
    @Test
    public void testCreateOrder() {
        System.out.println("createOrder");
        Order order = null;
        OrderControl instance = new OrderControl();
        assertFalse(instance.createOrder(order));
        System.out.println("Create Order OK");
    }

    /**
     * Test of getOrder method, of class OrderControl.
     */
    @Test
    public void testGetOrder() {
        System.out.println("getOrder");
        Product product = new Product("AntPar+20", "Antiparasitario interno para más de 20 kilos", 8.3, 40, 5);
        OrderControl instance = new OrderControl();
        assertNotNull(instance.getOrder(product));
        System.out.println("Create Order OK");        
    }
    
}
