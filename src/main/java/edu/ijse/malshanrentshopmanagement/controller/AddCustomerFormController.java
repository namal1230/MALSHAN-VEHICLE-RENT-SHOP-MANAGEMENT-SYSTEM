package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.dto.CustomerDto;
import edu.ijse.malshanrentshopmanagement.model.CustomerModel;
import edu.ijse.malshanrentshopmanagement.model.DriverModel;
import edu.ijse.malshanrentshopmanagement.model.VehicleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerFormController implements Initializable {

    public static File selectedFile;
    public static FileInputStream fis;
    public JFXComboBox txtVehicleId;
    public JFXTextField txtLicenseId;
    public Label lblVehicleModel;
    public Label lblDriverName;
    public JFXComboBox txtDriverId;
    public JFXTextField txtEmail;
    public DatePicker txtDOB;
    public JFXTextField txtNic;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public Label lblId;
    public AnchorPane AncCustomer;
    public ImageView iv;
    public DatePicker txtDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
    }

    private void refreshPage() {
        setId();
        loadVehiclesId();
        loadDriversId();
    }

    private void loadDriversId() {
        ArrayList<Integer> allDriversId = DriverModel.getAllDriversId();
        ObservableList<Object> objects = FXCollections.observableArrayList();
        for (Integer driversId : allDriversId) {
            objects.add(driversId);
        }
        txtDriverId.setItems(objects);
    }

    private void loadVehiclesId() {
        ArrayList<Integer> allVehicleId = VehicleModel.getAllVehicleId();
        ObservableList<Object> objects = FXCollections.observableArrayList();
        for (Integer vehicleId : allVehicleId) {
            objects.add(vehicleId);
        }
        txtVehicleId.setItems(objects);
    }

    private void setId() {
        String id = CustomerModel.generateId();
        lblId.setText(id);
    }

    public void backOnAction(MouseEvent mouseEvent) {
        try {
            AncCustomer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CustomerManagementForm.fxml"));
            AncCustomer.getChildren().add(load);
        } catch (IOException e) {
            System.out.println("Cannot load Ui");
        }
    }

    public void saveOnAction(ActionEvent event) {
        try {
            boolean isLegal = true;
            String id = lblId.getText();
            String name = txtName.getText();
            if (!Pattern.matches("^[A-Za-z]+( [A-Za-z]+)*$", name)) {
                txtName.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtName.setStyle("");
            }
            String address = txtAddress.getText();
            if (!Pattern.matches("^[A-Za-z0-9\\s,.-/]+$", address)) {
                txtAddress.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtAddress.setStyle("");
            }
            String contact = txtContact.getText();
            if (!Pattern.matches("^\\d{10}$", contact)) {
                txtContact.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtContact.setStyle("");
            }
            String nic = txtNic.getText();
            if (!Pattern.matches("(^\\d{9}[Vv]$)|(^\\d{12}$)", nic) || !Pattern.matches("^\\d{12}$", nic)) {
                txtNic.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtNic.setStyle("");
            }
            String dob = txtDOB.getValue().toString();
            String email = txtEmail.getText();
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
                txtEmail.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtEmail.setStyle("");
            }
            int driverId = (int) txtDriverId.getValue();
            String licenId = txtLicenseId.getText();
            if (!Pattern.matches("^[A-Z]{2} \\d{3,5}$", licenId)) {
                txtLicenseId.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtLicenseId.setStyle("");
            }
            int vehicleId = (int) txtVehicleId.getValue();
            String date = txtDate.getValue().toString();
            if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || nic.isEmpty() || dob.isEmpty() || email.isEmpty() || String.valueOf(vehicleId).isEmpty() || date.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Fill All Values").show();
                return;
            }
            if (!isLegal) {
                return;
            }

            boolean isAvailable = CustomerModel.searchCustomerFromId(id);
            if (isAvailable) {
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Update It?", ButtonType.YES, ButtonType.NO).showAndWait();
                if (buttonType.get() == ButtonType.YES) {
                    boolean isUpdate = CustomerModel.updateCustomer(new CustomerDto(Integer.parseInt(id), name, address, contact, nic, dob, email, driverId, licenId, vehicleId, date));
                    if (isUpdate) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Update SuccessFully..").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Customer Not Updated...").show();
                    }
                }
            } else {
                boolean isSave = CustomerModel.saveCustomer(new CustomerDto(Integer.parseInt(id), name, address, contact, nic, dob, email, driverId, licenId, vehicleId, date));
                if (isSave) new Alert(Alert.AlertType.INFORMATION, "Customer Save Successfully..").show();
                else new Alert(Alert.AlertType.WARNING, "Customer Not Saved..").show();
            }
        }catch (NullPointerException e){
            System.out.println("Some Values Are missing.");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }

    public void cmbVehicleOnAction(ActionEvent actionEvent) {
        Object selectedItem = txtVehicleId.getSelectionModel().getSelectedItem();
        String brand = VehicleModel.getBrand(selectedItem);
        lblVehicleModel.setText(brand);
    }

    public void selectIdOnAction(ActionEvent actionEvent) {
        Object selectedItem = txtDriverId.getSelectionModel().getSelectedItem();
        String value = String.valueOf(selectedItem);
        String name = DriverModel.getName(Integer.parseInt(value));
        lblDriverName.setText(name);

    }

    public void getAllValuesOnAction(MouseEvent mouseEvent) {
        String nic = txtNic.getText();
        CustomerDto customer = CustomerModel.getAllCustomerNic(nic);
        lblId.setText(String.valueOf(customer.getId()));
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtContact.setText(customer.getContact());
        txtDOB.setValue(LocalDate.parse(customer.getDob()));
        txtEmail.setText(customer.getEmail());
        txtDriverId.setValue(customer.getDriverId());
        txtLicenseId.setText(customer.getLicen_number());
        txtVehicleId.setValue(customer.getVehicleId());
        String brand = VehicleModel.getBrand(customer.getVehicleId());
        lblVehicleModel.setText(brand);
        String name = DriverModel.getName(customer.getDriverId());
        lblDriverName.setText(name);
        txtDate.setValue(LocalDate.parse(customer.getDate()));
    }
}

