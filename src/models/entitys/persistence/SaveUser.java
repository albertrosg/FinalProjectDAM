/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import models.entitys.User;

/**
 *
 * @author Albert
 */
public class SaveUser {
    
    private final String url = System.getProperty("user.dir") + "\\src\\temp\\user.bin";
    
    public void saveUser(User user){
    
        try{
            File file = new File(url);
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            
            oos.writeObject(user);
            
            oos.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public User loadUser(){
        
        User user = null;
    
        try{
            File file = new File(url);
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);
            
            user = (User) ois.readObject();
            
            ois.close();
            is.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        
        return user;
    
    }
    
    public void removeUser(){
        
        File file = new File(url);
        file.delete();
        
    }
    
}
