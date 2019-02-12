/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.advice;

import com.sg.flooringmastery.dao.FlooringMasteryAuditDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author acetip
 */
public class LoggingAdvice {
        
    FlooringMasteryAuditDao logger;

    public LoggingAdvice(FlooringMasteryAuditDao logger) {
        this.logger = logger;
    }
    
    public void writeReturns(JoinPoint jp){
        
        Object[] args = jp.getArgs();   // use getArgs to get all the arguments passed into the method that this advice is being applied to
        String auditEntry = jp.getSignature().getName() + ": "; // get the method's signature so you can get the name of the method
        for(Object currentArg : args){  // go through each argument that the method got and add them to the audit entry
            auditEntry = auditEntry + currentArg + "   ";   // take each argument and log it to audit file
        }
        try{
            logger.writeLog(auditEntry);
        }catch(FlooringMasteryPersistenceException e){
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");    
        }
        
    }
    
    // logs only exceptions to audit log
    public void writeExceptions(JoinPoint jp, Exception ex){    // Exception ex comes from xml file "throwing"
        
        Object[] args = jp.getArgs();
        String auditEntry = ex.getClass().getSimpleName() + ": ";   // get the name of the exception and add it to the audit entry
        
        for(Object exception : args){   // go through the arguments (exceptions) you got when the method threw an exception(s)
            
           auditEntry += exception;   // take each argument (exception) and log it to audit file
            
        }
        
        try{
            logger.writeLog(auditEntry);
        }catch(FlooringMasteryPersistenceException e){
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");    
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
      
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
}
