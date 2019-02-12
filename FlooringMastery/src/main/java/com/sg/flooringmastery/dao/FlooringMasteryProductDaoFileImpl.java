/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
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
public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {
        
   public static final String PRODUCT_FILE = "Products.txt";
   public static final String DELIMITER = "::::";
    
    
    private Map<String, Product> allProducts = new HashMap<>(); 

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        
        loadProducts();
        return new ArrayList<Product>(allProducts.values());
        
        
    }

    @Override
    public Product getProductByName(String name) throws FlooringMasteryPersistenceException {
        
        String productFirstLetter = name.substring(0, 1);  // isolate the first letter of the product type (Ex. In "laminate" you would get "l"
        productFirstLetter = productFirstLetter.toUpperCase();  // change that letter to uppercase ("l" is now "L")
        
        String restOfWord = name.substring(1); // get a string that starts after the first letter (In "laminate" you would get "aminate"
        restOfWord = restOfWord.toLowerCase();   // make the rest of the word all lowercase
        
        String entireProductName = productFirstLetter + restOfWord;  // put the word back together and it will now start with a capitol letter
        Product retrievedProduct = allProducts.get(entireProductName);
        return retrievedProduct;
    }
    
    
    private void loadProducts() throws FlooringMasteryPersistenceException{
        
        
        
        Scanner sc;
        
        try{
            
            sc = new Scanner(
                new BufferedReader(
                    new FileReader(PRODUCT_FILE)));
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
            
            
            
            BigDecimal CostSqFt = new BigDecimal(currentTokens[1]);
            BigDecimal CostSqFtScaled = CostSqFt.setScale(2, RoundingMode.HALF_UP);
            
            BigDecimal LaborCostSqFt = new BigDecimal(currentTokens[2]);
            BigDecimal LaborCostSqFtScaled = LaborCostSqFt.setScale(2, RoundingMode.HALF_UP);

            
        
            Product fromFile = new Product(currentTokens[0], CostSqFtScaled, LaborCostSqFtScaled);
            
            allProducts.put(fromFile.getProductType(), fromFile);
        }
        
        sc.close();
    }
        
        
    }
    
    