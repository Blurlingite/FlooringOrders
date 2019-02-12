/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author acetip
 */
public interface FlooringMasteryServiceLayer {
        
    public List<Order> getAllOrders(String date) throws InvalidDateException, FlooringMasteryPersistenceException;
    
    public void getAllOrdersToFile() throws FlooringMasteryPersistenceException;

    public Order createOrder(String todaysDate, String customerName, String customerState, String customerProduct, BigDecimal customerArea) throws FlooringMasteryPersistenceException;
     
    public Order getOrder(String date, int orderId) throws InvalidDateException, InvalidOrderIdException,  FlooringMasteryPersistenceException;
    
    public Order editOrder(String date, Order editedOrder) throws FlooringMasteryPersistenceException;
    
    public Order deleteOrder(String date, Order order) throws InvalidDateException, FlooringMasteryPersistenceException;      
    
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
    
    public Product getProductByName(String name) throws FlooringMasteryPersistenceException;  
    
    public List<StateTax> getAllTaxes() throws FlooringMasteryPersistenceException;
    
    public StateTax readByAbbr(String stateAbbreviation) throws FlooringMasteryPersistenceException;
    
    public String createFile(String date, String todaysDate) throws FlooringMasteryPersistenceException;

    public void loadNestedHashmap() throws FlooringMasteryPersistenceException;
    
  
}
