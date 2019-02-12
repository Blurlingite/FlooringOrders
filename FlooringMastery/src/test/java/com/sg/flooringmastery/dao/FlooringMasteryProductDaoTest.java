/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vishnukdawah
 */
public class FlooringMasteryProductDaoTest {
    
    private FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoFileImpl();
    public List<Product> productList;
    
    public FlooringMasteryProductDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FlooringMasteryPersistenceException {
        
        productList = productDao.getAllProducts();
    }
    
    @After
    public void tearDown() {
        
    }

    /**
     * Test of getAllProducts method, of class FlooringMasteryProductDao.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        
        assertNotNull(productList);
        
        
    }

    /**
     * Test of getProductByName method, of class FlooringMasteryProductDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetProductByName() throws Exception {
        
        Product productFromDao = productDao.getProductByName("Wood");
        
        assertNotNull(productFromDao);
        
    }


    
}
