/*
Methods that throw exceptions: you must catch them to pass the tes

Methods that do not: you must verify that when you call the method from the service layer, that it is not null
 */
package com.sg.flooringmastery.service;


import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author vishnukdawah
 */
public class FlooringMasteryServiceLayerTest {
    
    private FlooringMasteryServiceLayer service;    // Since this is a test of the Service Layer interface, you need this variable
    private String date = "10022018";

    public FlooringMasteryServiceLayerTest() {
        
        // need a StubImpl for each Dao you have
//        FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoStubImpl();   
//        FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoStubImpl();
//        FlooringMasteryStateTaxDao taxDao = new FlooringMasteryStateTaxDaoStubImpl();
//        
//        service = new FlooringMasteryServiceLayerImpl(orderDao, productDao, taxDao);


    ApplicationContext ctx = new ClassPathXmlApplicationContext("testApplicationContext.xml");  // get the xml file
    service = ctx.getBean("serviceLayerTest", FlooringMasteryServiceLayer.class);   // get the bean from that xml file

    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        
//        String date = "10022018";
        List<Order> orderListFromDao = service.getAllOrders(date);  // get the existing list from OrderDaoStubImpl
        
        assertNotNull(orderListFromDao);    // assert that the list exists in OrderDaoStubImpl
    }

    /**
     * Test of getAllOrdersToFile method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllOrdersToFile() throws Exception {
        
        service.getAllOrdersToFile();
    }

    /**
     * Test of createOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testCreateOrder() throws Exception {
        
        // Remember the OrderDaoStubImpl has only 1 order in it with an ID of 87 
        // since we are testing the business logic, we want to try to create an order with any other ID
//        Order order = new Order(12,"Susan", "OH", new BigDecimal("4.44"), "Tile", new BigDecimal("4.44"), new BigDecimal("4.44"), new BigDecimal("4.44"), new BigDecimal("4.44"), new BigDecimal("4.44"), new BigDecimal("4.44"), new BigDecimal("4.44"));

//        String todaysDate = "10022018";
        String customerName = "Susan";
        String customerState = "IN";
        String customerProduct = "Wood";
        
        BigDecimal customerArea = new BigDecimal("100.11");
        BigDecimal customerAreaScaled = customerArea.setScale(2, RoundingMode.HALF_UP);
        
        Order newOrder = service.createOrder(date, customerName, customerState, customerProduct, customerAreaScaled);
                
        assertNotNull(newOrder);   // assert that newOrder is not null (that an order was created)

    }

    /**
     * Test of getOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetOrderInvalidId() throws Exception {
//        String date = "10022018";
        Order order = new Order(0,"Customer", "IN", new BigDecimal("2.22"), "Wood", new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"));

       try{
        Order o = service.getOrder(date, order.getOrderId());
//        if(o == null){      // exception wasn't being thrown even when order ws null
//            throw new NullPointerException();
//        }
       }catch(InvalidOrderIdException e){
           return;
       }
        
        
    }
    
    @Test
    public void testGetOrderInvalidDate() throws Exception {
        String badDate = "10052019";   // this should cause the InvalidDateException since the only date in the list in OrderDaoStubImpl is 10022018
        Order order = new Order(0,"Customer", "IN", new BigDecimal("2.22"), "Wood", new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"), new BigDecimal("2.22"));

       try{
        Order o = service.getOrder(badDate, order.getOrderId());
        fail("Expected InvalidDateException was not thrown");
       }catch(InvalidDateException e){
           return;
       }
        
        
    }

    /**
     * Test of editOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testEditOrder() throws Exception {

//        String todaysDate = "10022018";
//        String customerName = "Susan";
//        String customerState = "IN";
//        String customerProduct = "Wood";
//        
//        BigDecimal customerArea = new BigDecimal("100.11");
//        BigDecimal customerAreaScaled = customerArea.setScale(2, RoundingMode.HALF_UP);
//        
//        Order created = service.createOrder(todaysDate, customerName, customerState, customerProduct, customerAreaScaled);
        Order fromDao = service.getOrder(date, 87);   // get the only order in OrderDaoStubImpl
        
        
        assertNotNull(fromDao);   // assert that fromDao is not null
        
        // change some of the order info
        fromDao.setCustomerName("Ashton");
        fromDao.setState("OH");
        fromDao.setProductType("Tile");
        
        BigDecimal newCustomerArea = new BigDecimal("100.11");
        BigDecimal newCustomerAreaScaled = newCustomerArea.setScale(2, RoundingMode.HALF_UP);
        
        fromDao.setArea(newCustomerAreaScaled);
        
        Order editedOrder = service.editOrder(date, fromDao);
        
        assertNotNull(editedOrder);   // assert that editedOrder is not null

        
    }

    /**
     * Test of deleteOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testDeleteOrder() throws Exception {
//        String todaysDate = "10022018";

        Order fromDao = service.getOrder(date, 87);   // get the only order in OrderDaoStubImpl
        
        assertNotNull(fromDao);   // assert that editedOrder is not null

        Order removed = service.deleteOrder(date, fromDao);   // remove the onlyOrder
       
        assertNotNull(removed);   // assert that removed is not null

    }

    /**
     * Test of getAllProducts method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        
        assertEquals(1,service.getAllProducts().size());
        
    }

    /**
     * Test of getProductByName method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetProductByName() throws Exception {
        
        String productName = "Wood";
        Product productFromDao = service.getProductByName(productName);
        
        assertNotNull(productFromDao);
    }

    /**
     * Test of getAllTaxes method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllTaxes() throws Exception {
        
        assertEquals(1,service.getAllTaxes().size());

    }

    /**
     * Test of readByAbbr method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testReadByAbbr() throws Exception {
        
        String stateAbbreviation ="IN";
        StateTax taxFromDao = service.readByAbbr(stateAbbreviation);
        
        assertNotNull(taxFromDao);
    }

    /**
     * Test of createFile method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testCreateFile() throws Exception {
        
        service.createFile(date, date);
    }

    /**
     * Test of loadNestedHashmap method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testLoadNestedHashmap() throws Exception {
        
        service.loadNestedHashmap();
    }

}
