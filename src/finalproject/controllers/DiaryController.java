/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import models.DiaryVisit;
import models.Hours;
import models.connection.VisitControl;
import models.entitys.Visit;
import models.entitys.persistence.SaveDiaryVisit;
import models.entitys.persistence.SavePet;
import models.entitys.persistence.SaveUser;
import models.entitys.persistence.SaveVisit;
import views.Alerts;

/**
 * Controller for manage the view diary
 */
public class DiaryController implements Initializable {

    @FXML
    private DatePicker dpDateDiary;
    @FXML
    private TableView<DiaryVisit> tvDiary;
    @FXML
    private TableColumn<DiaryVisit, String> tcPetName, tcOwnerName, tcVisitType, tcComment;
    @FXML
    private TableColumn<DiaryVisit, LocalDate> tcDate;
    @FXML
    private TableColumn<DiaryVisit, LocalTime> tcHour;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btRemoveVisit;

    private FlowPane flowPane;

    private final ObservableList<DiaryVisit> items = FXCollections.observableArrayList();

    private DiaryVisit selectedVisit;

    /**
     * Initializes the controller class.
     *
     * @param url The URL of the view
     * @param rb The resource of the RecourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeTable();
        initializeDatePicker();

        selectedVisit = null;
        
        btRemoveVisit.setDisable(true);

        setItems(items);
        
        Platform.runLater(() -> initializeDimensions());

    }

    /**
     * Method that execute to click on add visit add visit Load the view
     * manageOwner
     *
     * @param event The event of the button
     * @throws IOException Exception throw if there is a problem when loading
     * the view
     */
    @FXML
    private void addVisit(ActionEvent event) throws IOException {

        flowPane = (FlowPane) anchorPane.getParent();
        anchorPane = FXMLLoader.load(getClass().getResource("/views/manageOwners.fxml"));
        flowPane.getChildren().clear();
        flowPane.getChildren().add(anchorPane);

    }
    
    @FXML
    private void removeVisit(ActionEvent event) throws IOException {
        
        Alert alert = new Alerts(Alert.AlertType.CONFIRMATION, "Eliminar visita", "¿Está seguro que desea eliminar la visita?", "")
                .getAlert();
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            if (new VisitControl().removeVisit(selectedVisit.getVisit().getVisitId())){
                new Alerts(Alert.AlertType.INFORMATION, "Visita eliminada", "Se ha eliminado la visita.", null).getAlert().showAndWait();
            } else {
                new Alerts(Alert.AlertType.ERROR, "Visita no eliminada", "No se ha eliminado la visita.", null).getAlert().showAndWait();
            }
        }
        
        setItems(items);
        btRemoveVisit.setDisable(true);
    }

    /**
     * Method for initialize the table
     */
    private void initializeTable() {

        tvDiary.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, NewValue) -> {
                    selectedVisit = NewValue;
                }
        );
        
        tvDiary.setOnMouseClicked(e -> {
            if (selectedVisit==null){                
                btRemoveVisit.setDisable(true);                                
            }else{
                if (selectedVisit.getVisit()!=null){
                    btRemoveVisit.setDisable(false);
                }else{
                    btRemoveVisit.setDisable(true);
                }
                
                
            }
        });

        tvDiary.setRowFactory(e -> {
            TableRow<DiaryVisit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    try {
                        Visit visit = selectedVisit.getVisit();

                        if (visit == null) {

                            DiaryVisit diaryVisit = (DiaryVisit) row.getItem();
                            new SaveDiaryVisit().saveDiaryVisit(diaryVisit);
                            flowPane = (FlowPane) anchorPane.getParent();
                            anchorPane = FXMLLoader.load(getClass().getResource("/views/manageOwners.fxml"));
                            flowPane.getChildren().clear();
                            flowPane.getChildren().add(anchorPane);

                        } else {
                            new SaveVisit().saveVisit(visit);
                            flowPane = (FlowPane) anchorPane.getParent();
                            anchorPane = FXMLLoader.load(getClass().getResource("/views/entry.fxml"));
                            flowPane.getChildren().clear();
                            flowPane.getChildren().add(anchorPane);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return row;
        });

        tcPetName.setStyle("-fx-alignment : center");
        tcPetName.setCellValueFactory(new PropertyValueFactory<>("petName"));

        tcOwnerName.setStyle("-fx-alignment : center");
        tcOwnerName.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        tcVisitType.setStyle("-fx-alignment : center");
        tcVisitType.setCellValueFactory(new PropertyValueFactory<>("visitType"));

        tcComment.setCellValueFactory(new PropertyValueFactory<>("visitComment"));

        tcDate.setStyle("-fx-alignment : center");
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tcDate.setCellFactory(new Callback<TableColumn<DiaryVisit, LocalDate>, TableCell<DiaryVisit, LocalDate>>() {
            @Override
            public TableCell<DiaryVisit, LocalDate> call(TableColumn<DiaryVisit, LocalDate> param) {
                return new TableCell<DiaryVisit, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.format(dateFormat));
                        }
                    }
                };
            }
        });

        
        tcHour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        tcHour.setCellFactory(e -> {
            return new TableCell<DiaryVisit, LocalTime>() {
                @Override
                protected void updateItem(LocalTime hour, boolean empty) {
                    super.updateItem(hour, empty);
                    if (empty || hour == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(hour.toString());

                        DiaryVisit diaryVisit = (DiaryVisit) getTableRow().getItem();

                        if (diaryVisit.getVisit() == null) {
                            setStyle("-fx-background-color: green;" + "-fx-text-fill: white;" + "-fx-alignment : center;");
                        } else {
                            setStyle("-fx-background-color: red;" + "-fx-text-fill: white;" + "-fx-alignment : center;");
                        }
                    }
                }
            };
        });
    }

    /**
     *
     */
    private void initializeDatePicker() {
        
        if (LocalDate.now().getDayOfWeek() != DayOfWeek.SUNDAY){
            dpDateDiary.setValue(LocalDate.now());
        }else{
            dpDateDiary.setValue(LocalDate.now().plusDays(1));
        }
        
        dpDateDiary.setOnAction(e -> setItems(items));
        dpDateDiary.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
    }

    /**
     * Set items of the TableView
     *
     * @param items ObservableList of Visit that contains the visits
     */
    private void setItems(ObservableList<DiaryVisit> items) {

        tvDiary.getItems().clear();
        int userId = new SaveUser().loadUser().getId();
        List<LocalTime> busyHours = new VisitControl().getHours(dpDateDiary.getValue(), userId);
        ObservableList<Visit> visits = new VisitControl().getVisit(userId,dpDateDiary.getValue(), busyHours);
        DiaryVisit diaryVisit = null;        
        ObservableList<LocalTime> allHours = new Hours().getHours();
        for (LocalTime hours : allHours) {  
            if (!visits.isEmpty()) {
                for (Visit visit : visits) {
                    if (hours.equals(visit.getHour())) {
                        diaryVisit = new DiaryVisit(dpDateDiary.getValue(), hours, visit);
                        break;
                    } else {
                        diaryVisit = new DiaryVisit(dpDateDiary.getValue(), hours, null);
                    }
                }
            } else {
                diaryVisit = new DiaryVisit(dpDateDiary.getValue(), hours, null);
            }
            items.add(diaryVisit);
        }
        tvDiary.setItems(items);
    }

    private void initializeDimensions() {
        
        flowPane = (FlowPane) anchorPane.getParent();
        double height = flowPane.getHeight();
        double width = flowPane.getWidth();
        
        anchorPane.setPrefHeight(height);
        anchorPane.setPrefWidth(width);
    }

}
