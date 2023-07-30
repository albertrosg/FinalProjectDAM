/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Class for throw an exception for email
 * @author Albert
 */
public class EmailException extends Exception{
    
    public EmailException(String message){
        super(message);
    }
}
