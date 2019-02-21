/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author acetip
 */
public class Product {
      
  private String productType;  
  private BigDecimal CostSqFt;
  private BigDecimal LaborCostSqFt;

    public Product(String productType, BigDecimal CostSqFt, BigDecimal LaborCostSqFt) {
        this.productType = productType;
        this.CostSqFt = CostSqFt;
        this.LaborCostSqFt = LaborCostSqFt;
    }


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostSqFt() {
        return CostSqFt;
    }

    public void setCostSqFt(BigDecimal CostSqFt) {
        this.CostSqFt = CostSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return LaborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal LaborCostSqFt) {
        this.LaborCostSqFt = LaborCostSqFt;
    }
  
  
    public BigDecimal multiplyProductBD(BigDecimal area, BigDecimal productAttribute){
        
        // get the total  cost and round the result to 2 decimal places
        BigDecimal cost = area.multiply(productAttribute);
        BigDecimal costScaled = cost.setScale(2, RoundingMode.HALF_UP);
        
        return costScaled;
    
    }

    @Override
    public String toString() {
        return "Product{" + "productType=" + productType + ", CostSqFt=" + CostSqFt + ", LaborCostSqFt=" + LaborCostSqFt + '}';
    }
    
    
}
