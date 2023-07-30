/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validations;

/**
 *
 * @author Albert
 */
public class Validation {

    public boolean dniValidate(String dni) {
        if (dni.matches("[0-9]{8}[A-Z]")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean emailValidate(String email) {
        if (email.matches("[a-zA-Z]{1}[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.][a-zA-Z]{1,10}")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean phoneNumberValidate(String phoneNumber){
        if (phoneNumber.matches("[0-9]{9}")){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean licenseNumberValidate(String licenseNumber){
        if (licenseNumber.matches("[0-9]{5}")){
            return true;
        }else{
            return false;
        }
    }

}
