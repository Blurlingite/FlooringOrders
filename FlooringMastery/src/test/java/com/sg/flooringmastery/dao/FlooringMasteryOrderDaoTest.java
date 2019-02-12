/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
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
public class FlooringMasteryOrderDaoTest {
    
    private FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoFileImpl();    //get access to the Dao adn the methods in it's Impl
     public List<Order> orderList;
     public String date = "11082121";
    
    public FlooringMasteryOrderDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FlooringMasteryPersistenceException {
        
      orderDao.loadNestedHashmap(); // load parent hashmap in OrderDao including the test date of 11082121
      orderList = orderDao.getAllOrders(date);    // get list from test file with 1 order in it
      

    }
    
    @After
    public void tearDown() throws FlooringMasteryPersistenceException {
        
        // after each test empty the Dao's hashmap, the set up will add back the onlyOrder from the file
        for(Order currentOrder : orderList){   // go through the list and remove every order to be in a known good state for testing the next method
        
             orderDao.deleteOrder(date, currentOrder);
      
        }
    
    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryOrderDao.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        
        // assert the setup put only 1 order in Dao
        assertEquals(1, orderDao.getAllOrders(date).size());
        
        // make a new order and assert that there are now 2 orders in Dao
        Order order = new Order(3, "Susan","MI",new BigDecimal("7.00"), "Tile", new BigDecimal("3.40"), new BigDecimal("8.15"), new BigDecimal("2.75"), new BigDecimal("12.66"), new BigDecimal("30.90"), new BigDecimal("5.61"), new BigDecimal("36.17"));
        orderDao.createOrder(date, order);

        assertEquals(2, orderDao.getAllOrders(date).size());
    }

    /**
     * Test of createOrder method, of class FlooringMasteryOrderDao.
     */
    @Test
    public void testCreateOrder() throws Exception {

        Order order = new Order(2, "Bartholomew","OH",new BigDecimal("7.00"), "Tile", new BigDecimal("3.40"), new BigDecimal("8.15"), new BigDecimal("2.75"), new BigDecimal("12.66"), new BigDecimal("30.90"), new BigDecimal("5.61"), new BigDecimal("36.17"));

        orderDao.createOrder(date, order);  // create the order
        
        Order fromDao = orderDao.getOrder(date, order.getOrderId());    // get the order you just created
        
        assertEquals(order, fromDao);   // both the order you made and the one added to the Dao must be equal (overridden toString() method helps actually compare the values of the orders, not just the address
        
    }

    /**
     * Test of getOrder method, of class FlooringMasteryOrderDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetOrder() throws Exception {
        Order order = new Order(1, "Lindsey","IN",new BigDecimal("6.00"), "Wood", new BigDecimal("4.40"), new BigDecimal("5.15"), new BigDecimal("4.75"), new BigDecimal("22.66"), new BigDecimal("20.90"), new BigDecimal("2.61"), new BigDecimal("46.17"));
        Order fromDao = orderDao.getOrder(date, 1); // get the only order (name: Lindsey) from parent hashmap
        
        assertEquals(order, fromDao);
    }

    /**
     * Test of editOrder method, of class FlooringMasteryOrderDao.
     */
    @Test
    public void testEditOrder() throws Exception {
        
        Order fromDao = orderDao.getOrder(date, 1);   // get the only order in Dao (Lindsey)
        
        
        // make changes to it but keep orderID the same
        Order changeName = new Order(1, "Alex","IN",new BigDecimal("6.00"), "Tile", new BigDecimal("4.40"), new BigDecimal("5.15"), new BigDecimal("4.75"), new BigDecimal("22.66"), new BigDecimal("20.90"), new BigDecimal("2.61"), new BigDecimal("46.17"));

        // edit the order
        Order editedOrder = orderDao.editOrder(date, changeName);
        
        //assert that the order you got before from Dao is not equal to the edited order and also assert that there is still only 1 order in the Dao
        assertNotEquals(fromDao, changeName);
        assertEquals(1, orderList.size());
        
    }

    /**
     * Test of deleteOrder method, of class FlooringMasteryOrderDao.
     */
    @Test
    public void testDeleteOrder() throws Exception {
        
        Order order1 = new Order(2, "Alex","IN",new BigDecimal("6.00"), "Tile", new BigDecimal("4.40"), new BigDecimal("5.15"), new BigDecimal("4.75"), new BigDecimal("22.66"), new BigDecimal("20.90"), new BigDecimal("2.61"), new BigDecimal("46.17"));
        Order addedOrder = orderDao.createOrder(date, order1);
        
        // there should now be 2 orders in the Dao
        assertEquals(2,orderDao.getAllOrders(date).size());
        
        // remove addedOrder
        Order removedOrder = orderDao.deleteOrder(date, addedOrder);
        
        // there should now be 1 order in Dao
        assertEquals(1,orderDao.getAllOrders(date).size());
        
        
        
    }

    // can't test otherwise empty file will cause data to not be written to files
    /**
     * Test of createFile method, of class FlooringMasteryOrderDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFile() throws Exception {
        // the name of the file that will be created if it does not exist already
//        String fakeFile = "Orders_01092099.txt";
//        String f = orderDao.createFile("01092099", "01092099");
//        
//        assertEquals(f, fakeFile);
        
    }

    /**
     * Test of loadNestedHashmap method, of class FlooringMasteryOrderDao.
     */
//    @Test
//    public void testLoadNestedHashmap() throws Exception {
//    }

    /**
     * Test of getAllOrdersToFile method, of class FlooringMasteryOrderDao.
     */
    @Test
    public void testGetAllOrdersToFile() throws Exception {
        
        // test to see if there are orders in Dao
        
        List<Order> listOfOrdersFromDao = orderDao.getAllOrders(date);
        
        assertNotNull(listOfOrdersFromDao);
        
        
    }

    /**
     * Test of getListOfDates method, of class FlooringMasteryOrderDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetListOfDates() throws Exception {
        
        List<String> dateList = orderDao.getListOfDates();
        
        assertNotNull(dateList);
        
    }

 
    
}
