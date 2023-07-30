/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.io.IOException;
import javafx.scene.text.Font;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.connection.EntryControl;
import models.connection.PetControl;
import models.entitys.Entry;
import models.entitys.Pet;
import models.entitys.Visit;
import models.entitys.persistence.SaveVisit;
import views.Alerts;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import models.entitys.persistence.SavePet;

/**
 * FXML Controller class
 *
 * @author Albert
 */
public class EntryController implements Initializable {

    @FXML
    private TextField txtNamePet;
    @FXML
    private TextField txtSexPet;
    @FXML
    private TextField txtTypePet;
    @FXML
    private TextField txtChipNumber;
    @FXML
    private TextField txtWeightPet;
    @FXML
    private TextField txtRacePet;
    @FXML
    private TextField txtDateBirth;
    @FXML
    private TextField txtAgePet;
    @FXML
    private TextArea txtCommentVisit;
    @FXML
    private ScrollPane scpEntries;
    @FXML
    private Button btAddEntry, btPay;
    @FXML
    private AnchorPane anchorPane;

    private Visit visit = null;

    private Pet pet = null;

    private EntryControl entryControl;
    
    private FlowPane flowPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        entryControl = new EntryControl();        

        visit = new SaveVisit().loadVisit();
        
        if (visit==null){
            pet = new SavePet().loadPet();
        }else{

            pet = visit.getPet();
        
        }

        initializeTextFields();

        initializeTextArea();

        initializeScrollPane();

    }

    @FXML
    private void addEntry(ActionEvent event) {

        Entry entry = new Entry(LocalDate.now(), txtCommentVisit.getText(), LocalTime.now(), pet);
        if (entryControl.createEntry(entry)) {
            new Alerts(Alert.AlertType.INFORMATION, "Entrada creada", "Se ha almacenado la entrada en el historial", null).getAlert().showAndWait();
            initializeScrollPane();
        }

    }
    
    @FXML
    private void pay(ActionEvent event) throws IOException{
        
        flowPane = (FlowPane) anchorPane.getParent();
        anchorPane = FXMLLoader.load(getClass().getResource("/views/pay.fxml"));
        flowPane.getChildren().clear();
        flowPane.getChildren().add(anchorPane);
        
    }

    private void initializeTextFields() {

        txtNamePet.setText(pet.getName());
        txtSexPet.setText(pet.getSex());
        txtTypePet.setText(pet.getType());
        txtChipNumber.setText(pet.getChipNumber());
        txtWeightPet.setText(String.valueOf(pet.getWeight()));
        txtWeightPet.setOnKeyReleased(e -> updateWeight());
        txtRacePet.setText(pet.getRace());
        txtDateBirth.setText(formatLocalDate(pet.getBirthDate()));
        txtAgePet.setText(getAge(pet.getBirthDate()));

    }

    private String formatLocalDate(LocalDate date) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(format);

    }

    private String getAge(LocalDate date) {

        String mounth;

        Period period = Period.between(date, LocalDate.now());

        if (period.getMonths() == 1) {
            mounth = "mes";
        } else {
            mounth = "meses";
        }

        if (period.getYears() == 0) {
            return period.getMonths() + " " + mounth;
        } else {
            return period.getYears() + " años y " + period.getMonths() + " " + mounth;
        }

    }

    private void initializeTextArea() {
        
        if (visit!=null){

        txtCommentVisit.setText("\n Fecha: " + formatLocalDate(LocalDate.now())
                + "\n\n Motivo: " + visit.getType()
                + "\n\n Peso: " + txtWeightPet.getText()
                + "\n\n Sintomas: " + visit.getComment()
                + "\n\n Observaciones: ");
        
        }else{
            txtCommentVisit.setText("");
            txtCommentVisit.setDisable(true);
            btAddEntry.setDisable(true);
            btPay.setDisable(true);
        }

    }

    private void updateWeight() {

        new PetControl().updatePet(pet, "", Double.parseDouble(txtWeightPet.getText()));
        initializeTextArea();

    }

    private void initializeScrollPane() {

        VBox vBox = new VBox();
        vBox.setSpacing(2);

        ObservableList<Entry> ol = entryControl.getEntry(pet);

        for (Entry entry : ol) {

            Label label = new Label();

            label.setWrapText(true);

            label.setMinHeight(200);
            label.setMinWidth(489);
            label.setStyle("-fx-background-color: white;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;");
            Tooltip tooltip = new Tooltip(entry.getComment());           
            tooltip.setFont(new Font(16));            
            
            label.setOnMouseEntered(e -> label.setTooltip(tooltip));              
            
            label.setOnMouseClicked(e -> showEntry(e, entry));           
            

            if (entry.getComment().length() < 130) {
                label.setText(entry.getComment());
            } else {
                label.setText(entry.getComment().substring(0, 130) + "...");
            }

            vBox.getChildren().add(label);

        }

        scpEntries.setContent(vBox);
    }

    private void showEntry(MouseEvent e, Entry entry) {
        
        if (e.getClickCount() == 2){
            new WindowControl().openWindowShowEntry(entry);
        }
        
    }

}
