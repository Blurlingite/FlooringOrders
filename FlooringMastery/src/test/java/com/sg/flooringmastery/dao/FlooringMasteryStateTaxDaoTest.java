/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.StateTax;
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
public class FlooringMasteryStateTaxDaoTest {
    
    private FlooringMasteryStateTaxDao taxDao = new FlooringMasteryStateTaxDaoFileImpl();
    public List<StateTax> taxList;
    
    public FlooringMasteryStateTaxDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FlooringMasteryPersistenceException {
        
        taxList = taxDao.getAllTaxes();
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllTaxes method, of class FlooringMasteryStateTaxDao.
     */
    @Test
    public void testGetAllTaxes() throws Exception {
        
        assertNotNull(taxList);
    }

    /**
     * Test of readByAbbr method, of class FlooringMasteryStateTaxDao.
     */
    @Test
    public void testReadByAbbr() throws Exception {
        
        StateTax taxFromDao = taxDao.readByAbbr("IN");
        
        assertNotNull(taxFromDao);
        
        
    }

}
