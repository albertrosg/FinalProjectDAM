/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Class for throw an exception for phone number
 * @author Albert
 */
public class PhoneNumberException extends Exception{
    
    public PhoneNumberException(String message){
        super(message);
    }
    
}
