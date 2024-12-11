package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.InsuranceDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.InsuranceTm;
import edu.ijse.malshanrentshopmanagement.model.InsuranceModel;
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

public class InsuranceManagementFormController implements Initializable {
    public AnchorPane AncVehicle;
    public DatePicker txtInsuranceExpir;
    public JFXTextField txtInsurancePolicyType;
    public JFXTextField txtVehicleChassisNumber;
    public DatePicker txtInsuranceStartDate;
    public Label txtId;
    public JFXTextField txtSearch;
    public JFXTextField txtInsurancePolicyNunber;
    public JFXTextField txtInsuranceProvider;
    public TableView<InsuranceTm> tblInsurance;
    public TableColumn<InsuranceTm, String> colEndDate;
    public TableColumn<InsuranceTm, String> colStartDate;
    public TableColumn<InsuranceTm, String> colPollicyType;
    public TableColumn<InsuranceTm, String> colPollicyNumber;
    public TableColumn<InsuranceTm, String> colChassieNumber;
    public TableColumn<InsuranceTm, String> colProvider;
    public TableColumn<InsuranceTm, Integer> colId;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
    }

    private void refreshPage() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProvider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        colChassieNumber.setCellValueFactory(new PropertyValueFactory<>("chassieNumber"));
        colPollicyNumber.setCellValueFactory(new PropertyValueFactory<>("policynumber"));
        colPollicyType.setCellValueFactory(new PropertyValueFactory<>("policyType"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clearTextFields();
        generateId();
        loadtable();
        btnDelete.setDisable(true);
    }

    private void clearTextFields() {
        txtInsuranceProvider.clear();
        txtVehicleChassisNumber.clear();
        txtInsurancePolicyNunber.clear();
        txtInsurancePolicyType.clear();
        txtInsuranceStartDate.setValue(null);
        txtInsuranceExpir.setValue(null);
        generateId();
        loadtable();
        btnDelete.setDisable(true);
    }

    private void generateId() {
        int insuranceId = InsuranceModel.getInsuranceId();
        String id = String.format("%03d", (insuranceId + 1));
        txtId.setText(id.toString());
    }

    private void loadtable() {
        ArrayList<InsuranceDto> allInsurance = InsuranceModel.getAllInsurance();
        ObservableList<InsuranceTm> insurance = FXCollections.observableArrayList();
        for (InsuranceDto insurancedto : allInsurance) {
            InsuranceTm insuranceTm = new InsuranceTm(insurancedto.getId(), insurancedto.getInsu_provider(), insurancedto.getVehicle_chassis(),
                    insurancedto.getInsu_policy_number(), insurancedto.getInsu_policy_type(), insurancedto.getStartDate(), insurancedto.getExpiryDate());
            insurance.add(insuranceTm);
        }
        tblInsurance.setItems(insurance);
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
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

    public void deleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Id").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure You Want To Delete it?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = InsuranceModel.deleteInsurance(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Insurance Delete SuccessFully").show();
                refreshPage();
            }
        }

    }

    public void saveOnAction(ActionEvent event) {
        try {
            boolean isValid = true;
            String id = txtId.getText();
            String provider = txtInsuranceProvider.getText();
            if (!Pattern.matches("^[A-Za-z]+( [A-Za-z]+)*$", provider)) {
                txtInsuranceProvider.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtInsuranceProvider.setStyle("");
            }
            String chassisNumber = txtVehicleChassisNumber.getText();
            if (!Pattern.matches("^[A-HJ-NPR-Z0-9]{17}$", chassisNumber)) {
                txtVehicleChassisNumber.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtVehicleChassisNumber.setStyle("");
            }
            String policyNumber = txtInsurancePolicyNunber.getText();
            if (!Pattern.matches("^[A-Z]{3,4}-?\\d{6,10}$", policyNumber)) {
                txtInsurancePolicyNunber.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtInsurancePolicyNunber.setStyle("");
            }
            String policyType = txtInsurancePolicyType.getText();
            if (!Pattern.matches("^(Comprehensive|Third-Party|Third-Party Fire & Theft|Personal Accident|Full Insurance|Third-Party Liability|Basic Coverage)$", policyType)) {
                txtInsurancePolicyType.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtInsurancePolicyType.setStyle("");
            }
            String startDate = txtInsuranceStartDate.getValue().toString();
            String expiryDate = txtInsuranceExpir.getValue().toString();
            if (id.isEmpty() || provider.isEmpty() || chassisNumber.isEmpty() || policyType.isEmpty() || policyNumber.isEmpty() || startDate.isEmpty() || expiryDate.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please Input All Values").show();
                return;
            }
            if (!isValid) {
                return;
            }
            boolean isAvailable = InsuranceModel.checkId(id);
            if (isAvailable) {
                boolean isUpdated = InsuranceModel.updateInsurance(new InsuranceDto(Integer.parseInt(id), provider, chassisNumber, policyNumber, policyType, startDate, expiryDate));
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Insurance Updated").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Insurance Not Updated").show();
                }
            } else {
                boolean isSave = InsuranceModel.saveInsurance(new InsuranceDto(Integer.parseInt(id), provider, chassisNumber, policyNumber, policyType, startDate, expiryDate));
                if (isSave) {
                    new Alert(Alert.AlertType.INFORMATION, "Insurance Save SuccessFully..!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Insurance Not Save..!");
                }
            }
        }catch (NullPointerException e){
            System.out.println("Values Are missing.");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }

    public void searchInsuranceOnAction(KeyEvent keyEvent) {
        String searchInsurance = txtSearch.getText();
        ArrayList<InsuranceDto> insuranceDtos = InsuranceModel.searchInsurance(searchInsurance);
        ObservableList<InsuranceTm> insurance = FXCollections.observableArrayList();
        for (InsuranceDto insuranceDto : insuranceDtos) {
            insurance.add(new InsuranceTm(insuranceDto.getId(), insuranceDto.getInsu_provider(), insuranceDto.getVehicle_chassis(),
                    insuranceDto.getInsu_policy_number(), insuranceDto.getInsu_policy_type(), insuranceDto.getStartDate(), insuranceDto.getExpiryDate()));
        }
        tblInsurance.setItems(insurance);
    }

    public void getValueOnAction(MouseEvent mouseEvent) {

        try {
            InsuranceTm selectedItem = tblInsurance.getSelectionModel().getSelectedItem();
            txtId.setText(String.valueOf(selectedItem.getId()));
            txtInsuranceProvider.setText(selectedItem.getProvider());
            txtVehicleChassisNumber.setText(selectedItem.getChassieNumber());
            txtInsurancePolicyNunber.setText(selectedItem.getPolicynumber());
            txtInsurancePolicyType.setText(selectedItem.getPolicyType());
            txtInsuranceStartDate.setValue(LocalDate.parse(selectedItem.getStartDate()));
            txtInsuranceExpir.setValue(LocalDate.parse(selectedItem.getEndDate()));
            btnDelete.setDisable(false);
            btnSave.setText("Update");
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Please Select the Value of Row").show();
        }
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Insurance_Report.jrxml");
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
