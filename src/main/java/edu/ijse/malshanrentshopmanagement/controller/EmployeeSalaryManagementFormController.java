package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.EmployeeDto;
import edu.ijse.malshanrentshopmanagement.dto.EmployeeSalaryDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.EmployeeSalaryTm;
import edu.ijse.malshanrentshopmanagement.model.EmployeeModel;
import edu.ijse.malshanrentshopmanagement.model.EmployeeSalaryModel;
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

public class EmployeeSalaryManagementFormController implements Initializable {
    public AnchorPane AncEmployee;
    public JFXTextField txtSalary;
    public JFXTextField txtBankAccount;
    public JFXTextField txtTax;
    public Label lblPeriod;
    public JFXTextField txtPeriodDate;
    public DatePicker txtDate;
    public JFXButton txtSave;
    public JFXTextField txtSearch;
    public TableView<EmployeeSalaryTm> tblEmployeeSalary;

    public TableColumn<EmployeeSalaryTm, Integer> colId;
    public TableColumn<EmployeeSalaryTm, String> colSalary;
    public TableColumn<EmployeeSalaryTm, String> colBankAccount;
    public TableColumn<EmployeeSalaryTm, String> colTax;
    public TableColumn<EmployeeSalaryTm, String> colPeriod;
    public TableColumn<EmployeeSalaryTm, String> colDate;
    public JFXComboBox<Integer> cmbId;
    public JFXButton btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colBankAccount.setCellValueFactory(new PropertyValueFactory<>("bankAccount"));
        colTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
        colPeriod.setCellValueFactory(new PropertyValueFactory<>("probationPeriod"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        generateIds();
        loadtable();
        btnDelete.setDisable(true);
    }

    private void generateIds() {
        ArrayList<Integer> allId = EmployeeModel.getAllId();
        ObservableList<Integer> id = FXCollections.observableArrayList();
        for (Integer value : allId) {
            id.add(value);
        }
        cmbId.setItems(id);
    }

    private void loadtable() {
        ArrayList<EmployeeSalaryDto> allEmployeeSalary = EmployeeSalaryModel.getAllEmployeeSalary();
        ObservableList<EmployeeSalaryTm> employeeSalary = FXCollections.observableArrayList();
        for (EmployeeSalaryDto employee : allEmployeeSalary) {
            employeeSalary.add(new EmployeeSalaryTm(employee.getId(), employee.getSalary(), employee.getBank_account(), employee.getTax(),
                    employee.getProbation_period(), employee.getDate()));
        }
        tblEmployeeSalary.setItems(employeeSalary);
    }

    private void clearTextFields() {
        txtSalary.clear();
        txtBankAccount.clear();
        txtTax.clear();
        btnDelete.setDisable(true);
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
            boolean isValid = true;
            int id = cmbId.getSelectionModel().getSelectedItem();
            String salary = txtSalary.getText();
            if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", salary)) {
                txtSalary.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtSalary.setStyle("");
            }
            String bankAccount = txtBankAccount.getText();
            if (!Pattern.matches("^\\d{12}$", bankAccount)) {
                txtBankAccount.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtBankAccount.setStyle("");
            }
            String tax = txtTax.getText();
            if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", tax)) {
                txtTax.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtTax.setStyle("");
            }
            String period = txtPeriodDate.getText();
            if (!Pattern.matches("^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", period)) {
                txtPeriodDate.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtPeriodDate.setStyle("");
            }
            String date = txtDate.getValue().toString();
            if (String.valueOf(id).isEmpty() || salary.isEmpty() || bankAccount.isEmpty() || tax.isEmpty() || period.isEmpty() || date.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Fill All Values").show();
                return;
            }
            if (!isValid) {
                return;
            }
            boolean isSave = EmployeeSalaryModel.saveEmployee(new EmployeeSalaryDto(id, salary, bankAccount, tax, period, date));
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Salary Saved..").show();
                clearTextFields();
                loadtable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Employee Salary Not Saved..").show();
            }
        }catch (NullPointerException e){
            System.out.println("Some Values are missing.");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        if (cmbId.toString().isEmpty() || txtSalary.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Values Before Delete").show();
        } else {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete it?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                int id = cmbId.getSelectionModel().getSelectedItem();
                boolean isDelete = EmployeeSalaryModel.deleteEmployeeSalary(id);
                if (isDelete) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Salary Delete Successfully..").show();
                    clearTextFields();
                    loadtable();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Salary Not Deleted..").show();
                }
            }
        }
    }

    public void searchEmployeeSOnAction(KeyEvent keyEvent) {
        String text = txtSearch.getText();
        ArrayList<EmployeeSalaryDto> employeeSalaryDtos = EmployeeSalaryModel.searchEmployeeSalary(text);
        ObservableList<EmployeeSalaryTm> employeeSalary = FXCollections.observableArrayList();
        for (EmployeeSalaryDto employee : employeeSalaryDtos) {
            employeeSalary.add(new EmployeeSalaryTm(employee.getId(), employee.getSalary(), employee.getBank_account(), employee.getTax(),
                    employee.getProbation_period(), employee.getDate()));
        }
        tblEmployeeSalary.setItems(employeeSalary);
    }

    public void getValueOnAction(MouseEvent mouseEvent) {
        try {
            EmployeeSalaryTm selectedItem = tblEmployeeSalary.getSelectionModel().getSelectedItem();
            cmbId.setValue(selectedItem.getId());
            txtSalary.setText(selectedItem.getSalary());
            txtBankAccount.setText(selectedItem.getBankAccount());
            txtTax.setText(selectedItem.getTax());
            txtPeriodDate.setText(selectedItem.getProbationPeriod());
            txtDate.setValue(LocalDate.parse(selectedItem.getDate()));
            btnDelete.setDisable(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Please Select the Values of Row").show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

    public void idOnAction(ActionEvent actionEvent) {
        int selectedItem = cmbId.getSelectionModel().getSelectedItem();
        EmployeeDto values = EmployeeModel.getValues(selectedItem);
        lblPeriod.setText(values.getPeriod());
        txtPeriodDate.setText(values.getPeriodDate());

    }

    public void dateOnAction(ActionEvent actionEvent) {
        String date = txtDate.getValue().toString();
        LocalDate dates = LocalDate.parse(date);
        String period = txtPeriodDate.getText().toString();
        LocalDate periods = LocalDate.parse(period);
        if (dates.isAfter(periods)) {
            txtPeriodDate.setStyle("-fx-text-fill: RED");
        }else {txtPeriodDate.setStyle("");}
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Employee_Salary.jrxml");
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
