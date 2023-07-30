/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import exceptions.LicenseNumberException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.connection.UserControl;
import models.entitys.User;
import models.entitys.encryption.SHAEncryption;
import models.entitys.persistence.SaveOwner;
import validations.Validation;
import views.Alerts;

/**
 * FXML Controller class
 *
 * @author Albert
 */
public class ManageUsersController implements Initializable {

    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> surname;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> licenseNumber;
    @FXML
    private TableColumn<User, Boolean> admin;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtLicenseNumber;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;
    @FXML
    private CheckBox cbAdmin;
    @FXML
    private Button btSave, btRemoveUser;
    @FXML
    private Label lblMessageSave;

    private ObservableList<User> items = FXCollections.observableArrayList();

    private UserControl userControl;

    private User selectedUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        

        userControl = new UserControl();
        
        initializeTable();

        addListenerTextFields();

        btSave.setDisable(true);   
        
        btRemoveUser.setDisable(true);

    }

    @FXML
    private void addUser(ActionEvent evt) throws IOException {

        try {
            String name = txtName.getText();
            String surname = txtSurname.getText();
            String licenseNumber = null;
            if (new Validation().licenseNumberValidate(txtLicenseNumber.getText())) {
                licenseNumber = txtLicenseNumber.getText();
            } else {
                throw new LicenseNumberException("License number's isn't correct");
            }
            String userName = txtUser.getText();
            String password = new SHAEncryption().Sha256(txtPassword.getText());
            boolean admin = cbAdmin.isSelected();

            User user = new User(name, surname, licenseNumber, userName, password, admin);

            boolean saved = new UserControl().createUser(user, lblMessageSave);

            if (saved) {

                txtName.setText("");
                txtSurname.setText("");
                txtLicenseNumber.setText("");
                txtUser.setText("");
                txtPassword.setText("");
                cbAdmin.setSelected(false);

            }

        } catch (LicenseNumberException e) {
            
            lblMessageSave.setText("El número de licencia no tiene el formato adecuado");
        }
        
        btSave.setDisable(true);

        setItems(items);

    }

    @FXML
    private void removeUser(ActionEvent evt) {        
        
        Alert alert = new Alerts(Alert.AlertType.CONFIRMATION, "Eliminar usuario", "¿Está seguro que desea eliminar a " + selectedUser.getName() + "?", "")
                .getAlert();
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            if (userControl.removeUser(selectedUser)){
                new Alerts(Alert.AlertType.INFORMATION, "Usuario eliminado", "Se ha eliminado a " +selectedUser.getName() + ".", null).getAlert().showAndWait();
            } else {
                new Alerts(Alert.AlertType.ERROR, "Usuario no eliminado", "No se ha eliminado a " + selectedUser.getName() + ".", null).getAlert().showAndWait();
            }
        }        

        setItems(items);
        btRemoveUser.setDisable(true);
    }

    private void setItems(ObservableList<User> items) {

        items.setAll(new UserControl().getUsers());
        tableUsers.setItems(items);

    }

    private void updateUser(TableColumn.CellEditEvent<User, String> event, String type) {
        userControl.updateUser(event, type, lblMessageSave);
        setItems(items);

    }

    private void activeButton() {

        lblMessageSave.setText("");

        if (!txtName.getText().isEmpty()) {
            if (!txtSurname.getText().isEmpty()) {
                if (!txtLicenseNumber.getText().isEmpty()) {
                    if (!txtUser.getText().isEmpty()) {
                        if (!txtPassword.getText().isEmpty()) {
                            btSave.setDisable(false);
                        } else {
                            btSave.setDisable(true);
                        }
                    }
                }
            }
        }
        
        if (selectedUser == null){
            btRemoveUser.setDisable(true);
        }else{
            btRemoveUser.setDisable(false);
        }

    }

    private void initializeTable() {
        
        tableUsers.setEditable(true);
        
        tableUsers.setOnMouseReleased(e -> activeButton());

        tableUsers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedUser = newValue;
                }
        );

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit((event) -> updateUser(event, "name"));

        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surname.setCellFactory(TextFieldTableCell.forTableColumn());
        surname.setOnEditCommit((event) -> updateUser(event, "surname"));

        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userName.setCellFactory(TextFieldTableCell.forTableColumn());
        userName.setOnEditCommit((event) -> updateUser(event, "userName"));

        licenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        licenseNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        licenseNumber.setOnEditCommit((event) -> updateUser(event, "licenseNumber"));

        admin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        admin.setCellFactory(p -> new TableCell<User, Boolean>() {

            private CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    User user = (User) getTableRow().getItem();
                    userControl.updateUser(user.getId(), checkBox.isSelected());
                });

            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(checkBox);
                    checkBox.setSelected(item);
                }
            }
        });
        
        setItems(items);
    }

    private void addListenerTextFields() {
        
        txtName.setOnKeyReleased(e -> activeButton());
        txtSurname.setOnKeyReleased(e -> activeButton());
        txtLicenseNumber.setOnKeyReleased(e -> activeButton());
        txtUser.setOnKeyReleased(e -> activeButton());
        txtPassword.setOnKeyReleased(e -> activeButton());
        
    }

}
