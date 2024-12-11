package edu.ijse.malshanrentshopmanagement.controller;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.VehicleDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.VehicleTm;
import edu.ijse.malshanrentshopmanagement.model.VehicleModel;
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


public class VehicleManagementFormController implements Initializable {
    public AnchorPane AncVehicle;
    public TableView<VehicleTm> tblVehicle;
    public TableColumn<VehicleTm,String> colId;
    public TableColumn<VehicleTm,String> colBrand;
    public TableColumn<VehicleTm,String> colModel;
    public TableColumn<VehicleTm,String> colChassieNumber;
    public TableColumn<VehicleTm,String> colColor;
    public TableColumn<VehicleTm,String> colDate;
    public TableColumn<VehicleTm,Double> colPrice;
    public TableColumn<VehicleTm,String> colRegisterCode;
    public TextField txtSearchVehicle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colChassieNumber.setCellValueFactory(new PropertyValueFactory<>("chassie_number"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colRegisterCode.setCellValueFactory(new PropertyValueFactory<>("register_code"));
        loadtable();
    }

    private void loadtable() {
        try {
            ArrayList<VehicleDto> allVehicle = VehicleModel.getAllVehicle();
            ObservableList<VehicleTm> objects = FXCollections.observableArrayList();
            for (VehicleDto vehicleDto : allVehicle) {
                VehicleTm vehicleTm = new VehicleTm(vehicleDto.getId(), vehicleDto.getBrand(), vehicleDto.getModel(),
                        vehicleDto.getChassie_number(), vehicleDto.getColor(), vehicleDto.getDate(),
                        vehicleDto.getPrice(), vehicleDto.getRegistration_code());
                objects.add(vehicleTm);
            }
            tblVehicle.setItems(objects);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addVehicleOnAction(MouseEvent mouseEvent) {
        loadUI("AddVehicleForm.fxml");
    }

    public void manageInsuranceOnAction(MouseEvent mouseEvent) {
        loadUI("InsuranceManagementForm.fxml");
    }

    public void vehicleRepairOnAction(MouseEvent mouseEvent) {
        loadUI("VehicleMaintainanceForm.fxml");
    }

    public void manageDriverOnAction(MouseEvent mouseEvent) {
        loadUI("ManageDriverForm.fxml");
    }
    public void loadUI(String location){
        try{
            AncVehicle.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + location));
            AncVehicle.getChildren().add(load);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
            e.printStackTrace();
        }
    }
    public void searchOnAction(KeyEvent keyEvent) {
       searchText();
    }

    private void searchText() {
        String text = txtSearchVehicle.getText();
        ArrayList<VehicleDto> vehicleDtos = VehicleModel.searchVehicleTextField(text);
        ObservableList<VehicleTm> objects = FXCollections.observableArrayList();
        for (VehicleDto vehicleDto:vehicleDtos){
            objects.add(new VehicleTm(vehicleDto.getId(),vehicleDto.getBrand(),vehicleDto.getModel(),
                    vehicleDto.getChassie_number(),vehicleDto.getColor(),vehicleDto.getDate(),
                    vehicleDto.getPrice(),vehicleDto.getRegistration_code()));
        }
        tblVehicle.setItems(objects);
    }

    public void deleteOnAction(MouseEvent mouseEvent) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete selected row", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                VehicleTm selectedItem = tblVehicle.getSelectionModel().getSelectedItem();
                boolean isDeleted = VehicleModel.deleteVehicle(new VehicleDto(selectedItem.getId(),
                        selectedItem.getBrand(), selectedItem.getModel(),
                        selectedItem.getChassie_number(), selectedItem.getColor(),
                        selectedItem.getDate(), selectedItem.getPrice(),
                        selectedItem.getRegister_code()));
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle Deleted Successfully").show();
                    loadtable();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING,"Please Select The Values of row").show();
        }
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Vehicle_Report.jrxml");
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
