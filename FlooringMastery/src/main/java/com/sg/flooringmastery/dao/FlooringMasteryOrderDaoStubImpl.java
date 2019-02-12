///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vishnukdawah
 */
public class FlooringMasteryOrderDaoStubImpl implements FlooringMasteryOrderDao {

    private String onlyDate;
    private Order onlyOrder;
    private List<Order> orderList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();

    public FlooringMasteryOrderDaoStubImpl() {
        // dummy data
        onlyDate = "10022018";
        onlyOrder = new Order(87,"Customer", "IN", new BigDecimal("2.22"), "Wood", new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"));
    
        orderList.add(onlyOrder);
        
        dateList.add(onlyDate);
        
    }


    
    
    @Override
    public List<Order> getAllOrders(String date) throws FlooringMasteryPersistenceException {
        // no matter what happens when you call this method, it will return the list in this DaoStubImpl
        return orderList;
    }

    @Override
    public Order createOrder(String todaysDate, Order brandNew) throws FlooringMasteryPersistenceException {
        // if you try to create an order with an existing ID, this method will return that order(b/c of the return type being Order) (so when you assign it to something like Order newOrder, newOrder will be assigned this order)
        // if you pass in an Order that has an existing ID, it will return that Order object (If you can create the order because it was given a valid ID, create it)
        // if you pass in an Order that does not have an existing ID, it will return null(because it cannot be found (If the ID was invalid, then don't create it, return null)
        
        if(brandNew.getOrderId() == onlyOrder.getOrderId()){    // if the order you passed in is equal to the onlyOrder's, return onlyOrder
            return onlyOrder;
        }else{      // otherwise, don't return onlyOrder. Must return something, so return null
            return null;
        }
        
        
    }

    @Override
    public Order getOrder(String date, int orderId) throws FlooringMasteryPersistenceException {
        
        if(orderId == onlyOrder.getOrderId()){    // if the order you passed in is equal to the onlyOrder's, return onlyOrder
            return onlyOrder;
        }else{      // otherwise, don't return onlyOrder. Must return something, so return null
           orderId = 0;
            return null;
        }
        
    }

    @Override
    public Order editOrder(String date, Order editedOrder) throws FlooringMasteryPersistenceException {
       
        if(editedOrder.getOrderId() == onlyOrder.getOrderId()){    // if the order you passed in is equal to the onlyOrder's, return onlyOrder
            return onlyOrder;
        }else{      // otherwise, don't return onlyOrder. Must return something, so return null
            return null;
        }
        
    }

    @Override
    public Order deleteOrder(String date, Order order) throws FlooringMasteryPersistenceException {
        
        // if you try to remove an order with an existing ID, this method will return that order(b/c of the return type being Order) (so when you assign it to something like Order removedOrder, removedOrder will be assigned this order)
        if(order.getOrderId() == onlyOrder.getOrderId()){    // if the order you passed in is equal to the onlyOrder's, return onlyOrder
            return onlyOrder;
        }else{      // otherwise, don't return onlyOrder. Must return something, so return null
            return null;
        }
        
    }

    // Are These methods even necessary to have here?
    @Override
    public String createFile(String date, String todaysDate) throws FlooringMasteryPersistenceException {
        return "Q";
    }

    @Override
    public void loadNestedHashmap() throws FlooringMasteryPersistenceException {
    }

    @Override
    public void getAllOrdersToFile() throws FlooringMasteryPersistenceException {
    }

    @Override
    public List<String> getListOfDates() throws FlooringMasteryPersistenceException {
        return dateList;
    }
    
}
