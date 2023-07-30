/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.controllers;

import java.net.URL;
import java.time.LocalTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import models.Pay;
import models.connection.ProductControl;
import models.entitys.Product;
import views.Alerts;

/**
 * FXML Controller class
 *
 * @author Albert
 */
public class PayController implements Initializable {

    @FXML
    private ComboBox<String> cbProduct;
    @FXML
    private TextField txtAmount;
    @FXML
    private Button btAddProduct, btRemoveProduct, btPay;
    @FXML
    private TableView<Pay> tvPay;
    @FXML
    private TableColumn<Pay, String> tcCodeProduct;
    @FXML
    private TableColumn<Pay, String> tcNameProduct;
    @FXML
    private TableColumn<Pay, Double> tcPriceProduct;
    @FXML
    private TableColumn<Pay, Integer> tcAmountProduct;
    @FXML
    private TableColumn<Pay, Double> tcTotalPrice;
    @FXML
    private Label lblTotalParcial, lblIVA, lblTotal;
    
    private ObservableList<Product> products = FXCollections.observableArrayList();
    
    private ObservableList<String> namesProducts = FXCollections.observableArrayList();
    
    private final int iva = 21;
    
    private Pay selectedPay = null;
    
    ObservableList<Pay> items = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fillObservableList();
        
        initializeCombobox();
        
        initializeTable();
        
        initializeListeners();
        
        disableButtons();        
        
        initializeLabels();
        
    }    

    @FXML
    private void addProduct(ActionEvent event) {
        
        try{
            
            int amount = Integer.parseInt(txtAmount.getText());
        
            Product product = new ProductControl().getProductPay(cbProduct.getValue());            

            if (amount <= product.getStock()){

                Pay pay = new Pay(product.getProductId(), product.getName(), product.getPrice(), amount);                
                
                items.add(pay);
                
                setItems(items);

                calculateTotal();            
                
        
                cbProduct.getSelectionModel().clearSelection();
                txtAmount.setText("");
                btAddProduct.setDisable(true);
                initializeCombobox();
            
            }else{               
                
                new Alerts(Alert.AlertType.WARNING, "Stock insuficiente", "El stock de " + product.getName() + " es " + String.valueOf(product.getStock()) + 
                        ". No es posible retirar " + String.valueOf(amount) + ".", null).getAlert().showAndWait();
                
                cbProduct.setValue(product.getName());
                txtAmount.requestFocus();
                
            }
        
        }catch (NumberFormatException e){
            
            new Alerts(Alert.AlertType.ERROR, "Formato incorrecto", "La cantidad debe ser un número entero.", null).getAlert().showAndWait();
            
        }
        
    }
    
    @FXML
    private void removeProduct(ActionEvent event) {      
        
        Alert alert = new Alerts(Alert.AlertType.CONFIRMATION, "Eliminar articulo", "¿Está seguro que desea eliminar el articulo?", "")
                .getAlert();
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            
            items.remove(selectedPay);
        
            tvPay.getItems().remove(selectedPay);           

            calculateTotal();

            btRemoveProduct.setDisable(true);

            initializeCombobox();
        
        }
        
    }
    
    @FXML
    private void pay(ActionEvent event) {
        
        ObservableList<Pay> items = tvPay.getItems();
        
        for (Pay pay : items){
            
            Product product = new ProductControl().getProductPay(pay.getName());
            
            new ProductControl().updateStock(product, pay.getAmount());
            
        }
        
        new Alerts(Alert.AlertType.INFORMATION, "Se ha realizado el cobro", "El cobro se ha realizado y se ha descontado la cantidad de los productos en stock.", null)
                .getAlert().showAndWait();
        
        tvPay.getItems().clear();
        
        initializeLabels();
        
    }
    
    private void fillObservableList() {           
        
        products.addAll(new ProductControl().getProduct());
        
        for (Product product : products){
            
            namesProducts.add(product.getName());
            
        }
        
    }

    private void initializeCombobox() {
        
        ObservableList<Pay> productsInTable = tvPay.getItems();
        
        cbProduct.setItems(namesProducts);
        
        cbProduct.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setDisable(false);
                        } else {
                            setText(item);
                            
                            for (Pay pay : productsInTable){
                                
                                if (pay.getName().equals(item)){
                                    setDisable(true);
                                    setStyle("-fx-opacity: 0.3");
                                    break;
                                }else{
                                    setDisable(false);
                                    setStyle("-fx-opacity: 1");
                                } 
                            }
                        }
                    }
                };
            }
        });
        
        
        
    }

    private void initializeTable() {     
        
        tvPay.setEditable(true);
        
        tvPay.setStyle("-fx-table-cell-border-color: transparent;");
        
        tvPay.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedPay = newValue;                    
                }
        );
        
        tcCodeProduct.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcNameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPriceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcAmountProduct.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tcAmountProduct.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tcAmountProduct.setOnEditCommit(e -> modifyAmount(e));
        tcTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
        
    }

    private void enableButtons() {
        
        if (cbProduct.getValue()!=null){
            if (!txtAmount.getText().isEmpty()){
                btAddProduct.setDisable(false);
            } else {
                btAddProduct.setDisable(true);
            }
        }
        
        if (selectedPay==null){
            btRemoveProduct.setDisable(true);
        }else{
            btRemoveProduct.setDisable(false);
        }
        
        if (tvPay.getItems().isEmpty()){
            btPay.setDisable(true);
        }else{
            btPay.setDisable(false);
        }
        
    }

    private void initializeListeners() {
        
        txtAmount.setOnKeyReleased(e -> enableButtons());
        cbProduct.setOnMouseClicked(e -> enableButtons());
        tvPay.setOnMouseClicked(e -> enableButtons());
        btAddProduct.setOnMouseReleased(e -> enableButtons());
        btRemoveProduct.setOnMouseReleased(e -> enableButtons());
    }

    private void calculateTotal() {
        
        Double totalParcial = calculateTotalParcial();
        
        lblTotalParcial.setText(String.format("%.2f", totalParcial) + "€");
        
        lblIVA.setText(String.valueOf(iva) + "%");
        
        Double totalPrice = totalParcial + (totalParcial*iva/100);
        
        lblTotal.setText(String.format("%.2f", totalPrice) + "€");
        
    }

    private double calculateTotalParcial() {
        
        Double totalParcial = 0.0;
        
        ObservableList<Pay> pays = tvPay.getItems();
        
        for (Pay pay : pays){
            
            totalParcial += pay.getTotal();
            
        }
        
        return totalParcial;
        
    }

    private void disableButtons() {
              
        btAddProduct.setDisable(true);
        btRemoveProduct.setDisable(true);
        btPay.setDisable(true);
        
    }

    private void initializeLabels() {
        
        lblTotalParcial.setText("0.00€");
        lblIVA.setText(String.valueOf(iva) + "%");
        lblTotal.setText("0.00€");
        
    }   

    private void modifyAmount(TableColumn.CellEditEvent<Pay, Integer> e) {
        
        Pay pay = e.getRowValue();
        pay.setAmount(e.getNewValue());
        tvPay.refresh();
        calculateTotal();
        
    }

    private void setItems(ObservableList<Pay> items) {
        tvPay.getItems().clear();
        tvPay.getItems().addAll(items);
    }
    
}
