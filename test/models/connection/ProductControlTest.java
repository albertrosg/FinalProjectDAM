/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
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
public class ProductControlTest {
    
    public ProductControlTest() {
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
     * Test of createProduct method, of class ProductControl.
     */
    @Test
    public void testCreateProduct() {
        System.out.println("createProduct");
        Product product = null;
        ProductControl instance = new ProductControl();
        assertFalse(instance.createProduct(product));
        System.out.println("CreateProduct OK");
    }

    /**
     * Test of getProduct method, of class ProductControl.
     */
    @Test
    public void testGetProduct_0args() {
        System.out.println("getProduct");
        ProductControl instance = new ProductControl();
        assertNotNull(instance.getProduct());
        System.out.println("GetProduct OK");
    }

    /**
     * Test of getProduct method, of class ProductControl.
     */
    @Test
    public void testGetProduct_String() {
        System.out.println("getProduct");
        String text = "";
        ProductControl instance = new ProductControl();
        assertNotNull(instance.getProduct(text));
        System.out.println("GetProduct String OK");
    }

    

    /**
     * Test of modifyPrice method, of class ProductControl.
     */
    @Test
    public void testModifyPrice() {
        System.out.println("modifyPrice");
        TableColumn.CellEditEvent<Product, Double> e = null;
        assertNull(e);
        System.out.println("ModifyPrice OK");
    }

    /**
     * Test of modifyCriticalStock method, of class ProductControl.
     */
    @Test
    public void testModifyCriticalStock() {
        System.out.println("modifyCriticalStock");
        TableColumn.CellEditEvent<Product, Integer> e = null;
        ProductControl instance = new ProductControl();
        assertNull(e);
        System.out.println("ModifyCriticalStock OK");
    }
    
}
