package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.CustomerPaymentDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.CustomerPaymentTm;
import edu.ijse.malshanrentshopmanagement.model.CustomerModel;
import edu.ijse.malshanrentshopmanagement.model.CustomerPaymentModel;
import edu.ijse.malshanrentshopmanagement.model.DriverModel;
import edu.ijse.malshanrentshopmanagement.model.VehicleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageCustomerPaymentFormController implements Initializable {
    public AnchorPane AncCustomer;
    public JFXTextField txtCardNumber;
    public DatePicker txtRentalPeriod;
    public JFXComboBox<Integer> cmbVehicleId;
    public JFXComboBox<String> cmbpaymentMethod;
    public JFXComboBox<Integer> cmbDriverId;
    public JFXComboBox<Integer> cmbCustomerd;
    public JFXTextField txtLocationDistance;
    public JFXButton txtSave;
    public TableView<CustomerPaymentTm> tblCustPayment;
    public Label lblCustomer;
    public Label lblDriver;
    public Label lblVehicle;
    public JFXTextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPaymentMethod();
        loadCustomerIds();
        loadVehicleIds();
        loadDriverIds();
    }

    private void loadDriverIds() {
        ArrayList<Integer> allDriversId = DriverModel.getAllDriversId();
        ObservableList<Integer> drivers = FXCollections.observableArrayList();
        for (Integer value:allDriversId){
            drivers.add(value);
        }
        cmbDriverId.setItems(drivers);
    }

    private void loadVehicleIds() {
        ArrayList<Integer> allVehicleId = VehicleModel.getAllVehicleId();
        ObservableList<Integer> vehicles = FXCollections.observableArrayList();
        for (Integer value:allVehicleId){
            vehicles.add(value);
        }
        cmbVehicleId.setItems(vehicles);
    }

    private void loadCustomerIds() {
        ArrayList<Integer> customerId = CustomerPaymentModel.loadCustomerId();
        ObservableList<Integer> customers = FXCollections.observableArrayList();
        for (Integer value:customerId){
            customers.add(value);
        }
        cmbCustomerd.setItems(customers);
    }

    private void loadPaymentMethod() {
        ObservableList<String> paymentMethod=FXCollections.observableArrayList();
        paymentMethod.add("Card");
        paymentMethod.add("Cash");
        cmbpaymentMethod.setItems(paymentMethod);
    }

    public void backOnAction(MouseEvent mouseEvent) {
        try{
            AncCustomer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CustomerManagementForm.fxml"));
            AncCustomer.getChildren().add(load);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
        }
    }

    public void saveOnAction(ActionEvent event) {
        try {
            boolean isValid = true;
            String id = cmbCustomerd.getSelectionModel().getSelectedItem().toString();
            String customerFromId = CustomerModel.getCustomerFromId(Integer.parseInt(id));
            lblCustomer.setText(customerFromId);
            String paymentMethod = cmbpaymentMethod.getSelectionModel().getSelectedItem().toString();
            String cardNumber = txtCardNumber.getText();
            if (!Pattern.matches("^\\d{12}$", cardNumber)) {
                txtCardNumber.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtCardNumber.setStyle("");
            }
            String rental = txtRentalPeriod.getValue().toString();
            String location = txtLocationDistance.getText();
            if (!Pattern.matches("^([1-9]\\d*|0)(\\.\\d+)?\\s?km$", location)) {
                txtLocationDistance.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtLocationDistance.setStyle("");
            }
            String driverId = cmbDriverId.getSelectionModel().getSelectedItem().toString();
            String name = DriverModel.getName(Integer.parseInt(driverId));
            lblDriver.setText(name);
            String vehicleId = cmbVehicleId.getSelectionModel().getSelectedItem().toString();
            String brand = VehicleModel.getBrand(Integer.parseInt(vehicleId));
            lblVehicle.setText(brand);
            if (id.isEmpty() || paymentMethod.isEmpty() || cardNumber.isEmpty() || rental.isEmpty() || location.isEmpty() || driverId.isEmpty() || vehicleId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Fill All Values").show();
                return;
            }
            if (!isValid) {
                return;
            }
            boolean isSave = CustomerPaymentModel.saveCustomerPayment(new CustomerPaymentDto(Integer.parseInt(id), paymentMethod,
                    cardNumber, Integer.parseInt(vehicleId), rental, location, Integer.parseInt(driverId)));
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Payment Save Successfully").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Customer Payment Not Saved").show();
            }
        }catch (NullPointerException e){
            System.out.println("Some Values are missing.");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        Integer selectedItem = cmbCustomerd.getSelectionModel().getSelectedItem();
        String customerFromId = CustomerModel.getCustomerFromId(selectedItem);
        lblCustomer.setText(customerFromId);
    }

    public void cmbVehicleOnAction(ActionEvent actionEvent) {
        Integer selectedItem = cmbVehicleId.getSelectionModel().getSelectedItem();
        String brand = VehicleModel.getBrand(selectedItem);
        lblVehicle.setText(brand);
    }

    public void cmDriverOnAction(ActionEvent actionEvent) {
        Integer selectedItem = cmbDriverId.getSelectionModel().getSelectedItem();
        String name = DriverModel.getName(selectedItem);
        lblDriver.setText(name);
    }
}
