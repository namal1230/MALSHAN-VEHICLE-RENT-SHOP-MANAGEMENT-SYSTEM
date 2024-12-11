package edu.ijse.malshanrentshopmanagement.controller;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.EmployeeDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.EmployeeTm;
import edu.ijse.malshanrentshopmanagement.model.EmployeeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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

public class EmployeeManagementFormController implements Initializable {

    public AnchorPane AncEmployee;
    public ImageView txtContact;
    public TableView<EmployeeTm> tblEmployee;
    public TableColumn<EmployeeTm,Integer> colId;
    public TableColumn<EmployeeTm,String> colName;
    public TableColumn<EmployeeTm,String> colAddress;
    public TableColumn<EmployeeTm,String> colContact;
    public TableColumn<EmployeeTm,String> colEmail;
    public TableColumn<EmployeeTm,String> colDOB;
    public TableColumn<EmployeeTm,String> colRole;
    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        loadtable();
    }

    private void loadtable() {
        ArrayList<EmployeeDto> allEmployee = EmployeeModel.getAllEmployee();
        ObservableList<EmployeeTm> employees = FXCollections.observableArrayList();
        for (EmployeeDto employee:allEmployee){
            employees.add(new EmployeeTm(employee.getId(),employee.getName(),employee.getAddress(),employee.getContact(),
                    employee.getEmail(),employee.getDob(),employee.getJobRole()));
        }
        tblEmployee.setItems(employees);
    }

    public void addEmployeeOnAction(MouseEvent mouseEvent) {
        loadUI("AddEmployeeForm.fxml");
    }

    public void backOnAction(MouseEvent mouseEvent) {
        loadUI("DashBoardForm.fxml");
    }

    public void manageSalaryOnAction(MouseEvent mouseEvent) {
        loadUI("EmployeeSalaryManagementForm.fxml");
    }
    public void loadUI(String location){
        try{
            AncEmployee.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + location));
            AncEmployee.getChildren().add(load);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
        }
    }

    public void getValueOnAction(MouseEvent mouseEvent) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete it?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
                boolean isDelete = EmployeeModel.deleteEmployee(selectedItem.getId());
                if (isDelete) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Delete Successfully").show();
                    loadtable();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Deleted").show();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING,"Please Select the Values of Row").show();
        }
    }

    public void searchOnAction(KeyEvent keyEvent) {
        String text = txtSearch.getText();
        ArrayList<EmployeeDto> employeeDtos = EmployeeModel.searchEmployee(text);
        ObservableList<EmployeeTm> employees = FXCollections.observableArrayList();
        for (EmployeeDto employeeDto:employeeDtos){
            employees.add(new EmployeeTm(employeeDto.getId(),employeeDto.getName(),employeeDto.getAddress(),employeeDto.getContact(),
                    employeeDto.getEmail(),employeeDto.getDob(),employeeDto.getJobRole()));
        }
        tblEmployee.setItems(employees);
    }

    public void eliminateEmployeesOnAction(MouseEvent mouseEvent) {
        loadUI("EliminateEmployeeForm.fxml");
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Employee_Report.jrxml");
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
