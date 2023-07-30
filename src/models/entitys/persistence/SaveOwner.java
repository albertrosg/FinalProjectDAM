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
import models.entitys.Owner;


/**
 *
 * @author Albert
 */
public class SaveOwner {
    
    private final String url = System.getProperty("user.dir") + "\\src\\temp\\owner.bin";
    
    public void saveOwner(Owner owner){
    
        try{
            File file = new File(url);
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            
            oos.writeObject(owner);
            
            oos.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public Owner loadOwner(){
        
        Owner owner = null;
    
        try{
            File file = new File(url);
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);
            
            owner = (Owner) ois.readObject();
            
            ois.close();
            is.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        
        return owner;
    
    }
    
    public void removeOwner(){
        
        File file = new File(url);
        file.delete();
        
    }
    
}
