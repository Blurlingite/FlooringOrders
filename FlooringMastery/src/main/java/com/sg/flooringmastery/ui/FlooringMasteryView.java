/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author acetip
 */
public class FlooringMasteryView {
   
    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    
    public String getTodaysDate(){
        
        // declare how you want the date to appear
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        
        // open up LocalDate object and use .now() to get today's date
        LocalDate ld = LocalDate.now();
        
        // assign the formatted date to a string and print it out
        String todaysDate =ld.format(formatter);
        
        return todaysDate;
    }
    
    public int printMainMenu(){
        
        io.print("");
        io.print("");
        io.print("");
        
        
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");
        
        
        
        io.print("");
        io.print("");
        io.print("");
        
        return io.readInt("Select from the above choices", 1, 6);

    }
//        
    public String displayOrderAndGetApproval(Order approval){
        
        io.print("Order: " + approval.getCustomerName() + " || " + approval.getState() +" || " + 
         " || " + approval.getProductType() + " || " + approval.getArea()+ " || " 
        );
        
        return io.readString("Remove this order? (Y/N)");
    
    }
//
//    public String AskForListOfOrdersByDate(String date){
//    
//    }
//
    public void printOrderListByDate(List<Order> orderList){
        
        for(Order order : orderList){
            io.print( order.getOrderId() + " ||| " + order.getCustomerName() + " ||| " + order.getState() + " ||| "
                    + order.getTaxRate() + " ||| " + order.getProductType() + " ||| " + order.getMaterialCost() + " ||| "
                    + order.getLaborCost() + " ||| " + order.getArea() + " ||| " + order.getMaterialCost() + " ||| " 
                    + order.getTotalLaborCost() + " ||| " + order.getTotalTax() + " ||| " + order.getTotal()
            );
        } 
    
    }

  
    public String askForCustomerName(){
        
        String askForName = "";
        
        while(askForName.length() == 0){
         askForName = io.readString("Please enter your name");
        }
        return askForName;
        
    }
    
    // find the right state tax by getting user input, search a list of taxes to find the right StateTax 
    public String askForCustomerState(List<StateTax> listOfTaxes){
        boolean again = true;
        String returnedState = "";  // what will be returned, which should be a valid State

        while(again){
            String customerState = io.readString("Please enter a valid state (OH, PA, MI, IN)");
            
            String stateCaps = taxCapitalize(customerState);
            
            StateTax other = new StateTax("tax", new BigDecimal("3.33"));   // give default values otherwise error appears
            for(StateTax tax : listOfTaxes){

                other = tax;
                if(stateCaps.equalsIgnoreCase(tax.getState())){  //if what the user enters is equal to one of the states in StateTax,
                    returnedState = stateCaps; 
                    again = false;
                }
            }
        }
  
        return returnedState;
    
    }
        
    public String askForCustomerProduct(List<Product> listOfProducts){
        
        boolean again = true;
        String returnedProduct = "";  // what will be returned, which should be a valid product type

        while(again){
            String customerProduct = io.readString("Please choose the available product you want (Carpet, Laminate, Tile, Wood)");
            String capitalProduct = productCapitalize(customerProduct);
        
            Product other = new Product("product", new BigDecimal("3.33"), new BigDecimal("3.33"));   // give default values otherwise error appears
            for(Product product : listOfProducts){

                other = product;
                if(capitalProduct.equalsIgnoreCase(product.getProductType())){  //if what the user enters is equal to one of the product types in Products,
                    returnedProduct = capitalProduct; 
                    again = false;
                }
            }
        }
  
         return returnedProduct;
        
     
  
        
    }
            
    public BigDecimal askForCustomerArea(){
        
        BigDecimal area = io.readBigDecimal("Please enter how much area");
        BigDecimal areaScaled = area.setScale(2, RoundingMode.HALF_UP);
        areaScaled = areaOverHundred(areaScaled);


        return areaScaled;
        
    }
    
    
    public String askForOrderDate(){
        
        boolean isProper = false;   // used to keep asking for a date until a valid one is entered
        String date = "";
        
        while(!isProper){
       
        String userDate = io.readString("Enter date");   // ask for the order date
        if(userDate.length() != 8 ){    // if the length of the date they entered is not 8 characters
            io.print("That is not a valid date!");
            return askForOrderDate();
        }
        // set the format
        DateFormat format = new SimpleDateFormat("MMddyyyy");
        
        format.setLenient(false);   // turn off leniet, leniet can interpret invalid formats as valid
        
        try{
            format.parse(userDate);   // try to extract a propererly formatted date out of the user's input
            date = userDate;        // give the date the properly formatted date that you can return it
            isProper = true;    // stop looping
        } catch (ParseException e) {
            io.print("Date " + userDate + " is not valid according to " +
               // SimpleDateFormat holds the pattern you defined (MM/dd/yyy) in this method. toPattern gets the String representation of it
                ((SimpleDateFormat) format).toPattern() + " pattern.");
        }
        }
        
        return date;
        
    }
   
    
    public int askForOrderNumber(){
    
       return io.readInt("Enter your order number");
 
    }

    // ask the user if they want to proceed with the information they entered
    public boolean askForConfirmation(String customerName, String state, String productType, BigDecimal area){
        
        io.print("Order: " + customerName + " || " + state +" || " + 
           productType + " || " + area 
        );
        
        String confirm = io.readString("Correct? (Y/N)");
        
        if(confirm.equalsIgnoreCase("Y")){
            return true;
        }else if(confirm.equalsIgnoreCase("N")){
            return false;
        }else{
            return askForConfirmation(customerName, state, productType, area);
        }
        
        
    }
    
    public void displayErrorMessage(String message){
         io.print(message);
    }
    
    
    public Order editOrderInfo(Order order, List<StateTax> taxList, List<Product> productList){
        
        // get the previous values
        String customerName = order.getCustomerName();
        String state = order.getState();
        String productType = order.getProductType();
        BigDecimal area = order.getArea();
        
        
        String askForNewName = "";
        boolean changeTheName = true;
        
        String askForNewState = "";
        boolean changeTheState = true;
        
        String askForNewProduct = "";
        boolean changeTheProductType = true;
        
        BigDecimal askForNewArea = order.getArea(); // just to initialze this variable
        boolean changeTheArea = true;
        
        
        

        
       io.print("Current Order Info: "); 
       
        io.print( order.getOrderId() + " ||| " + order.getCustomerName() + " ||| " + order.getState() + " ||| "
             + order.getProductType()  + " ||| " + order.getArea() 
        );
        
        io.print("");
       

        // ************************CHANGE NAME************************************
        
        while(changeTheName){
            changeTheName = io.readBooleanYes("Do you want to change the name? (Y/N or press ENTER to skip)");
            if(!changeTheName){ // if they don't want to change it, break out of the while loop
                customerName = order.getCustomerName(); // set the name to the original one if they don't want to change the name, then break the while loop
                break;
            }
            askForNewName = io.readString("Enter new name");    // otherwise, ask for a new name
            changeTheName = io.readBooleanNo("Are you sure? (Y/N)");  // returns true if No because of readBooleanNo
            if(!changeTheName){ // if the user is sure...
                customerName = askForNewName;   // assign their input to customerName and break out of the while loop
                io.print("Name change was successful!");
                break;
            }
 

        }
        
            
            
        // ************************CHANGE STATE************************************

            
            
        while(changeTheState){
            changeTheState = io.readBooleanYes("Do you want to change the state (OH, PA, MI, IN)? (Y/N or press ENTER to skip)");
            if(!changeTheState){ // if they don't want to change it, break out of the while loop
                state = order.getState(); // set the state to the original one if they don't want to change the state, then break the while loop
                break;
            }

            askForNewState = askForCustomerState(taxList);  // use this previous method to ask for a state that is available according to the list of taxes



            changeTheState = io.readBooleanNo("Are you sure? (Y/N)");  // returns true if No because of readBooleanNo
            if(!changeTheState){ // if the user is sure...
                state = askForNewState;   // assign their input to state and break out of the while loop
                io.print("State change was successful!");
                break;
            }
 
        }
        
        
        
        // ************************CHANGE PRODUCT TYPE************************************
        
        
        while(changeTheProductType){
            changeTheProductType = io.readBooleanYes("Do you want to change the product (Carpet, Laminate, Tile, Wood)? (Y/N or press ENTER to skip)");
            if(!changeTheProductType){ // if they don't want to change it, break out of the while loop
                productType = order.getProductType(); // set the product to the original one if they don't want to change the product, then break the while loop
                break;
            }

            askForNewProduct = askForCustomerProduct(productList);  // use this previous method to ask for a product that is available according to the list of products



            changeTheProductType = io.readBooleanNo("Are you sure? (Y/N)");  // returns true if No because of readBooleanNo
            if(!changeTheProductType){ // if the user is sure...
                productType = askForNewProduct;   // assign their input to product and break out of the while loop
                io.print("Product change was successful!");
                break;
            }
 
        }
        
        
        // ************************CHANGE AREA************************************

        while(changeTheArea){
            changeTheArea = io.readBooleanYes("Do you want to change the area? (Y/N or press ENTER to skip)");
            if(!changeTheArea){ // if they don't want to change it, break out of the while loop
                area = order.getArea(); // set the area to the original one if they don't want to change the area, then break the while loop
                break;
            }

            askForNewArea = askForCustomerArea();  // use this previous method to ask for a area that is available according to the list of area

            changeTheArea = io.readBooleanNo("Are you sure? (Y/N)");  // returns true if No because of readBooleanNo
            if(!changeTheArea){ // if the user is sure...
                area = askForNewArea;   // assign their input to area and break out of the while loop
                io.print("Area change was successful!");
                break;
            }
 
        }

        
       Order editedOrder = order;   // give the edited order the previous order's values, then use setter method to set the appropriate fields that could be changed
       editedOrder.setCustomerName(customerName);
       editedOrder.setState(state);
       editedOrder.setProductType(productType);
       editedOrder.setArea(area);
       
       
       return editedOrder;
        
    }
    
    public void handleNullPointerID(){
        io.print("");
        io.print("");
        io.print("");

        io.print("ERROR! The ID could not be found!");
    }
    
    public void handleNullPointerDate(){
        io.print("");
        io.print("");
        io.print("");

         io.print("ERROR! A date had no orders! CHANGES NOT SAVED!");
    }
    
 
    public String productCapitalize(String name){
        name = name.trim();
        String productFirstLetter = name.substring(0, 1);  // isolate the first letter of the product type (Ex. In "laminate" you would get "l"
        productFirstLetter = productFirstLetter.toUpperCase();  // change that letter to uppercase ("l" is now "L")
        
        String restOfWord = name.substring(1); // get a string that starts after the first letter (In "laminate" you would get "aminate"
        restOfWord = restOfWord.toLowerCase();   // make the rest of the word all lowercase
        
        String entireProductName = productFirstLetter + restOfWord;  // put the word back together and it will now start with a capitol letter
    
        return entireProductName;
    }
    
    public String taxCapitalize(String name){
        name = name.trim();
        String abbr = name.toUpperCase();  // convert the String to all caps so it can be found in the hashmap
    
        return abbr;
    }
    
    public void printOrderSuccessMessage(String event){
        io.print("Order successfully " + event );
    }
    
    public void printOrderFailureMessage(String event){
        io.print("Order was not " + event );
    }
    
    
    public void saveMessage(){
        io.print("Don't worry, all your changes were saved") ;
    }
    
    public BigDecimal areaOverHundred(BigDecimal currentArea){
        
//        BigDecimal limit = new BigDecimal("1.11");
        BigDecimal limitScaled = currentArea.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal limitReScaled = currentArea;
        
        BigDecimal hundred = new BigDecimal("100.00");
        BigDecimal hundredScaled = hundred.setScale(2, RoundingMode.HALF_UP);
        
        int bigDecimalComparison = currentArea.compareTo(hundredScaled);  // comapre what the user entered as area with "100.00"
        
        while(bigDecimalComparison == 1){
            
            limitScaled = io.readBigDecimal("Please enter an amount less than or equal to 100.00");
            limitReScaled = limitScaled.setScale(2, RoundingMode.HALF_UP);
            bigDecimalComparison = limitReScaled.compareTo(hundredScaled);  // comapre what the user entered as area with "100.00"
 
        }
        return limitReScaled;
    }
    

}
