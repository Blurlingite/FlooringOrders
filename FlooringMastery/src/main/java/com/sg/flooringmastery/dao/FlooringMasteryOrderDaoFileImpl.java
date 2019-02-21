/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao {
    
    public String ORDER_FILE = "Orders_MMDDYYYY.txt";
    public static final String DELIMITER = "::::";
    
    private Map<Integer, Order> allOrders = new HashMap<>();    // <Order ID, Order>

    private Map<String, Map<Integer, Order>> ordersByDate = new HashMap<String, Map<Integer, Order>>();    //<String orderDate, allOrders hashmap>

    
    

    
    // only returns what is in memeory, no file persistence. Used in writeOrders() so I don't have a StackOverFlow error. Before, in writeOrders, I was calling a method that had a writeOrders() call inside it
    @Override
    public List<Order> getAllOrders(String date) throws FlooringMasteryPersistenceException {
        
        Map<Integer, Order> hm = ordersByDate.get(date); // get the hashmap by the date
        // when the file is empty the map will be null, so to avoid error, return an empty ArrayList instead, that way it skips that empty file
        if(hm == null){
            return new ArrayList<>();
        }else{
            return new ArrayList<>(hm.values());   // get and return the list of orders from the <Integer, Order> hashmap

        }
    }


    @Override
    public Order createOrder(String todaysDate, Order brandNew) throws FlooringMasteryPersistenceException {
        
        int newOrderId = generateOrderId(todaysDate);   // generate the order ID by next available slot in the file
        if(newOrderId == 0){    // male the file start at 1
            newOrderId = 1;
        }
        brandNew.setOrderId(newOrderId);  // give the order you want to create a new order ID of orderNum

        Map<Integer,Order> hm =ordersByDate.get(todaysDate);
        if(hm == null){ // hm is null in the line before this because you can't assign a hashmap a value that way?
            ordersByDate.put(todaysDate, hm = new HashMap<>()); // make hm a new hashmap and then put it in the parent hashmap
        }
        
        hm.put(brandNew.getOrderId(), brandNew);    // put the added order in 
        ordersByDate.put(todaysDate, hm); // make hm a new hashmap and then put it in the parent hashmap

        return hm.put(brandNew.getOrderId(), brandNew);
        
    }

    @Override
    public Order getOrder(String date, int orderId) throws FlooringMasteryPersistenceException {
        
        Map<Integer,Order> getHash =ordersByDate.get(date);
        if(getHash == null){ // getHash is null in the line before this because you can't assign a hashmap a value that way?
            ordersByDate.put(date, getHash = new HashMap<>()); // make getHash a new hashmap and then put it in the parent hashmap
        }
        
        return getHash.get(orderId);
        
    }

    @Override
    public Order editOrder(String date, Order editedOrder) throws FlooringMasteryPersistenceException {
        // NEED TO PUT IN PARENT HASHMAP LIKE IN REMOVE ORDER
        Map<Integer,Order> editHash =ordersByDate.get(date);
        if(editHash == null){ // editHash is null in the line before this because you can't assign a hashmap a value that way?
            ordersByDate.put(date, editHash = new HashMap<>()); // make editHash a new hashmap and then put it in the parent hashmap
        }
        editHash.put(editedOrder.getOrderId(), editedOrder); 
        ordersByDate.put(date, editHash);
        return editHash.put(editedOrder.getOrderId(), editedOrder);    // replace the order with new info, don't assign a new Order ID like in createOrder()

    }

    // to remove an order
    @Override
    public Order deleteOrder(String date, Order order) throws FlooringMasteryPersistenceException {
        Map<Integer,Order> childHash =ordersByDate.get(date);
        if(childHash == null){ // childHash is null in the line before this because you can't assign a hashmap a value that way?
            ordersByDate.put(date, childHash = new HashMap<>()); // make childHash a new hashmap and then put it in the parent hashmap
        }
        return childHash.remove(order.getOrderId());
        
    }
    

    
        // create a new file with the order's date unless it already exists and write to that file
    @Override
    public String createFile(String date, String todaysDate) throws FlooringMasteryPersistenceException{
        
        String fileName = "";
         
        if(date.equalsIgnoreCase(todaysDate)){  // if the date is today's date, make the file for today's date which will set the ORDER_FILE variable     
             fileName = "Orders_" + todaysDate  + ".txt";    // what you will all the file
        }else{  // otherwise, use the date that was entered by the user and set ORDER_FILE to this date instead
             fileName = "Orders_" + date  + ".txt";    // what you will all the file
        }
        
            try {
                
                // find the directory that java was run from (in this case it's in your Netbeasns project folder)
                String workingDirectory = System.getProperty("user.dir");   
			
			
                String absoluteFilePath = "";
			
                //absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
                absoluteFilePath = workingDirectory + File.separator + fileName;
               // File.separator is either / or \ that is used to split up the path to a specific file. For example on Windows it is \ or C:\Documents\Test
               
               
                System.out.println("Final filepath : " + absoluteFilePath);
			
			
                File file = new File(absoluteFilePath);
               // if the file was created
                if (file.createNewFile()) {
                    System.out.println("File is created!");
                } else {
                    
                    System.out.println("File already exists!");
                }

            } catch (IOException e) {
              throw new FlooringMasteryPersistenceException(
              "COULD NOT PERSIST TO FILE",e);
            }
            
            // set the filename to the public (no longer static final) string variable
            this.ORDER_FILE = fileName;
        
        return fileName;
        
    }
    
    
    // load a certain file's data by passing a date
    private void loadOrders(String date) throws FlooringMasteryPersistenceException{
        
        String filename = "Orders_" + date + ".txt";
        Scanner scanner;
	    
                try {
                    // Create Scanner for reading the file
                    scanner = new Scanner(
                         new BufferedReader(
                                    new FileReader(filename)));
                } catch (FileNotFoundException e) { 
                    throw new FlooringMasteryPersistenceException(
                         "-_- Could not load data into memory.", e);
                }
             // currentLine holds the most recent line read from the file
	    String currentLine;

	    String[] currentTokens;

	    while (scanner.hasNextLine()) {
	        // get the next line in the file
	        currentLine = scanner.nextLine();
	        // break up the line into tokens
	        currentTokens = currentLine.split(DELIMITER);
                
                // convert each field in the file into a field of the Order DTO's constructor
                int orderNumber = Integer.parseInt(currentTokens[0]);
                String customerName = currentTokens[1];
                String state = currentTokens[2];
                
                BigDecimal taxRate = new BigDecimal(currentTokens[3]);
                BigDecimal taxRateScaled = taxRate.setScale(2, RoundingMode.HALF_UP);
                
                String productType = currentTokens[4];
                
                BigDecimal materialCost = new BigDecimal(currentTokens[5]);
                BigDecimal materialCostScaled = materialCost.setScale(2, RoundingMode.HALF_UP);
                
                BigDecimal laborCost = new BigDecimal(currentTokens[6]);
                BigDecimal laborCostScaled = laborCost.setScale(2, RoundingMode.HALF_UP);
                
                BigDecimal area = new BigDecimal(currentTokens[7]);
                BigDecimal areaScaled = area.setScale(2, RoundingMode.HALF_UP);
                
                BigDecimal totalMaterialCost = new BigDecimal(currentTokens[8]);
                BigDecimal totalMaterialCostScaled = totalMaterialCost.setScale(2, RoundingMode.HALF_UP);
                
                BigDecimal totalLaborCost = new BigDecimal(currentTokens[9]);
                BigDecimal totalLaborCostScaled = totalLaborCost.setScale(2, RoundingMode.HALF_UP);
                
                BigDecimal totalTax = new BigDecimal(currentTokens[10]);
                BigDecimal totalTaxScaled = totalTax.setScale(2, RoundingMode.HALF_UP);
                
                BigDecimal total = new BigDecimal(currentTokens[11]);
                BigDecimal totalScaled = total.setScale(2, RoundingMode.HALF_UP);
                
                

                Order currentOrder = new Order(orderNumber, customerName, state, taxRateScaled, productType, materialCostScaled, laborCostScaled, areaScaled, totalMaterialCostScaled, totalLaborCostScaled, totalTaxScaled, totalScaled);
        
                
                Map<Integer,Order> hm = ordersByDate.get(date); // get the child hashmap by date
                if(hm == null){ // hm is null in the line before this because you can't assign a hashmap a value that way?
                    ordersByDate.put(date, hm = new HashMap<>()); // make hm a new hashmap and then put it in the parent hashmap
                }
                
                hm.put(currentOrder.getOrderId(), currentOrder); // put in current order to child hashmap
                ordersByDate.put(date, hm); // put child hashmap with current order in parent hashmap
                
            }
            scanner.close();
    }
    

    
    // boolean edit is used to determine how to write to the file (append or overwrite). If it is true, that means an order was edited and the data should be overwritten
    private void writeOrders(List<String> listOfDates) throws FlooringMasteryPersistenceException{
        
        String date = "";   // date set by the loop that loops through the list of dates
        String fileName = "";    // the file name that will be set by the loop, so that you can write to each file
        
        for(String currentDate: listOfDates){
            
            date = currentDate; // give the variable a date, which will also set the file name
            fileName = "Orders_" + date + ".txt";
            
            
        // the FileWriter will write to the auto generated file assigned to fileName
        
        // need to initialize out because of if, else if and else statements
        PrintWriter out;

        try{
                out = new PrintWriter(new FileWriter(fileName)); // put ,true so it appends to the file instead of overwriting it (for adding)

        }catch(IOException e){
            throw new FlooringMasteryPersistenceException(
            "COULD NOT WRITE TO FILE",e);
        }
                
        //*****************************************************************//
        // Write list to file
            
        // get the list by the date from the hashmap
        List<Order> orderList = this.getAllOrders(date);    // get the list of orders by a certain date

            
        // go through the list until all orders are written to the file
        for(Order currentOrder: orderList){
            out.println(currentOrder.getOrderId() + DELIMITER
            + currentOrder.getCustomerName() + DELIMITER
            + currentOrder.getState() + DELIMITER
            + currentOrder.getTaxRate()+ DELIMITER
            + currentOrder.getProductType()+ DELIMITER
            + currentOrder.getArea() + DELIMITER
            + currentOrder.getMaterialCost() + DELIMITER
            + currentOrder.getLaborCost()+ DELIMITER
            + currentOrder.getTotalMaterialCost() + DELIMITER
            + currentOrder.getTotalLaborCost() + DELIMITER
            + currentOrder.getTotalTax() + DELIMITER
            + currentOrder.getTotal() + DELIMITER
                        
            );
                
            out.flush();
       
        }
            
        out.close();
        }
    }
    
    @Override
    public void loadNestedHashmap() throws FlooringMasteryPersistenceException{
        
        String dateToLoad = ""; 
        
        List<String> listofDates = getListOfDates();  // get the dates of every file in the directory
        
        for(String s : listofDates){
            
            dateToLoad = s;
            loadOrders(dateToLoad);     // load by each date in listofDates

        }

    }
    
    // this will write changes to all files
    @Override
    public void getAllOrdersToFile() throws FlooringMasteryPersistenceException{
        
        writeOrders(getListOfDates()); // make a new file with date as title and add orders to it
        
    }
    

    private int generateOrderId(String date) throws FlooringMasteryPersistenceException{
        
        String fileName = "Orders_" + date + ".txt";
        int lineCounter = 1;    // count how many lines there are in the file, start at 1 so you don't overwrite the last line in the file
        Scanner scanner;
	    
	try {
	    // Create Scanner for reading the file
	    scanner = new Scanner(
	            new BufferedReader(
	                    new FileReader(fileName)));
	} catch (FileNotFoundException e) {
	    throw new FlooringMasteryPersistenceException(
	            "-_- Could not load roster data into memory.", e);
	}
	// currentLine holds the most recent line read from the file

	// Go through file line by line, decoding each line into a 
	// Item object.
	// Process while we have more lines in the file
        
        Map<Integer, Order> hm = ordersByDate.get(date);
        if(hm == null){     // the hashmap is null when empty so add an order with an ID of 1
            lineCounter = 1;
        }else{
            lineCounter = (hm.size()) + 1;
        }
           
        scanner.close();
        return lineCounter;
        }
    
       
    
    // gets a list of every date in the directory
    @Override
    public List<String> getListOfDates() throws FlooringMasteryPersistenceException {
       
        List<String> listOfDates = new ArrayList<>();
        String dateToLoad = ""; 
        String fileDirectory = System.getProperty("user.dir");  // get the directory of where java was ran (this project's folder)
        
        File folder = new File(fileDirectory);  // turn the directory string into a file
        File[] listOfFiles = folder.listFiles();    // get the list of files in the directory
        
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
      
                String nameOfFile = listOfFiles[i].getName();   // get the name of the file
                String numbersOnly = nameOfFile.replaceAll("[\\D]", "");  // replace all non-number characters with whitespace

                if(numbersOnly.equals("")){  // if there were no numbers in the file name (ex. pom.xml), do nothing
    
                }else{
                    int dateOfFile = Integer.parseInt(numbersOnly);  // get the numbers part of the file name
                    int lengthOfdate = String.valueOf(dateOfFile).length();    // get the length of the int by converting it to a String and using .length
                    if(lengthOfdate < numbersOnly.length()){     // if the leading 0 got chopped off when parsing
                            dateToLoad = "0"+ dateOfFile;       // add it back
                        }else{
                            dateToLoad = Integer.toString(dateOfFile);    // otherwise if there were no leading 0s, set to the String version of dateOfFile, NOT dateToLoad, as that will have the previous date
                        }
                    listOfDates.add(dateToLoad);
                    }
        
                }
    
            }
                
        return listOfDates;

        }
    

    
  
}