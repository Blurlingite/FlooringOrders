/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author acetip
 */
public interface FlooringMasteryOrderDao {
       
    public List<Order> getAllOrders(String date) throws FlooringMasteryPersistenceException;

    public Order createOrder(String todaysDate, Order brandNew) throws FlooringMasteryPersistenceException;
     
    public Order getOrder(String date, int orderId) throws FlooringMasteryPersistenceException;
    
    public Order editOrder(String date, Order editedOrder) throws FlooringMasteryPersistenceException;
    
    public Order deleteOrder(String date, Order order) throws FlooringMasteryPersistenceException;
    
    public String createFile(String date, String todaysDate) throws FlooringMasteryPersistenceException;
    
    public void loadNestedHashmap() throws FlooringMasteryPersistenceException;

    public void getAllOrdersToFile() throws FlooringMasteryPersistenceException;

    public List<String> getListOfDates() throws FlooringMasteryPersistenceException;
  
}
