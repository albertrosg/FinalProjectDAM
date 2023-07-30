/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.connection;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import models.entitys.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import validations.Validation;

/**
 *
 * @author Albert
 */
public class UserControl {

    private Session session;
    private Transaction tx;
    private boolean saved;
    private SessionControl sessionControl = new SessionControl();

    public boolean createUser(User user, Label label) {

        session = sessionControl.createConnection();

        try {

            session.beginTransaction();

            session.save(user);

            saved = true;

        } catch (ConstraintViolationException e) {
            label.setText("El número de licencia ya existe en la base de datos");
            saved = false;
        } finally {

            sessionControl.closeConnection(session);

        }

        return saved;

    }
    
    public ObservableList<User> getUsers() {

        ObservableList<User> listView = FXCollections.observableArrayList();

        List<User> list;

        session = sessionControl.createConnection();

        try {

            session.beginTransaction();

            list = session.createQuery("from User").getResultList();

            for (User user : list) {

                int id = user.getId();
                String name = user.getName();
                String surname = user.getSurname();
                String licenseNumber = user.getLicenseNumber();
                String userN = user.getUserName();
                boolean admin = user.isAdmin();

                listView.add(new User(id, name, surname, licenseNumber, userN, admin));
            }

            return listView;

        } finally {

            sessionControl.closeConnection(session);

        }

    }

    public User getUser(String userName, String password, Label error) {

        User user = null;

        List<User> list;

        try {

            session = sessionControl.createConnection();

            session.beginTransaction();

            list = session.createQuery("from User us where us.userName='" + userName + "' and us.password='" + password + "'").getResultList();

            for (User userFound : list) {

                user = userFound;

            }

            return user;

        } catch (Exception e) {

            error.setText("A ocurrido un error en la conexión");
            e.printStackTrace();
            return user;

        } finally {

            sessionControl.closeConnection(session);

        }
    }

    public boolean removeUser(User user) {

        session = sessionControl.createConnection();

        try {

            tx = session.beginTransaction();

            session.delete(user);

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

    public void updateUser(TableColumn.CellEditEvent<User, String> event, String type, Label label) {

        session = sessionControl.createConnection();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            User user = event.getRowValue();

            String hql = "from User where id=" + user.getId();

            User temporalUser = (User) session.createQuery(hql).getSingleResult();

            boolean found = false;

            switch (type) {
                case "name":
                    temporalUser.setName(event.getNewValue());
                    break;
                case "surname":
                    temporalUser.setSurname(event.getNewValue());
                    break;
                case "userName":

                    if (event.getNewValue().equals(getUnique("userName"))) found = true;            

                    if (!found) {
                        temporalUser.setUserName(event.getNewValue());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Usuario repetido");
                        alert.setContentText("El nombre de usuario que está introduciendo ya se encuentra en la base de datos");
                        alert.showAndWait();
                    }
                    break;

                case "licenseNumber":

                    if (event.getNewValue().equals(getUnique("licenseNumber"))) found = true;                       
                    
                    if (!found) {
                        if (new Validation().licenseNumberValidate(event.getNewValue())) {
                            temporalUser.setLicenseNumber(event.getNewValue());
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Formato de licencia incorrecto");
                            alert.setContentText("La licencia debe ser un numero de cinco cifras");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Licencia repetida");
                        alert.setContentText("La licencia que está introduciendo ya se encuentra en la base de datos");
                        alert.showAndWait();
                    }

                    break;
            }

            user = temporalUser;

            session.update(user);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();

        } finally {

            sessionControl.closeConnection(session);

        }

    }

    public void updateUser(int id, boolean admin) {

        session = sessionControl.createConnection();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "from User where id=" + id;

            User user = (User) session.createQuery(hql).getSingleResult();

            user.setAdmin(admin);

            session.update(user);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();

        } finally {

            sessionControl.closeConnection(session);

        }

    }

    private String getUnique(String type) {

        String hql = "select " + type + " from User";

        return (String) session.createQuery(hql).getSingleResult();

    }

}
