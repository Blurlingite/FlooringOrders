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
public class StateTax {
     
    private String state;
    private BigDecimal tax;

    
    public StateTax(String state, BigDecimal tax) {
        this.state = state;
        this.tax = tax;
    }
    

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    
    
    
    public BigDecimal taxOperations(BigDecimal taxOperand1, BigDecimal taxOperand2, String operand){
        
        // what you will return after performing a math operation
        BigDecimal returnBd = new BigDecimal("0.00");
        BigDecimal returnBdScaled = returnBd.setScale(2, RoundingMode.HALF_UP);
        
        switch(operand){
            case "+":
                BigDecimal plusOperand = taxOperand1.add(taxOperand2);
                BigDecimal plusOperandScaled = plusOperand.setScale(2, RoundingMode.HALF_UP);
                returnBdScaled = plusOperandScaled;
                break;
            case "-":
                BigDecimal minusOperand = taxOperand1.subtract(taxOperand2);
                BigDecimal minusOperandScaled = minusOperand.setScale(2, RoundingMode.HALF_UP);
                returnBdScaled = minusOperandScaled;
                break;
            case "*":
                BigDecimal multOperand = taxOperand1.multiply(taxOperand2);
                BigDecimal multOperandScaled = multOperand.setScale(2, RoundingMode.HALF_UP);
                returnBdScaled = multOperandScaled;
                break;
            case "/":
                BigDecimal divOperand = taxOperand1.divide(taxOperand2);
                BigDecimal divOperandScaled = divOperand.setScale(2, RoundingMode.HALF_UP);
                returnBdScaled = divOperandScaled;
                break;
            default:
                break;
                
                
        }
        
        return returnBdScaled;
    
    }

    @Override
    public String toString() {
        return "StateTax{" + "state=" + state + ", tax=" + tax + '}';
    }

  
}
