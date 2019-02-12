/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vishnukdawah
 */
public class FlooringMasteryStateTaxDaoStubImpl implements FlooringMasteryStateTaxDao {
    StateTax onlyTax;
    List<StateTax> taxList = new ArrayList<>();

    public FlooringMasteryStateTaxDaoStubImpl() {
        onlyTax = new StateTax("IN", new BigDecimal("2.22"));
        taxList.add(onlyTax);
    }
    @Override
    public List<StateTax> getAllTaxes() throws FlooringMasteryPersistenceException {
        
        return taxList;
    }

    @Override
    public StateTax readByAbbr(String stateAbbreviation) throws FlooringMasteryPersistenceException {
        
        if(stateAbbreviation.equalsIgnoreCase("IN")){
            return onlyTax;
        }else{
            return null;
        }
        
    }
    
}