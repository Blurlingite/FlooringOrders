/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author vishnukdawah
 */
public class InvalidOrderIdException extends Exception {

    public InvalidOrderIdException(String message) {
        super(message);
    }

    public InvalidOrderIdException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
