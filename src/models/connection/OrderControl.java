/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.entitys.Order;
import models.entitys.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Albert
 */
public class OrderControl {
    
    private Session session;
    private Transaction tx;
    private boolean saved;
    private int removed;
    private SessionControl sessionControl = new SessionControl();
    
    
    public boolean createOrder(Order order) {        

        session = sessionControl.createConnection();

        try {

            session.beginTransaction();

            session.save(order);

            saved = true;

        } catch (Exception e) {
            e.printStackTrace();
            saved = false;
        } finally {

            sessionControl.closeConnection(session);

        }

        return saved;

    }
    
    public ObservableList<Order> getOrder(Product product){
        
        ObservableList<Order> obList = FXCollections.observableArrayList();
        
        session = sessionControl.createConnection();
        
        try{
            
            List<Order> list = session.createQuery("from Order where productId = '" + product.getProductId() + "'").getResultList();
            
            for (Order order : list){              
                
                int orderNum = order.getOrderNum();
                String deliveryNote = order.getDeliveryNote();
                LocalDate date = order.getDate();
                int amount = order.getAmount();
                
                obList.add(new Order(orderNum, deliveryNote, date, amount));
                
            }
            
            return obList;
            
        }finally{
            sessionControl.closeConnection(session);
        }
        
    }
}
