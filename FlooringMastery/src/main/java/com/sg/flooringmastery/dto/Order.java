/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author acetip
 */
public class Order {
        
    
   private int orderId;
   private String customerName;
   
   // StateTax fields
   private String state;
   private BigDecimal taxRate;
   
   // Product fields
   String productType;
   BigDecimal materialCost;
   BigDecimal laborCost;
   
   
   private BigDecimal area;
   private BigDecimal totalMaterialCost;
   private BigDecimal totalLaborCost;
   private BigDecimal totalTax;
   private BigDecimal total;

    public Order(int orderId, String customerName, String state, BigDecimal taxRate, String productType,  BigDecimal area, BigDecimal materialCost, BigDecimal laborCost, BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal totalTax, BigDecimal total) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.state = state; 
        this.taxRate = taxRate;
        this.productType = productType;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.area = area;
        this.totalMaterialCost = totalMaterialCost;
        this.totalLaborCost = totalLaborCost;
        this.totalTax = totalTax;
        this.total = total;
    }
   
   

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }
    

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(BigDecimal totalLaborCost) {
        this.totalLaborCost = totalLaborCost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.orderId;
        hash = 83 * hash + Objects.hashCode(this.customerName);
        hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.taxRate);
        hash = 83 * hash + Objects.hashCode(this.productType);
        hash = 83 * hash + Objects.hashCode(this.materialCost);
        hash = 83 * hash + Objects.hashCode(this.laborCost);
        hash = 83 * hash + Objects.hashCode(this.area);
        hash = 83 * hash + Objects.hashCode(this.totalMaterialCost);
        hash = 83 * hash + Objects.hashCode(this.totalLaborCost);
        hash = 83 * hash + Objects.hashCode(this.totalTax);
        hash = 83 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.totalMaterialCost, other.totalMaterialCost)) {
            return false;
        }
        if (!Objects.equals(this.totalLaborCost, other.totalLaborCost)) {
            return false;
        }
        if (!Objects.equals(this.totalTax, other.totalTax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "  Order{" + "ORDER ID  =  " + orderId + ",  CUSTOMER NAME  =  " + customerName + 
                ",  STATE  =  " + state + ",  TAX RATE  =  " + taxRate + ",  PRODUCT TYPE  =  " + productType + 
                ",  MATERIAL COST  =  " + materialCost + ",  LABOR COST  =  " + laborCost + " , AREA  =  " + area +
                ",  TOTAL MATERIAL COST  =  " + totalMaterialCost + ",  TOTAL LABOR COST  =  " + totalLaborCost + 
                ",  TOTAL TAX  =  " + totalTax + ",  TOTAL  =  " + total + '}';
    }



    
}
