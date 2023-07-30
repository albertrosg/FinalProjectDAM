/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.entitys.PetRace;
import models.entitys.PetType;
import org.hibernate.Session;

/**
 *
 * @author Albert
 */
public class TypeControl {
    
    SessionControl sessionControl = new SessionControl();
    Session session = null;
    
    public ObservableList<String> getRace(String typeName) {

        ObservableList<String> listView = FXCollections.observableArrayList();

        List<PetRace> list = null;

        session = sessionControl.createConnection();

        try {

            session.beginTransaction();

            PetType petType = (PetType) session.createQuery("from PetType where nameType='" + typeName + "'").getSingleResult();

            int type = petType.getTypeId();

            list = session.createQuery("from PetRace where typeId='" + type + "'").getResultList();

            for (PetRace petRace : list) {

                listView.add(petRace.getNameRace());
            }

        } finally {

            sessionControl.closeConnection(session);

        }

        return listView;

    }

    public ObservableList<String> getType() {

        ObservableList<String> listView = FXCollections.observableArrayList();

        List<PetType> list = null;

        session = sessionControl.createConnection();

        try {

            session.beginTransaction();

            list = session.createQuery("from PetType").getResultList();

            for (PetType petType : list) {

                listView.add(petType.getNameType());
            }

        } finally {

            sessionControl.closeConnection(session);

        }

        return listView;

    }
    
}
