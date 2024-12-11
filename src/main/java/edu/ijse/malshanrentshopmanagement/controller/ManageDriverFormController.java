package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.DriverDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.DriverTm;
import edu.ijse.malshanrentshopmanagement.model.DriverModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class ManageDriverFormController implements Initializable {
    public AnchorPane AncVehicle;
    public DatePicker txtDob;
    public JFXTextField txtSearch;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public DatePicker txtLicenseExp;
    public JFXTextField txtNic;
    public JFXTextField txtLicense;
    public JFXTextField txtEmail;
    public JFXTextField txtLicenseType;
    public JFXTextField txtMedical;
    public JFXTextField txtStatus;
    public TableView<DriverTm> tblDriver;
    public TableColumn<DriverTm, String> colContact;
    public TableColumn<DriverTm, String> colName;
    public TableColumn<DriverTm, String> colAddress;
    public TableColumn<DriverTm, String> colLicens;
    public TableColumn<DriverTm, String> colLicenseExpiry;
    public TableColumn<DriverTm, String> colLicenseType;
    public TableColumn<DriverTm, String> colMedical;
    public TableColumn<DriverTm, String> colStatus;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLicens.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        colLicenseExpiry.setCellValueFactory(new PropertyValueFactory<>("licenseExpiry"));
        colLicenseType.setCellValueFactory(new PropertyValueFactory<>("licenseType"));
        colMedical.setCellValueFactory(new PropertyValueFactory<>("medical"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        refreshPage();
    }

    private void refreshPage() {
        loadtable();
        btnDelete.setDisable(true);
        clearTextFields();
    }

    private void clearTextFields() {
        txtName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtEmail.clear();
        txtDob.setValue(null);
        txtNic.clear();
        txtLicense.clear();
        txtLicenseExp.setValue(null);
        txtLicenseType.clear();
        txtMedical.clear();
        txtStatus.clear();
        loadtable();
        btnDelete.setDisable(true);
    }

    private void loadtable() {
        ArrayList<DriverDto> allDrivers = DriverModel.getAllDrivers();
        ObservableList<DriverTm> drivers = FXCollections.observableArrayList();
        for (DriverDto driverDto : allDrivers) {
            drivers.add(new DriverTm(driverDto.getName(), driverDto.getAddress(),
                    driverDto.getContact(), driverDto.getLicense_number(), driverDto.getLicense_exp(),
                    driverDto.getLicense_type(), driverDto.getMedical(), driverDto.getStatus()));

        }
        tblDriver.setItems(drivers);

    }

    public void backOnAction(MouseEvent mouseEvent) {
        try {
            AncVehicle.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/VehicleManagementForm.fxml"));
            AncVehicle.getChildren().add(load);
        } catch (IOException e) {
            System.out.println("CannotLoad UI");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            boolean isValid = true;
            String name = txtName.getText();
            if (!Pattern.matches("^[A-Za-z]+( [A-Za-z]+)*$", name)) {
                txtName.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtName.setStyle("");
            }
            String address = txtAddress.getText();
            if (!Pattern.matches("^[A-Za-z0-9\\s,.-/]+$", address)) {
                txtAddress.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtAddress.setStyle("");
            }
            String contact = txtContactNumber.getText();
            if (!Pattern.matches("^\\d{10}$", contact)) {
                txtContactNumber.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtContactNumber.setStyle("");
            }
            String email = txtEmail.getText();
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
                txtEmail.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtEmail.setStyle("");
            }
            String dob = txtDob.getValue().toString();
            String nic = txtNic.getText();
            if (!Pattern.matches("(^\\d{9}[Vv]$)|(^\\d{12}$)", nic)) {
                txtNic.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtNic.setStyle("");
            }
            String license = txtLicense.getText();
            if (!Pattern.matches("^[A-Z]{2} \\d{3,5}$", license)) {
                txtLicense.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtLicense.setStyle("");
            }
            String licenseExp = txtLicenseExp.getValue().toString();
            String licenseType = txtLicenseType.getText();
            /*if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", license)) {
                txtLicenseType.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtLicenseType.setStyle("");
            }*/
            String medical = txtMedical.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", medical)) {
                txtMedical.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtMedical.setStyle("");
            }
            String status = txtStatus.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", status)) {
                txtStatus.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtStatus.setStyle("");
            }
            if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty() || dob.isEmpty() || nic.isEmpty() || licenseExp.isEmpty() || license.isEmpty() || licenseType.isEmpty() || medical.isEmpty() || status.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Fill All Values").show();
                return;
            }
            if (!isValid) {
                return;
            }
            boolean isAvailabe = DriverModel.searchNic(nic);
            if (isAvailabe) {
                boolean isUpate = DriverModel.updateDriver(new DriverDto(0, name, address, contact, email, dob, nic, license,
                        licenseExp, licenseType, medical, status));
                if (isUpate) {
                    new Alert(Alert.AlertType.INFORMATION, "Driver Update Successfully..").show();
                    loadtable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Driver Not Updated..").show();
                }
            } else {
                boolean isSave = DriverModel.saveDriver(new DriverDto(0, name, address, contact, email, dob,
                        nic, license, licenseExp, licenseType, medical, status));
                if (isSave) {
                    new Alert(Alert.AlertType.INFORMATION, "Driver Save Successfully..").show();
                    loadtable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Driver Not Save..").show();
                }
            }
        }catch (NullPointerException e){
            System.out.println("Some Values are missing.");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String nic = txtNic.getText();
        if (nic.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Nic").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want To Delete It? ", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isDelete = DriverModel.deleteDriver(nic);
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Driver Delete Successfully..").show();
                loadtable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Driver Not Save..").show();
            }

        }
    }

    public void selectOnAction(MouseEvent mouseEvent) {
        try {
            DriverTm selectedItem = tblDriver.getSelectionModel().getSelectedItem();
            String licenseNumber = selectedItem.getLicenseNumber();
            DriverDto allDrivers = DriverModel.getAllDrivers(licenseNumber);
            txtName.setText(allDrivers.getName());
            txtAddress.setText(allDrivers.getAddress());
            txtContactNumber.setText(allDrivers.getContact());
            txtEmail.setText(allDrivers.getEmail());
            txtDob.setValue(LocalDate.parse(allDrivers.getDob()));
            txtNic.setText(allDrivers.getNic());
            txtLicense.setText(allDrivers.getLicense_number());
            txtLicenseExp.setValue(LocalDate.parse(allDrivers.getLicense_exp()));
            txtLicenseType.setText(allDrivers.getLicense_type());
            txtMedical.setText(allDrivers.getMedical());
            txtStatus.setText(allDrivers.getStatus());
            btnDelete.setDisable(false);
            btnSave.setText("Update");
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Please Select the Value of Row").show();
        }
    }

    public void searchOnAction(KeyEvent keyEvent) {
        String text = txtSearch.getText();
        ArrayList<DriverDto> driverDtos = DriverModel.searchDriver(text);
        ObservableList<DriverTm> drivers = FXCollections.observableArrayList();
        for (DriverDto driverDto : driverDtos) {
            drivers.add(new DriverTm(driverDto.getName(), driverDto.getAddress(), driverDto.getContact(),
                    driverDto.getLicense_number(), driverDto.getLicense_exp(), driverDto.getLicense_type(), driverDto.getMedical(), driverDto.getStatus()));
        }
        tblDriver.setItems(drivers);
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Driver_Report.jrxml");
            JasperReport jasperReport1 = JasperCompileManager.compileReport(resourceAsStream);
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("date", String.valueOf(LocalDate.now()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport1, parameter, connection);
            JasperViewer.viewReport(jasperPrint, false);
        }catch (JRException e){
            e.printStackTrace();
        }
    }
}
