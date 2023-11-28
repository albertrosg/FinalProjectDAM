/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import models.entitys.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Albert
 */
public class ProductControl {
    
    private Session session;
    private Transaction tx;    
    private int removed;
    private SessionControl sessionControl = new SessionControl();
    
    public boolean createProduct(Product product){
        
        session = sessionControl.createConnection();

        try {

            tx = session.beginTransaction();

            session.save(product);
            
            tx.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        } finally {

            sessionControl.closeConnection(session);

        }       
        
    }
    
    public ObservableList getProduct(){
        
        ObservableList<Product> listView = FXCollections.observableArrayList();
        
        List<Product> list;
        
        session = sessionControl.createConnection();
        
        try{
            
            session.beginTransaction();
            
            list = session.createQuery("from Product").getResultList();
            
            for (Product product : list){
                
                String id = product.getProductId();
                String name = product.getName();
                double price = product.getPrice();
                int stock = product.getStock();
                int criticalStock = product.getCriticalStock();
                
                listView.add(new Product(id, name, price, stock, criticalStock));
                
            }
            
            return listView;
                    
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }
    
    public Product getProductPay(String name){
        
        Product product = null;
        
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            Query query = session.createQuery("from Product where name = :name");
            
            query.setParameter("name", name);
            
            product = (Product) query.getSingleResult();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally{
            session.close();
            sessionControl.closeConnection(session);
        }
        
        return product;
        
    }
    
    public ObservableList getProduct(String text){
        
        ObservableList<Product> listView = FXCollections.observableArrayList();
        
        List<Product> list;
        
        session = sessionControl.createConnection();
        
        try{
            
            session.beginTransaction();
            
            list = session.createQuery("from Product where name like '" + text + "%'").getResultList();
            
            for (Product product : list){
                
                String id = product.getProductId();
                String name = product.getName();
                double price = product.getPrice();
                int stock = product.getStock();
                int criticalStock = product.getCriticalStock();
                
                listView.add(new Product(id, name, price, stock, criticalStock));
                
            }
            
            return listView;
                    
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }
    
    public boolean removeProduct(Product product){
        
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            session.delete(product);
            
            tx.commit();
            
            return true;
            
        } catch (Exception e){
            
            e.printStackTrace();
            
            tx.rollback();
            
            return false;
            
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }   
    

    public void modifyPrice(TableColumn.CellEditEvent<Product, Double> e) {
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            Product product = e.getRowValue();
            
            String hql = "from Product where product_id = '" + product.getProductId() + "'";
            
            Product temporalProduct = (Product) session.createQuery(hql).getSingleResult();
            
            
            temporalProduct.setPrice(e.getNewValue());                   
                        
            product = temporalProduct;
            
            session.update(product);
            
            tx.commit();
            
        }catch (Exception ex){
            ex.printStackTrace();
            tx.rollback();
        }finally{
            sessionControl.closeConnection(session);
        }
    }

    public void modifyCriticalStock(TableColumn.CellEditEvent<Product, Integer> e) {
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            Product product = e.getRowValue();
            
            String hql = "from Product where product_id = '" + product.getProductId() + "'";
            
            Product temporalProduct = (Product) session.createQuery(hql).getSingleResult();
            
            
            temporalProduct.setCriticalStock(e.getNewValue());                   
                        
            product = temporalProduct;
            
            session.update(product);
            
            tx.commit();
            
        }catch (Exception ex){
            ex.printStackTrace();
            tx.rollback();
        }finally{
            session.close();
            sessionControl.closeConnection(session);
        }
    }
    
    public boolean updateStock(Product product, int amount){
        
        session = sessionControl.createConnection();
        
        try{
            
            tx = session.beginTransaction();
            
            product.setStock(product.getStock() - amount);
            
            session.update(product);
            
            tx.commit();
            
            return true;
            
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
            return false;
        }finally{
            session.close();
            sessionControl.closeConnection(session);
        }
        
    }
    
}
