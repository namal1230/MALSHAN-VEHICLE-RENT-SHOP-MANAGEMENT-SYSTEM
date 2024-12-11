package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.EliminateEmployeeDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.EliminateEmployeeTm;
import edu.ijse.malshanrentshopmanagement.model.EliminateEmployeeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class EliminateEmployeeFormController implements Initializable {

    public AnchorPane AncEmployee;
    public ImageView txtContact;
    public TableView<EliminateEmployeeTm> tblEmployee;
    public TableColumn<EliminateEmployeeTm,Integer> colId;
    public TableColumn<EliminateEmployeeTm,String> colName;
    public TableColumn<EliminateEmployeeTm,String> colAddress;
    public TableColumn<EliminateEmployeeTm,String> colContact;
    public TableColumn<EliminateEmployeeTm,String> colEmail;
    public TableColumn<EliminateEmployeeTm,String> colDOB;
    public TableColumn<EliminateEmployeeTm,String> colRole;
    public JFXTextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        loadTables();
    }

    private void loadTables() {
        ArrayList<EliminateEmployeeDto> allEliminates = EliminateEmployeeModel.getAllEliminates();
        ObservableList<EliminateEmployeeTm> eliminateEmployees = FXCollections.observableArrayList();
        for (EliminateEmployeeDto eliminateEmployeeDto:allEliminates){
            eliminateEmployees.add(new EliminateEmployeeTm(eliminateEmployeeDto.getId(),eliminateEmployeeDto.getName(),eliminateEmployeeDto.getAddress(),
                    eliminateEmployeeDto.getContact(),eliminateEmployeeDto.getEmail(),eliminateEmployeeDto.getDob(),eliminateEmployeeDto.getJobRole()));
        }
        tblEmployee.setItems(eliminateEmployees);
    }

    public void getValueOnAction(MouseEvent mouseEvent) {
        try {
            EliminateEmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
            boolean isReplace = EliminateEmployeeModel.replaceEliminateEmployee(selectedItem.getId());
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Replace it?", ButtonType.NO, ButtonType.YES).showAndWait();
            if (buttonType.get()==ButtonType.YES) {
                if (isReplace) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Replace Successfully").show();
                    loadTables();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Replace").show();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING,"Please Select the Values of Row..").show();
        }
    }

    public void searchOnAction(KeyEvent keyEvent) {
        String text = txtSearch.getText();
        ArrayList<EliminateEmployeeDto> eliminateEmployeeDtos = EliminateEmployeeModel.searchEmployee(text);
        ObservableList<EliminateEmployeeTm> eliminateEmployees = FXCollections.observableArrayList();
        for (EliminateEmployeeDto employees:eliminateEmployeeDtos){
            eliminateEmployees.add(new EliminateEmployeeTm(employees.getId(),employees.getName(),employees.getAddress(),
                    employees.getContact(),employees.getEmail(),employees.getDob(),employees.getJobRole()));
        }
        tblEmployee.setItems(eliminateEmployees);
    }

    public void backOnAction(MouseEvent mouseEvent) {
        try {
            AncEmployee.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/EmployeeManagementForm.fxml"));
            AncEmployee.getChildren().add(load);
        }catch(IOException e){
            System.out.println("CannotLoad UI");
        }
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Eliminate_Employee_Report.jrxml");
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
