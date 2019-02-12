/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author acetip
 */
public class FlooringMasteryAuditDaoFileImpl implements FlooringMasteryAuditDao {
    
    private static final String AUDIT_FILE = "audit.txt";

    @Override
    public void writeLog(String entry) throws FlooringMasteryPersistenceException {
        
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        }catch(IOException e){
            throw new FlooringMasteryPersistenceException(
            "Could not write to log", e);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
        
    }
        
  
}
