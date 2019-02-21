/*





*/
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.service.InvalidDateException;
import com.sg.flooringmastery.service.InvalidOrderIdException;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author acetip
 */
public class FlooringMasteryController {
    
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;
    
    List<StateTax> taxList; // declare before run() so private methods can get access to it
    List<Product> productList;  // declare before run() so private methods can get access to it
    String todaysDate;
    String originalDate;  // will be used in editOrder() to write back to the original file with the correct date, instead of todaysDate
    

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;

    }



    public void run() throws FlooringMasteryPersistenceException{
        
       loadNestedHashmap(); // load the nested hsahmap in the Order Dao

        // get todays date
         todaysDate = retrieveTodaysDate();
          
        
        // load list of products from the file
         productList = getProductsFromFile();

        // load the list of taxes from the file
         taxList = getTaxesFromFile();
      
        boolean keepGoing = true;
        
        while(keepGoing){
        
        int menuSelection = getMenuChoice();
        
        switch(menuSelection){
            
            case 1:
                displayOrders();    // display orders
                break;
            case 2:
                addOrder();         // add an order
                break;
            case 3:
                editOrder();        // edit an order 
                break;
            case 4:
                removeOrder();      // remove an order
                break;
            case 5:
                saveCurrentWork();  // save all changes 
                keepGoing = false;
                break;
            case 6:
                quit();             // exit the prigram by changing boolean to false
                keepGoing = false;
                break;
            default:
                break;
        }
     }



    
    
    }

    private void loadNestedHashmap() throws FlooringMasteryPersistenceException{

        
        service.loadNestedHashmap();

    }
    
    private String retrieveTodaysDate(){
        
        return view.getTodaysDate();
    }
        
    
    private int getMenuChoice(){
        
       return view.printMainMenu();
       
    }
    
    private void displayOrders() throws FlooringMasteryPersistenceException  {

        try{
            String userDate = view.askForOrderDate();   //ask for date
            List<Order> orderList = service.getAllOrders(userDate);     // get the list of orders by date
                
            view.printOrderListByDate(orderList);
        }catch(InvalidDateException | NullPointerException e){
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    
     // return list of products from the file
    private List<Product> getProductsFromFile() throws FlooringMasteryPersistenceException{
        return service.getAllProducts();
    }
    
     // return list of taxes from the file
     private List<StateTax> getTaxesFromFile() throws FlooringMasteryPersistenceException{
        return service.getAllTaxes();
    }
    

    private void addOrder() throws FlooringMasteryPersistenceException{
        
        originalDate = todaysDate;  // set the date to write back to in saveCurrentWork() as today's date
        
        // ask for Customer's name, state, product they want, and area(sqFt)
        String customerName = view.askForCustomerName();
        

        // get the user's state so you can use the dao to search for the right tax in the next line of code
        // EX: IN
        String customerState = view.askForCustomerState(taxList);

        // ask for product type
        // EX: wood
        String customerProduct = view.askForCustomerProduct(productList);
        
         
        // ask for how much area (in SqFt.) the user wants
        // EX: 500.12
        BigDecimal customerArea = view.askForCustomerArea();
        
       
        // ask user if they want to proceed with the information they entered
        boolean confirm = view.askForConfirmation(customerName, customerState, customerProduct, customerArea);
       
        if(confirm == true){
            service.createFile(originalDate, todaysDate);   // create the file that will help generate the order ID, but do not persist until you click "save current work"
                // add the order to the hashmap but do not persist to the file
            service.createOrder(todaysDate, customerName, customerState, customerProduct, customerArea);
            view.printOrderSuccessMessage("ADDED");

            }else{
                view.printOrderFailureMessage("ADDED");

        }

    }

    private void editOrder () throws FlooringMasteryPersistenceException{

        // ask for date and validate it according to the format you set (MM/dd/yyyy)
        
        try{
        String userDate = view.askForOrderDate();
        originalDate = userDate;    // give the variable declared before run() the date the user entered so you can save to the correct file in saveCurrentWork()
        
        int userOrderNumber = view.askForOrderNumber();
        
        Order order = service.getOrder(userDate, userOrderNumber);    // get the order using input from user
        
        Order editedOrder = view.editOrderInfo(order, taxList, productList);  // prompt user to edit order info and return the order to be added back to the hashmap
        
        service.editOrder(originalDate, editedOrder);   // add the order to the hashmap which will overwrite the order with the same ID
        view.printOrderSuccessMessage("EDITED, or not if you didn't make any changes");
        }catch(InvalidDateException | InvalidOrderIdException e){
           
            view.displayErrorMessage(e.getMessage()); // if the user enters a date that does not exist
            
        }

        
    }
        
    private void removeOrder() throws FlooringMasteryPersistenceException{
        
        try{
            String userDate = view.askForOrderDate();
            originalDate = userDate;    // give the variable declared before run() the date the user entered so you can save to the correct file in saveCurrentWork()
        
            int userOrderNumber = view.askForOrderNumber();
            Order removedOrder = service.getOrder(userDate, userOrderNumber);    // get the order using input from user

            boolean keepLooping = true;
        
            while(keepLooping){
                String yesOrNo = view.displayOrderAndGetApproval(removedOrder); // display order and ask to remove it
                if(yesOrNo.equalsIgnoreCase("Y")){  // if user wants to remove the order, remove it
                    service.deleteOrder(originalDate, removedOrder);
                    view.printOrderSuccessMessage("REMOVED");   // tell the user the order was removed
                    keepLooping = false;
                }else if(yesOrNo.equalsIgnoreCase("N")){    // if not then don't
                 view.printOrderFailureMessage("REMOVED");
                 keepLooping = false;   // do nothing, but exit out of loop
                }else{                  // if they don't enter "Y" or "N"
                 keepLooping = true; // keep looping
                }
            }
        }catch(InvalidDateException | InvalidOrderIdException e){
           
            view.displayErrorMessage(e.getMessage()); // if the user enters a date that does not exist
        }
        
        
    }
    
    // needs to be able to compare date with todaysDate so you can write edits back to prope file, if date is not todaysDate, overwrite file instead of appending
    private void saveCurrentWork() throws FlooringMasteryPersistenceException{
        
       try{
       service.getAllOrdersToFile(); // write to the file
       view.saveMessage();
       }catch(NullPointerException e){
          view.handleNullPointerDate();
       }

    }
    
    private void quit() throws FlooringMasteryPersistenceException{
        // delete the file that was created if they added any orders
        
    }
    
    
}
