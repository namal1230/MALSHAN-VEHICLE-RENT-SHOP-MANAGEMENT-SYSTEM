package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.dto.EmployeeDto;
import edu.ijse.malshanrentshopmanagement.model.EmployeeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddEmployeeFormController implements Initializable {

    public AnchorPane AncEmployee;
    public Label txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public DatePicker txtDOB;
    public JFXTextField txtStatus;
    public JFXTextField txtJobRole;
    public JFXTextField txtDepartment;
    public DatePicker txtHire;
    public JFXTextField txtNic;
    public JFXTextField txtPeriod;
    public DatePicker txtPeriodDate;
    public JFXButton btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateIds();
    }

    private void generateIds() {
        String id = EmployeeModel.generateId();
        txtId.setText(id);
    }

    private void clearTextFields() {
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtDOB.setValue(null);
        txtJobRole.clear();
        txtDepartment.clear();
        txtNic.clear();
        txtStatus.clear();
        txtHire.setValue(null);
        txtPeriod.clear();
        txtPeriodDate.setValue(null);
        generateIds();
    }

    public void backOnAction(MouseEvent mouseEvent) {
        try {
            AncEmployee.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/EmployeeManagementForm.fxml"));
            AncEmployee.getChildren().add(load);
        } catch (IOException e) {
            System.out.println("CannotLoad UI");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            boolean isLegal = true;
            String id = txtId.getText();
            if (!Pattern.matches("^[0-9]+$", id)) {
                txtId.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtId.setStyle("");
            }
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
            String email = txtEmail.getText();
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
                txtEmail.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtEmail.setStyle("");
            }
            String dob = txtDOB.getValue().toString();
            String jobRole = txtJobRole.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", jobRole)) {
                txtJobRole.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtJobRole.setStyle("");
            }
            String department = txtDepartment.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", department)) {
                txtDepartment.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtDepartment.setStyle("");
            }
            String nic = txtNic.getText();
            if (!Pattern.matches("(^\\d{9}[Vv]$)|(^\\d{12}$)", nic)) {
                txtNic.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtNic.setStyle("");
            }
            String status = txtStatus.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", status)) {
                txtStatus.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtStatus.setStyle("");
            }
            String hire = txtHire.getValue().toString();
            String period = txtPeriod.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", period)) {
                txtPeriod.setStyle("-fx-text-fill: RED");
                isLegal = false;
            } else {
                txtPeriod.setStyle("");
            }
            if (LocalDate.parse(dob).isAfter(LocalDate.now())) {
                txtDOB.setStyle("-fx-text-fill: RED");
            } else {
                txtDOB.setStyle("");
            }
            String periodDate = txtPeriodDate.getValue().toString();
            if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty() || dob.isEmpty() || jobRole.isEmpty() || department.isEmpty() || nic.isEmpty() || status.isEmpty() || hire.isEmpty() || period.isEmpty() || periodDate.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Fill All Values").show();
                return;
            }
            if (!isLegal) {
                return;
            }
            boolean isAvailable = EmployeeModel.searchEmployees(nic);
            if (isAvailable) {
                boolean isUpdate = EmployeeModel.updateEmployee(new EmployeeDto(Integer.parseInt(id), name, address, contact, email, dob, jobRole, department, nic, status, hire, period, periodDate));
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Updated").show();
                    clearTextFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Updated").show();
                }
            } else {
                boolean isSave = EmployeeModel.saveEmployee(new EmployeeDto(Integer.parseInt(id), name, address, contact, email, dob, jobRole, department, nic, status, hire, period, periodDate));
                if (isSave) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Save Successfully..").show();
                    clearTextFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Emploee Not Save..").show();
                }
            }
        }catch (NullPointerException e){
            System.out.println("Some Values are missing..");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }
    public void getValueOnAction(MouseEvent mouseEvent) {
        String nic = txtNic.getText();
        EmployeeDto employeeValues = EmployeeModel.getEmployeeValues(nic);
        if (employeeValues != null) {
            txtId.setText(String.valueOf(employeeValues.getId()));
            txtName.setText(employeeValues.getName());
            txtAddress.setText(employeeValues.getAddress());
            txtContact.setText(employeeValues.getContact());
            txtEmail.setText(employeeValues.getEmail());
            txtDOB.setValue(LocalDate.parse(employeeValues.getDob()));
            txtJobRole.setText(employeeValues.getJobRole());
            txtDepartment.setText(employeeValues.getDepartment());
            txtStatus.setText(employeeValues.getStatus());
            txtHire.setValue(LocalDate.parse(employeeValues.getHire()));
            txtPeriod.setText(employeeValues.getPeriod());
            txtPeriodDate.setValue(LocalDate.parse(employeeValues.getPeriodDate()));
            btnSave.setText("Update");
        } else {
            new Alert(Alert.AlertType.WARNING, "Not Value Founded").show();
        }
    }
    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

}
