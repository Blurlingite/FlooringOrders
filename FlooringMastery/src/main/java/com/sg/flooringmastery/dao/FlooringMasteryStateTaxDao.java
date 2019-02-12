/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.StateTax;
import java.util.List;

/**
 *
 * @author acetip
 */
public interface FlooringMasteryStateTaxDao {
        
    
    
    public List<StateTax> getAllTaxes() throws FlooringMasteryPersistenceException;
    
    public StateTax readByAbbr(String stateAbbreviation) throws FlooringMasteryPersistenceException;
    
  
}
