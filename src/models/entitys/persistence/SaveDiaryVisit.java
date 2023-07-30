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
import models.DiaryVisit;

/**
 *
 * @author Albert
 */
public class SaveDiaryVisit {
    
    private final String url = System.getProperty("user.dir") + "\\src\\temp\\diaryVisit.bin";
    
    public void saveDiaryVisit(DiaryVisit diaryVisit){
    
        try{
            File file = new File(url);
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            
            oos.writeObject(diaryVisit);
            
            oos.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public DiaryVisit loadDiaryVisit(){
        
        DiaryVisit diaryVisit = null;
    
        try{
            File file = new File(url);
            InputStream is = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is);
            
            diaryVisit = (DiaryVisit) ois.readObject();
            
            ois.close();
            is.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        
        return diaryVisit;
    
    }
    
    public void removeDiaryVisit(){
        
        File file = new File(url);
        file.delete();
        
    }
    
}
