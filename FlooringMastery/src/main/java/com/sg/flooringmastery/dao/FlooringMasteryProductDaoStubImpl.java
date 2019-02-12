/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vishnukdawah
 */
public class FlooringMasteryProductDaoStubImpl implements FlooringMasteryProductDao {

    Product onlyProduct;
    List<Product> productList = new ArrayList<>();

    public FlooringMasteryProductDaoStubImpl() {
        onlyProduct = new Product("Wood", new BigDecimal("2.22"), new BigDecimal("2.22"));
        
        productList.add(onlyProduct);
    }
    
    
    
    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        
        return productList;
    }

    @Override
    public Product getProductByName(String name) throws FlooringMasteryPersistenceException {
        
        if(name.equalsIgnoreCase("Wood")){    // must match what is in constructor
            return onlyProduct;
        }else{
            return null;
        }
    }
    
}