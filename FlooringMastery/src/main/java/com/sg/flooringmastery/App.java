/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.service.InvalidDateException;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author acetip
 */
public class App {
        
    public static void main(String[] args) throws FlooringMasteryPersistenceException {
        String training = "Training";
        String production = "Prod";
        
        String mode = production;
        
        if(mode.equals("Prod")){
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("mainApplicationContext.xml");
        FlooringMasteryController controller =
                ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
        }else{
            ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("mainApplicationContext.xml");
        FlooringMasteryController controller =
                ctx.getBean("trainingController", FlooringMasteryController.class);
        controller.run();
        }
                
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
}
