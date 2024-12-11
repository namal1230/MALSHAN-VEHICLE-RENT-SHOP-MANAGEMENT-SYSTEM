package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.MaintenanceDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.VehicleMaintainTm;
import edu.ijse.malshanrentshopmanagement.model.MaintenanceModel;
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

public class VehicleMaintainanceFormController implements Initializable {
    public AnchorPane AncVehicle;
    public JFXTextField txtRepairCost;
    public JFXButton btnSave;
    public TableView<VehicleMaintainTm> tblMaintenance;
    public TableColumn<VehicleMaintainTm, String> colId;
    public TableColumn<VehicleMaintainTm, String> colVehicleChassisNumber;
    public TableColumn<VehicleMaintainTm, String> colMaintenanceType;
    public TableColumn<VehicleMaintainTm, String> colLastMaintenance;
    public TableColumn<VehicleMaintainTm, String> colNextMaintenance;
    public TableColumn<VehicleMaintainTm, String> colRepairCost;
    public JFXTextField txtSearch;
    public JFXButton btnDelete;
    public DatePicker txtNextMaintenance;
    public DatePicker txtLastMaintenance;
    public JFXTextField txtVehicleId;
    public JFXTextField txtMaintenanceType;
    public Label txtId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
    }

    private void refreshPage() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colVehicleChassisNumber.setCellValueFactory(new PropertyValueFactory<>("VehicleChassisNumber"));
        colMaintenanceType.setCellValueFactory(new PropertyValueFactory<>("MaintenanceType"));
        colLastMaintenance.setCellValueFactory(new PropertyValueFactory<>("LastMaintenance"));
        colNextMaintenance.setCellValueFactory(new PropertyValueFactory<>("NextMaintenance"));
        colRepairCost.setCellValueFactory(new PropertyValueFactory<>("RepairCost"));
        loadtable();
        clearTextFields();
        generateIds();
        btnDelete.setDisable(true);
    }

    private void generateIds() {
        int id = MaintenanceModel.getId();
        String generateId = String.format("%03d", (id + 1));
        txtId.setText(generateId);
    }

    private void clearTextFields() {
        txtVehicleId.clear();
        txtMaintenanceType.clear();
        txtLastMaintenance.setValue(null);
        txtNextMaintenance.setValue(null);
        txtRepairCost.clear();
        loadtable();
        generateIds();
        btnDelete.setDisable(true);
    }

    private void loadtable() {
        ArrayList<MaintenanceDto> all = MaintenanceModel.getAll();
        ObservableList<VehicleMaintainTm> objects = FXCollections.observableArrayList();
        for (MaintenanceDto maintenanceDto : all) {
            objects.add(new VehicleMaintainTm(maintenanceDto.getId(), maintenanceDto.getVehicle_chassis(), maintenanceDto.getMaintenance_type(),
                    maintenanceDto.getLast_maintenance(), maintenanceDto.getNext_maintenance(), maintenanceDto.getRepairCost()));
        }
        tblMaintenance.setItems(objects);
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

    public void searchMaintenanceOnAction(KeyEvent keyEvent) {
        String searchMaintenance = txtSearch.getText();
        ArrayList<MaintenanceDto> maintenanceDtos = MaintenanceModel.searchMaintenance(searchMaintenance);
        ObservableList<VehicleMaintainTm> maintenance = FXCollections.observableArrayList();
        for (MaintenanceDto maintenanceDto : maintenanceDtos) {
            maintenance.add(new VehicleMaintainTm(maintenanceDto.getId(), maintenanceDto.getVehicle_chassis(),
                    maintenanceDto.getMaintenance_type(), maintenanceDto.getLast_maintenance(), maintenanceDto.getNext_maintenance(), maintenanceDto.getRepairCost()));
        }
        tblMaintenance.setItems(maintenance);
    }

    public void deleteOnAction(ActionEvent event) {
        String ids = txtId.getText();
        if (ids.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Id").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Dlete it?").showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            int id = Integer.parseInt(txtId.getText());
            boolean isDelete = MaintenanceModel.deleteMaintenance(id);
            if (isDelete) new Alert(Alert.AlertType.INFORMATION, "Maintenance Delete SuccessFully..").show();
            else new Alert(Alert.AlertType.WARNING, "Maintenance Not Deleted..").show();
        }
    }

    public void saveOnAction(ActionEvent event) {
        try {
            boolean isValid = true;
            String id = txtId.getText();
            if (!Pattern.matches("^[0-9]+$", id)) {
                txtId.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtId.setStyle("");
            }
            String vehicleChassis = txtVehicleId.getText();
            if (!Pattern.matches("^[A-HJ-NPR-Z0-9]{17}$", vehicleChassis)) {
                txtVehicleId.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtVehicleId.setStyle("");
            }
            String maintainanceType = txtMaintenanceType.getText();
            if (!Pattern.matches("^[A-Za-z0-9 ]{2,50}$", maintainanceType)) {
                txtMaintenanceType.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtMaintenanceType.setStyle("");
            }
            String lastMaintenance = txtLastMaintenance.getValue().toString();
            String nextMaintenance = txtNextMaintenance.getValue().toString();
            String repairCost = txtRepairCost.getText();
            if (LocalDate.parse(nextMaintenance).isAfter(LocalDate.parse(lastMaintenance))) {
                txtNextMaintenance.setStyle("-fx-text-fill: RED");
            } else {
                txtNextMaintenance.setStyle("");
            }
            if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", repairCost)) {
                txtRepairCost.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtRepairCost.setStyle("");
            }
            if (id.isEmpty() || vehicleChassis.isEmpty() || maintainanceType.isEmpty() || lastMaintenance.isEmpty() || nextMaintenance.isEmpty() || repairCost.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Input All Values").show();
                return;
            }
            if (!isValid) {
                return;
            }
            boolean isAvailable = MaintenanceModel.searchId(id);
            if (isAvailable) {
                boolean isUpdate = MaintenanceModel.updateMaintenance(new MaintenanceDto(Integer.parseInt(id), vehicleChassis, maintainanceType, lastMaintenance, nextMaintenance, Double.parseDouble(repairCost)));
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Maintenance Update Successfully..").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Maintenance Not Updated...").show();
                }
            } else {
                boolean isSave = MaintenanceModel.saveMaintenance(new MaintenanceDto(Integer.parseInt(id), vehicleChassis, maintainanceType, lastMaintenance, nextMaintenance, Double.parseDouble(repairCost)));
                if (isSave) {
                    new Alert(Alert.AlertType.INFORMATION, "Maintenance Save Successfully..!").show();
                    loadtable();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Maintenance Not Save...").show();
                }
            }
        }catch (NullPointerException e){
            System.out.println("Values Are missing..");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }

    }

    public void getValueOnAction(MouseEvent mouseEvent) {
        try {
            VehicleMaintainTm vehicleMaintainTm = tblMaintenance.getSelectionModel().getSelectedItem();
            txtId.setText(String.valueOf(vehicleMaintainTm.getId()));
            txtVehicleId.setText(vehicleMaintainTm.getVehicleChassisNumber());
            txtMaintenanceType.setText(vehicleMaintainTm.getMaintenanceType());
            txtLastMaintenance.setValue(LocalDate.parse(vehicleMaintainTm.getLastMaintenance()));
            txtNextMaintenance.setValue(LocalDate.parse(vehicleMaintainTm.getNextMaintenance()));
            txtRepairCost.setText(String.valueOf(vehicleMaintainTm.getRepairCost()));
            btnSave.setText("Update");
            btnDelete.setDisable(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Please Select Correct Table Row").show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Vehicle_Repair_Report.jrxml");
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
