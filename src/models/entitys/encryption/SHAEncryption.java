/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys.encryption;

import java.security.MessageDigest;

/**
 *
 * @author Albert
 */
public class SHAEncryption {
    
    public String Sha256 (String cadena){
        StringBuffer ch = new StringBuffer();
        try {
            MessageDigest md =MessageDigest.getInstance("sha");
            byte[] cifrado = md.digest(cadena.getBytes());
            for (int i =0; i<cifrado.length;i++){
                ch.append(Integer.toHexString(0xFF & cifrado[i]));
            }            
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return ch.toString();
    }   
}
