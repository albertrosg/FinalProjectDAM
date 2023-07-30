/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entitys.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import models.entitys.Visit;

/**
 *
 * @author Albert
 */
public class SaveVisit {
    
    private final String url = System.getProperty("user.dir") + "\\src\\temp\\visit.bin";
    
    private final File file = new File(url);
    
    public void saveVisit(Visit visit){
        
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {            
            
            os = new FileOutputStream(file);
            oos = new ObjectOutputStream(os);
            oos.writeObject(visit);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
                os.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }       
    }
    
    public Visit loadVisit(){
        
        InputStream is = null;
        ObjectInputStream ois = null;
        Visit visit = null;
        
        try {          
            
            if (file.exists()){
            
            is = new FileInputStream(file);
            ois = new ObjectInputStream(is);
            visit = (Visit) ois.readObject();
            
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (file.exists()){
                    ois.close();
                    is.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return visit;
        
    }
    
    public void removerVisit(){
        file.delete();
    }
}
