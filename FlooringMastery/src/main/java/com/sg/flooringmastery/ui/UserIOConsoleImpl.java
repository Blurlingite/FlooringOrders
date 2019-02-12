/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author acetip
 */
public class UserIOConsoleImpl implements UserIO {

    // Weird Maven bug, can't print on the same line with "System.out.print" so I had no choice but to print to the next line
    @Override
    public void print(String msg) {
        
        System.out.println(msg);
        
    }
    
    
    
    // take in an int and print to the console
    @Override
    public void printInt(int number){
        
        System.out.print(number);
        
    }
    
    // take in a BigDecimal and print to the consoles
    @Override
    public void printBigDecimal(BigDecimal bd){
        
        System.out.print(bd);
        
    }

    @Override
    public double readDouble(String prompt) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public float readFloat(String prompt) {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public int readInt(String prompt) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        String userInput =  sc.nextLine();
        userInput = userInput.trim();

        try{
         Integer.parseInt(userInput);
        }catch(NumberFormatException n){
            print("");
            print("You must enter a whole number");
            print("");
            return readInt(prompt);
        }
        return Integer.parseInt(userInput);
    
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        
        Scanner sc = new Scanner(System.in);
        int num = 0;
        print(prompt);
        
        try{
        String userChoice = sc.nextLine().trim();
        
         num = Integer.parseInt(userChoice);
        if(num >= min && num <= max ){
            num = num;
        }else{
            print("Must be within the range of " + min + " - " + max);
            return readInt(prompt, min, max);
        }
        }catch(NumberFormatException e){
            print("You must enter an integer");
            print("");

            return readInt(prompt, min, max);

        }  
        return num;
        
    }

    @Override
    public long readLong(String prompt) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    // ask for input and return the input
    @Override
    public String readString(String prompt) {
        
        print(prompt);  // print out te prompt
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.trim();

        while(input.contains(":")){
//        input = sc.nextLine(); 
            readString(prompt);
        }
//        input = input.trim();
        
        return input;   // return what the user inputs
    
    }
    
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        
        Scanner sc = new Scanner(System.in);
        
        BigDecimal bd;
        print(prompt);
        
        
   // wrap this BigDecimal in a try catch or else it will throw an NumberFormatException exception. 
        try{
            String input = sc.nextLine();
            input = input.trim();
            String validateInput = "";
       
            while(!input.contains(".")){     //while the input does not contains a decimal...
                print("Enter with decimals.  EX. 3.23");  // print an error message and...
                input = sc.nextLine();       // take in the input again and assign to input variable again
            }
        
            bd = new BigDecimal(input);  // since the BigDecimal constructor requires a String, you can put the user's input here

        }catch(NumberFormatException e){
            System.out.println("Enter a valid BigDecimal");
            return readBigDecimal(prompt);
        
        }
        return bd;
    
    }
    
    @Override
    public String convertBigDecimalToString(BigDecimal bd){
        
        String bigDecimalString = String.valueOf(bd.doubleValue());
        
        return bigDecimalString;
        
    }

    @Override
    public boolean readBooleanYes(String prompt) {
        Scanner sc = new Scanner(System.in);
        print(prompt);
        String userInput = sc.nextLine();
        userInput = userInput.trim();
        if(userInput.equalsIgnoreCase("Y")){
            return true;
        }else if(userInput.equalsIgnoreCase("N")){
            return false;
        }else if(userInput.equalsIgnoreCase("")){
            return false;
        }else{
            return readBooleanYes(prompt);
        }
        
    }

    // if you pick yes the loop should end, if no keep looping
    @Override
    public boolean readBooleanNo(String prompt) {
        
        Scanner sc = new Scanner(System.in);
        print(prompt);
        String userInput = sc.nextLine();
        userInput = userInput.trim();
        if(userInput.equalsIgnoreCase("N")){
            return true;
        }else if(userInput.equalsIgnoreCase("Y")){
            return false;
        }else{
            return readBooleanNo(prompt);
        }
        
    }
    
}
