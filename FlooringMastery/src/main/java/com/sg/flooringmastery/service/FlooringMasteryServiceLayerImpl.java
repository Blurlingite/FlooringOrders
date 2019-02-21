/*
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryProductDao;
import com.sg.flooringmastery.dao.FlooringMasteryStateTaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author acetip
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {
    
    private FlooringMasteryOrderDao orderDao;
    private FlooringMasteryProductDao productDao;
    private FlooringMasteryStateTaxDao taxDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryProductDao productDao, FlooringMasteryStateTaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }
    
    
    @Override
    public List<Order> getAllOrders(String date) throws InvalidDateException, FlooringMasteryPersistenceException {
        
        validateDate(date);  // try to find the date, if you can't, throw the InvalidDateException
        return orderDao.getAllOrders(date);
        
    }

    

    @Override
    public Order createOrder(String todaysDate, String customerName, String customerState, String customerProduct, BigDecimal customerArea) throws FlooringMasteryPersistenceException {
        // find the Product object by type
        // EX: Wood
        Product chosenProduct = getProductByName(customerProduct);
        
        // search for the right tax by the State the user entered
        // EX: 6.00
        StateTax customerStateTax = readByAbbr(customerState);

        
        // get the total material cost and round the result to 2 decimal places
        // EX: 2,575.62
        BigDecimal materialCost = chosenProduct.multiplyProductBD(customerArea, chosenProduct.getCostSqFt());
        

        // get the total labor cost and round the result to 2 decimal places
        // EX: 2,375.57
        BigDecimal laborCost = chosenProduct.multiplyProductBD(customerArea, chosenProduct.getLaborCostSqFt());
   
        // get total before tax and scale it to 2 decimal places
        // EX: 4,951.19
        BigDecimal totalBeforeTax = customerStateTax.taxOperations(materialCost, laborCost, "+");
        
        // multiply that total by the tax rate and scale
        // EX: 29,707.14
        BigDecimal multiplyByTaxRate = customerStateTax.taxOperations(totalBeforeTax, customerStateTax.getTax(), "*"); 
          
        // divide that by 100 to get the actual amount of tax
        // initial a BigDecimal of 100 so you can divide by it
        BigDecimal hundred = new BigDecimal("100.00");
        BigDecimal hundredScaled = hundred.setScale(2, RoundingMode.HALF_UP);
        
        // find the actual amount of tax and scale it
        // EX: 297.07
        BigDecimal actualTax = customerStateTax.taxOperations(multiplyByTaxRate, hundredScaled, "/");

          
         // find the total cost by adding the cost before tax and actual tax and scale it
         // EX: 5,248.26
        BigDecimal total = customerStateTax.taxOperations(totalBeforeTax, actualTax, "+");
        
        // create the order
        Order brandNew = new Order(87,customerName, customerStateTax.getState(), customerStateTax.getTax(), chosenProduct.getProductType(), customerArea, chosenProduct.getCostSqFt(), chosenProduct.getLaborCostSqFt(), materialCost, laborCost, actualTax, total);

        return orderDao.createOrder(todaysDate, brandNew);
        
    }
    

    @Override
    public Order getOrder(String date, int orderId) throws InvalidDateException, InvalidOrderIdException, FlooringMasteryPersistenceException {
        
        validateDate(date); // try to find the date, if you can't, throw the InvalidDateException
        
        Order tryAndGetOrder = orderDao.getOrder(date, orderId);
        
        validateOrderID(tryAndGetOrder);
        return tryAndGetOrder;
        
    }
    

    @Override
    public Order editOrder(String date, Order editedOrder) throws FlooringMasteryPersistenceException {
        
        return orderDao.editOrder(date, editedOrder);
        
    }
    

    @Override
    public Order deleteOrder(String date, Order order) throws InvalidDateException, FlooringMasteryPersistenceException {
        
        return orderDao.deleteOrder(date, order);
        
    }
    

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        
        return productDao.getAllProducts();         
        
    }

    @Override
    public Product getProductByName(String name) throws FlooringMasteryPersistenceException {
        
        return productDao.getProductByName(name);
                
    }
    

    @Override
    public List<StateTax> getAllTaxes() throws FlooringMasteryPersistenceException {
        
        return taxDao.getAllTaxes();
    }
    

    @Override
    public StateTax readByAbbr(String stateAbbreviation) throws FlooringMasteryPersistenceException {
        
        return taxDao.readByAbbr(stateAbbreviation);
                
    }
    
    // needs to validate date because if the date doesn't exist and it tries to find the file by that date you will get a stack trace
    // use a helper method to validate date so you can reuse it in other methods that require date validation
    @Override
    public String createFile(String date, String todaysDate) throws FlooringMasteryPersistenceException {
        
       return orderDao.createFile(date, todaysDate);
    }
    

    // if date doesn't exist and you won't be able to find the file by that date
    private void validateDate(String date) throws InvalidDateException, FlooringMasteryPersistenceException{
        
        
        List<String> dateList = orderDao.getListOfDates();
        
       
        int dateComparison = 0; // if this variable stays at 0, the date could not be found
        for(String currentDate : dateList){
            if(date.equalsIgnoreCase(currentDate)){
                dateComparison++;
            }
        }
        
        if(dateComparison == 0){
            throw new InvalidDateException(
            "ERROR! The date could not be found!");
        }
        
    }
    
    // if the order Id can't be found
    private void validateOrderID(Order order) throws InvalidOrderIdException{
        
//        Order o = orderDao.getOrder(date, orderId);
        if(order == null){
                throw new InvalidOrderIdException(
                        "ERROR! That ID is Invalid!");
            }
        
    }
    

    @Override
    public void loadNestedHashmap() throws FlooringMasteryPersistenceException {
        
        orderDao.loadNestedHashmap();
        
    }
   
    
    @Override
    public void getAllOrdersToFile() throws FlooringMasteryPersistenceException{
        
        orderDao.getAllOrdersToFile();
    }

   
}
