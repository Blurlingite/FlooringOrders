/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.StateTax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author acetip
 */
public class FlooringMasteryStateTaxDaoFileImpl implements FlooringMasteryStateTaxDao {
    
    public static final String TAX_FILE = "Taxes.txt";
    public static final String DELIMITER = "::::";
    
    
    private Map<String, StateTax> allTaxes = new HashMap<>(); 
    



    @Override
    public List<StateTax> getAllTaxes() throws FlooringMasteryPersistenceException {
        loadTaxes();
        return new ArrayList<StateTax>(allTaxes.values());
        
    }

    @Override
    public StateTax readByAbbr(String stateAbbreviation) throws FlooringMasteryPersistenceException { 
        String abbr = stateAbbreviation.toUpperCase();  // convert the String to all caps so it can be found in the hashmap
        return allTaxes.get(abbr);
        
    }
      
    
    private void loadTaxes() throws FlooringMasteryPersistenceException{
        
        Scanner sc;
        
        try{
            
            sc = new Scanner(
                new BufferedReader(
                    new FileReader(TAX_FILE)));
        }catch(FileNotFoundException e){
                    
            throw new FlooringMasteryPersistenceException(
            "Could not Product data into memory", e);
                    
            }
       
     
            
        // hold the current line of the file
        String currentLine;
        
        // holds pieces of data of a  single line, but the line is split by the delimiter and each piece is a new index of this array
        String[] currentTokens;

        // while there is still another line in the file that hasn't been read yet
        while(sc.hasNextLine()){
            
            // set that line to currentLine
            currentLine = sc.nextLine();
            
            // split that line by the delimiter and put data in currentTokens array
            currentTokens = currentLine.split(DELIMITER);
            
            
            
            BigDecimal tax = new BigDecimal(currentTokens[1]);
            BigDecimal taxScaled = tax.setScale(2, RoundingMode.HALF_UP);

            
        
            StateTax fromFile = new StateTax(currentTokens[0], taxScaled);
            
            allTaxes.put(fromFile.getState(), fromFile);
        }
        
        sc.close();
        
    }
 
  
}
