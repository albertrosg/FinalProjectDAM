/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import exceptions.DniException;
import exceptions.EmailException;
import exceptions.PhoneNumberException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import models.DiaryVisit;
import models.Hours;
import models.TypeAppoinment;
import models.connection.OwnerControl;
import models.connection.PetControl;
import models.connection.TypeControl;
import models.connection.VisitControl;
import models.entitys.Owner;
import models.entitys.Pet;
import models.entitys.User;
import models.entitys.Visit;
import models.entitys.persistence.SaveDiaryVisit;
import models.entitys.persistence.SaveOwner;
import models.entitys.persistence.SavePet;
import models.entitys.persistence.SaveUser;
import models.entitys.persistence.SaveVisit;
import validations.Validation;
import views.Alerts;

/**
 * Controller for manage the view manageOwner
 *
 * @author Albert
 */
public class ManageOwnerController implements Initializable {

    @FXML
    private TextField txtNameOwner, txtSurnameOwner, txtDniOwner, txtEmailOwner, txtPhoneNumberOwner, txtAddressOwner, txtSearchOwner;
    @FXML
    private TableView<Pet> tvPet;
    @FXML
    private TableColumn<Pet, String> tcPetName, tcType, tcChipNumber;
    @FXML
    private TableView<Owner> tvOwner;
    @FXML
    private TableColumn<Owner, String> tcNameOwner, tcSurnameOwner, tcDNIOwner;
    @FXML
    private TextField txtNamePet, txtChipNumber, txtWeightPet;
    @FXML
    private Button btModifyOwner, btAddPet, btAddOwner, btModifyPet, btRemovePet, btRemoveOwner, btAccesEntry;
    @FXML
    private ComboBox<String> cbTypePet, cbRacePet, cbAppoinmentType;
    @FXML
    private ComboBox<LocalTime> cbAppoinmentHour;
    @FXML
    private DatePicker dpBirthDatePet, dpAppoinmentDate;
    @FXML
    private TextArea txtComment;
    @FXML
    private RadioButton rbFemale, rbMale;
    @FXML
    private TextArea txtOwnerDetails;
    @FXML
    private Label lblErrorOwner, lblErrorPet;
    @FXML
    private AnchorPane anchorPane;

    private Owner owner, selectedOwner;

    private Pet pet, selectedPet;

    private ObservableList<String> itemsCbTypePet = FXCollections.observableArrayList();

    private ObservableList<String> itemsCbRacePet = FXCollections.observableArrayList();

    private final ObservableList<Owner> itemsOwner = FXCollections.observableArrayList();

    private OwnerControl ownerControl;

    private PetControl petControl;

    private FlowPane flowPane;

    private List<LocalTime> busyHours = null;
    
    private String enteredText = "";

    /**
     * Initializes the controller class.
     *
     * @param url The URL of the view
     * @param rb The resource of the RecourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ownerControl = new OwnerControl();

        petControl = new PetControl();

        selectedOwner = null;

        selectedPet = null;

        initializeTables();

        initializeComboboxs();

        initializeDatePickers();

        addListeners();

        disableButtons();

        if (new File(System.getProperty("user.dir") + "\\src\\temp\\diaryVisit.bin").exists()) {
            DiaryVisit diaryVisit = new SaveDiaryVisit().loadDiaryVisit();
            dpAppoinmentDate.setDisable(false);
            dpAppoinmentDate.setValue(diaryVisit.getDate());
            cbAppoinmentHour.setDisable(false);
            cbAppoinmentHour.setValue(diaryVisit.getHour());
            new SaveDiaryVisit().removeDiaryVisit();
        }

        allOwner();

    }

    /**
     * Method for add owner
     *
     * @param event The event of the button
     */
    @FXML
    private void addOwner(ActionEvent event) {

        try {

            String name = txtNameOwner.getText();
            String surname = txtSurnameOwner.getText();
            String dni = null;
            if (new Validation().dniValidate(txtDniOwner.getText())) {
                dni = txtDniOwner.getText();
            } else {
                throw new DniException("DNI's format isn't correct");
            }
            String email = null;
            if (new Validation().emailValidate(txtEmailOwner.getText())) {
                email = txtEmailOwner.getText();
            } else {
                throw new EmailException("Email's format isn't correct");
            }
            String phoneNumber = null;
            if (new Validation().phoneNumberValidate(txtPhoneNumberOwner.getText())) {
                phoneNumber = txtPhoneNumberOwner.getText();
            } else {
                throw new PhoneNumberException("Phone number's format isn't correct");
            }
            String address = txtAddressOwner.getText();

            if (ownerControl.createOwner(new Owner(dni, name, surname, phoneNumber, email, address), lblErrorOwner, txtOwnerDetails)) {
                allOwner();
                cleanTextOwner();

            }
        } catch (DniException e) {
            lblErrorOwner.setText("EL DNI no tiene el formato correcto");
        } catch (EmailException ex) {
            lblErrorOwner.setText("El email no tiene el formato correcto");
        } catch (PhoneNumberException ex) {
            lblErrorOwner.setText("El teléfono no tiene un formato correcto");
        }

    }

    /**
     * Method for modify owner
     *
     * @param event The event of the button
     */
    @FXML
    private void modifyOwner(ActionEvent event) throws IOException {

        new WindowControl().openOtherWindow("modifyOwner", "Modificar cliente");
        allOwner();
        actionOwner();

    }

    /**
     * Method for remove owner
     *
     * @param event The event of the button
     */
    @FXML
    private void removeOwner(ActionEvent event) throws IOException {

        Alert alert = new Alerts(Alert.AlertType.CONFIRMATION, "Eliminar propietario", "Esta seguro que desea eliminar a " + new SaveOwner().loadOwner().getName(), "")
                .getAlert();
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            if (new OwnerControl().removeOwner(owner)) {
                new Alerts(Alert.AlertType.INFORMATION, "Propietario eliminado", "Se ha eliminado a " + new SaveOwner().loadOwner().getName() + ".", null).getAlert().showAndWait();
            } else {
                new Alerts(Alert.AlertType.ERROR, "Propietario no eliminado", "No se ha eliminado a " + new SaveOwner().loadOwner().getName() + ".", null).getAlert().showAndWait();
            }
        }
        txtOwnerDetails.setText("");
        selectedOwner = null;
        new SaveOwner().removeOwner();
        btModifyOwner.setDisable(true);
        btRemoveOwner.setDisable(true);
        allOwner();
        allPet();
    }

    /**
     * Method for add pet
     *
     * @param event The event of the button
     */
    @FXML
    private void addPet(ActionEvent event) {

        try {

            owner = ownerControl.getOwnerTable(selectedOwner.getDni());

            String sex = "";

            String name = txtNamePet.getText();
            String type = cbTypePet.getValue();
            String race = cbRacePet.getValue();
            if (rbFemale.isSelected()) {
                sex = "Hembra";
            } else if (rbMale.isSelected()) {
                sex = "Macho";
            }
            String chipNumber = txtChipNumber.getText();
            Double weigth = Double.parseDouble(txtWeightPet.getText());
            LocalDate birthDate = dpBirthDatePet.getValue();

            Pet pet = new Pet(chipNumber, name, type, race, sex, weigth, birthDate, owner);

            if (petControl.addPet(pet, lblErrorPet)) {
                txtNamePet.setText("");
                cbRacePet.setValue(itemsCbRacePet.get(0));
                cbTypePet.setValue(itemsCbTypePet.get(0));
                rbFemale.setSelected(false);
                rbMale.setSelected(false);
                txtChipNumber.setText("");
                txtWeightPet.setText("");
                dpBirthDatePet.setValue(null);
                btAddPet.setDisable(true);
            }

        } catch (NumberFormatException e) {
            lblErrorPet.setText("El peso debe ser un número.");
        }

        allPet();
    }

    @FXML
    private void modifyPet(ActionEvent event) {

    }

    /**
     * Method for remove pet
     *
     * @param event The event of the button
     */
    @FXML
    private void removePet(ActionEvent event) throws IOException {

        Alert alert = new Alerts(Alert.AlertType.CONFIRMATION, "Eliminar mascota", "¿Está seguro que desea eliminar a " + new SavePet().loadPet().getName() + "?", "")
                .getAlert();
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            if (new PetControl().removePet(pet)) {
                new Alerts(Alert.AlertType.INFORMATION, "Mascota eliminada", "Se ha eliminado a " + new SavePet().loadPet().getName() + ".", null).getAlert().showAndWait();
            } else {
                new Alerts(Alert.AlertType.ERROR, "Mascota no eliminada", "No se ha eliminado a " + new SavePet().loadPet().getName() + ".", null).getAlert().showAndWait();
            }
        }
        selectedPet = null;
        new SavePet().removePet();
        btModifyOwner.setDisable(true);
        btRemoveOwner.setDisable(true);
        btRemovePet.setDisable(true);
        btAccesEntry.setDisable(true);
        allPet();

    }

    /**
     * Method for add appoinment
     *
     * @param event The event of the button
     */
    @FXML
    private void makeAppoinment(ActionEvent event) throws IOException {

        String type = cbAppoinmentType.getValue();
        LocalTime hour = cbAppoinmentHour.getValue();
        LocalDate date = dpAppoinmentDate.getValue();
        String comment = txtComment.getText();
        User user = new SaveUser().loadUser();
        Pet pet = new SavePet().loadPet();
        Owner owner = new SaveOwner().loadOwner();

        Visit visit = new Visit(type, hour, date, comment, user, pet, owner);

        if (new VisitControl().createVisit(visit)) {
            new Alerts(Alert.AlertType.INFORMATION, "Visita añadida", "Se ha añadido correctamente la visita.\nYa puede consultarla en su agenda.", null)
                    .getAlert().showAndWait();
            dpAppoinmentDate.setValue(null);
            cbAppoinmentHour.setValue(null);
            cbAppoinmentHour.setDisable(true);
            cbAppoinmentType.setValue(null);
            cbAppoinmentType.setDisable(true);
            txtComment.setText("");
        } else {
            new Alerts(Alert.AlertType.ERROR, "Error añadir visita", "Se ha producido un error al añadir la visita.\nCompruebe que ha seleccionado todos los campos necesarios.", null)
                    .getAlert().showAndWait();
            dpAppoinmentDate.setValue(null);
            cbAppoinmentHour.setValue(null);
            cbAppoinmentHour.setDisable(true);
            cbAppoinmentType.setValue(null);
            cbAppoinmentType.setDisable(true);
            txtComment.setText("");
        }

        flowPane = (FlowPane) anchorPane.getParent();
        anchorPane = FXMLLoader.load(getClass().getResource("/views/manageOwners.fxml"));
        flowPane.getChildren().clear();
        flowPane.getChildren().add(anchorPane);

    }

    @FXML
    private void accesEntry(ActionEvent event) {

        new SaveVisit().removerVisit();
        try {

            flowPane = (FlowPane) anchorPane.getParent();
            anchorPane = FXMLLoader.load(getClass().getResource("/views/entry.fxml"));
            flowPane.getChildren().clear();
            flowPane.getChildren().add(anchorPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method initializes the owners TextField
     */
    private void addListeners() {

        txtNameOwner.setOnKeyReleased(e -> enableButtonAddOwner());
        txtSurnameOwner.setOnKeyReleased(e -> enableButtonAddOwner());
        txtDniOwner.setOnKeyReleased(e -> enableButtonAddOwner());
        txtEmailOwner.setOnKeyReleased(e -> enableButtonAddOwner());
        txtPhoneNumberOwner.setOnKeyReleased(e -> enableButtonAddOwner());
        txtAddressOwner.setOnKeyReleased(e -> enableButtonAddOwner());

        txtNamePet.setOnKeyReleased(e -> enableButtonAddPet());
        txtNamePet.setOnKeyReleased(e -> enableButtonAddPet());
        txtChipNumber.setOnKeyReleased(e -> enableButtonAddPet());
        txtWeightPet.setOnKeyReleased(e -> enableButtonAddPet());

        rbFemale.setOnMouseClicked(e -> enableButtonAddPet());
        rbMale.setOnMouseClicked(e -> enableButtonAddPet());

        txtSearchOwner.setOnKeyReleased(e -> searchOwner());

        tvPet.setOnMouseClicked(e -> actionPet());
        
    }

    /**
     * This method enables the modify button if any owner Texfield changes
     */
    private void enableButtonAddOwner() {
        if (!txtNameOwner.getText().isEmpty()) {
            if (!txtSurnameOwner.getText().isEmpty()) {
                if (!txtDniOwner.getText().isEmpty()) {
                    if (!txtEmailOwner.getText().isEmpty()) {
                        if (!txtPhoneNumberOwner.getText().isEmpty()) {
                            if (!txtAddressOwner.getText().isEmpty()) {
                                btAddOwner.setDisable(false);
                            } else {
                                btAddOwner.setDisable(true);
                            }
                        } else {
                            btAddOwner.setDisable(true);
                        }
                    } else {
                        btAddOwner.setDisable(true);
                    }
                } else {
                    btAddOwner.setDisable(true);
                }
            } else {
                btAddOwner.setDisable(true);
            }
        } else {
            btAddOwner.setDisable(true);
        }
        lblErrorOwner.setText("");
    }

    /**
     * This method enables the add button if any pet TextFields aren't empty and
     * RadioButton is selected
     */
    private void enableButtonAddPet() {
        if (!txtNamePet.getText().isEmpty()) {
            if (!txtChipNumber.getText().isEmpty()) {
                if (!txtWeightPet.getText().isEmpty()) {
                    if (rbMale.isSelected() || rbFemale.isSelected()) {
                        if (selectedOwner != null) {
                            btAddPet.setDisable(false);
                        } else {
                            btAddPet.setDisable(true);
                        }
                    } else {
                        btAddPet.setDisable(true);
                    }
                } else {
                    btAddPet.setDisable(true);
                }
            } else {
                btAddPet.setDisable(true);
            }
        } else {
            btAddPet.setDisable(true);
        }
        lblErrorPet.setText("");
    }

    /**
     * Method for fill the Combobox race
     */
    private void fillRace() {

        cbRacePet.getItems().clear();
        itemsCbRacePet = new TypeControl().getRace(cbTypePet.getValue());
        cbRacePet.setItems(itemsCbRacePet);
        cbRacePet.setValue(itemsCbRacePet.get(0));

    }

    /**
     * Method for initialize tables
     */
    private void initializeTables() {

        tvOwner.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedOwner = newValue;
                    new SaveOwner().saveOwner(newValue);
                }
        );
        tvOwner.setOnMouseClicked(e -> actionOwner());

        tcNameOwner.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcSurnameOwner.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tcDNIOwner.setCellValueFactory(new PropertyValueFactory<>("dni"));
        setItems(itemsOwner, "owner");

        tvPet.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedPet = newValue;
                    new SavePet().savePet(newValue);
                }
        );

        tcPetName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcChipNumber.setCellValueFactory(new PropertyValueFactory<>("chipNumber"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    /**
     * Method for set items according to the type
     *
     * @param items ObservableList to set in the TableView
     * @param type Type of TableView
     */
    private void setItems(ObservableList items, String type) {
        if (type.equals("owner")) {
            tvOwner.setItems(items);
        } else if (type.equals("pet")) {
            tvPet.setItems(items);
        }

    }

    /**
     * Method for save the owner selected and fill the TextArea with the details
     * of the owner
     */
    private void actionOwner() {

        dpAppoinmentDate.setDisable(true);

        owner = ownerControl.getOwnerTable(selectedOwner.getDni());
        new SaveOwner().removeOwner();
        new SaveOwner().saveOwner(owner);

        txtOwnerDetails.setText("\n\nNombre: \t\t\t\t" + owner.getName()
                + "\n\nApellidos: \t\t\t" + owner.getSurname()
                + "\n\nDNI: \t\t\t\t" + owner.getDni()
                + "\n\nCorreo electrónico: \t\t" + owner.getEmail()
                + "\n\nTeléfono: \t\t\t\t" + owner.getPhoneNumber()
                + "\n\nDirección: \t\t\t" + owner.getAddress());
        btModifyOwner.setDisable(false);
        btRemoveOwner.setDisable(false);
        allPet();
    }

    /**
     * Method for search owner according to the text of the TextField of search
     */
    private void searchOwner() {
        itemsOwner.clear();
        setItems(ownerControl.getOwner(txtSearchOwner.getText()), "owner");
    }

    /**
     * Method for show the owners
     */
    private void allOwner() {
        setItems(ownerControl.getOwner(), "owner");
    }

    /**
     * Method for show the pets
     */
    private void allPet() {
        tvPet.getItems().clear();
        if (selectedOwner != null) {
            Owner newOwner = ownerControl.getOwnerTable(selectedOwner.getDni());
            setItems(petControl.getPet(newOwner.getOwnerId()), "pet");
        }
    }

    /**
     * Methos for when the pet is selected enable buttons and save the pet
     * selected
     */
    private void actionPet() {

        dpAppoinmentDate.setDisable(false);
        btModifyOwner.setDisable(false);
        btRemovePet.setDisable(false);
        cbAppoinmentType.setDisable(false);
        btAccesEntry.setDisable(false);
        btRemovePet.setDisable(false);

        pet = petControl.getPet(selectedPet.getChipNumber());
        new SavePet().savePet(pet);

    }

    /**
     * Method for clean all the TextFields of owner
     */
    private void cleanTextOwner() {
        txtNameOwner.setText("");
        txtSurnameOwner.setText("");
        txtDniOwner.setText("");
        txtEmailOwner.setText("");
        txtPhoneNumberOwner.setText("");
        txtAddressOwner.setText("");
    }

    /**
     * Method for control the DatePicker of visit When the date is selected the
     * combobox of hours is enable. It is filled and if the hour is in the
     * database it is disable.
     */
    private void datePickerAction() {

        cbAppoinmentHour.setDisable(false);
        cbAppoinmentHour.getItems().clear();
        cbAppoinmentHour.setItems(new Hours().getHours());
        busyHours = null;
        cbAppoinmentHour.setCellFactory(new Callback<ListView<LocalTime>, ListCell<LocalTime>>() {
            @Override
            public ListCell<LocalTime> call(ListView<LocalTime> param) {
                return new ListCell<LocalTime>() {
                    @Override
                    protected void updateItem(LocalTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setDisable(false);
                        } else {
                            setText(item.toString());
                            if (busyHours == null) {
                                busyHours = getBusyHours();
                            }

                            Set<LocalTime> busyHours = new HashSet<>(ManageOwnerController.this.busyHours);
                            if (busyHours.contains(item)) {
                                setDisable(true);
                                setStyle("-fx-opacity: 0.3");

                            } else {
                                setDisable(false);
                                setStyle("-fx-opacity: 1");
                            }

                            if (dpAppoinmentDate.getValue().getDayOfWeek() == DayOfWeek.SATURDAY) {
                                LocalTime timeOnSaturday = LocalTime.parse("13:30");
                                if (item.isAfter(timeOnSaturday)) {
                                    setDisable(true);
                                    setStyle("-fx-opacity: 0.3");
                                }
                            }
                        }
                    }
                };
            }
        });

    }

    /**
     * Method for consult the busyHours in database
     *
     * @return
     */
    private List<LocalTime> getBusyHours() {
        return new VisitControl().getHours(dpAppoinmentDate.getValue(), new SaveUser().loadUser().getId());
    }

    private void initializeComboboxs() {

        itemsCbTypePet = new TypeControl().getType();
        cbTypePet.setItems(itemsCbTypePet);
        cbTypePet.setValue(itemsCbTypePet.get(0));
        fillRace();
        cbTypePet.setOnAction(e -> fillRace());

        cbAppoinmentHour.setDisable(true);
        ObservableList<LocalTime> h = new Hours().getHours();

        cbAppoinmentHour.setItems(h);

        cbAppoinmentType.setDisable(true);
        cbAppoinmentType.setItems(new TypeAppoinment().getTypes());
        
    }

    private void disableButtons() {

        btModifyOwner.setDisable(true);
        btAddOwner.setDisable(true);
        btRemoveOwner.setDisable(true);
        btAddPet.setDisable(true);
        btModifyPet.setDisable(true);
        btRemovePet.setDisable(true);
        btAccesEntry.setDisable(true);

    }

    private void initializeDatePickers() {

        dpAppoinmentDate.setDisable(true);
        dpAppoinmentDate.setOnAction(e -> datePickerAction());
        dpAppoinmentDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                        }
                        if (item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                        }
                    }
                };
            }
        });

        dpBirthDatePet.setValue(LocalDate.now());
        dpBirthDatePet.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                        }
                    }
                };
            }
        });

    }    

}
