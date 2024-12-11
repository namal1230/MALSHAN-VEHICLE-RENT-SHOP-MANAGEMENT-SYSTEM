package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.dto.VehicleDto;
import edu.ijse.malshanrentshopmanagement.model.VehicleModel;
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

public class AddVehicleFormController implements Initializable {
    public AnchorPane AncAddVehicle;
    public Label txtId;
    public JFXTextField txtBrand;
    public JFXTextField txtModel;
    public JFXTextField txtChassieNumber;
    public JFXTextField txtColor;
    public DatePicker txtDate;
    public JFXTextField txtPrice;
    public JFXTextField txtRegisterCode;
    public JFXButton btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genereateId();
    }


    private void clearTextFields() {
        txtBrand.clear();
        txtModel.clear();
        txtChassieNumber.clear();
        txtColor.clear();
        txtDate.setValue(null);
        txtPrice.clear();
        txtRegisterCode.clear();
        genereateId();
    }

    private void genereateId() {
        String id = VehicleModel.getId();
        txtId.setText(id);
    }

    public void backOnAction(MouseEvent mouseEvent) {
        try {
            AncAddVehicle.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/VehicleManagementForm.fxml"));
            AncAddVehicle.getChildren().add(load);
        } catch (IOException e) {
            System.out.println("CannotLoad UI");
        }
    }

    public void SaveOnAction(ActionEvent event) {
        boolean isValid=true;
        try {
            String chassieNumber = txtChassieNumber.getText();
            if (!Pattern.matches("^[A-HJ-NPR-Z0-9]{17}$", chassieNumber)) {
                txtChassieNumber.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtChassieNumber.setStyle("");
            }
            String id = txtId.getText();
            if (!Pattern.matches("^[0-9]+$", id)) {
                txtId.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtId.setStyle("");
            }
            String brand = txtBrand.getText();
            if (!Pattern.matches("^[A-Za-z]+( [A-Za-z]+)*$", brand)) {
                txtBrand.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtBrand.setStyle("");
            }
            String model = txtModel.getText();
            if (!Pattern.matches("^[A-Za-z]+( [A-Za-z]+)*$", model)) {
                txtModel.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtModel.setStyle("");
            }
            String color = txtColor.getText();
            if (!Pattern.matches("^[a-zA-Z\\s]+$", color)) {
                txtColor.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtColor.setStyle("");
            }
            String date = txtDate.getValue().toString();
            double price = Double.parseDouble(txtPrice.getText());
            if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", String.valueOf(price))) {
                txtPrice.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtPrice.setStyle("");
            }
            String registrationCode = txtRegisterCode.getText();
            if (!Pattern.matches("[A-Z]{3} \\d{4}$", registrationCode)) {
                txtRegisterCode.setStyle("-fx-text-fill: RED");
                isValid = false;
            } else {
                txtRegisterCode.setStyle("");
            }
            if (chassieNumber.isEmpty() || id.isEmpty() || brand.isEmpty() || model.isEmpty() || color.isEmpty() || date.isEmpty() || String.valueOf(price).isEmpty() || registrationCode.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Pleas Fill All Values").show();
                return;
            }
            if (!isValid) {
                return;
            }

            boolean isAvailable = VehicleModel.searchVehicles(chassieNumber);
            if (isAvailable) {
                boolean isUpdate = VehicleModel.updateVehicle(new VehicleDto(Integer.parseInt(id), brand, model,
                        chassieNumber, color, date, price, registrationCode));
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle Update Successfully").show();
                    clearTextFields();
                    genereateId();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Vehicle Not Updated").show();
                }
            }
            boolean isSave = VehicleModel.saveVehicle(new VehicleDto(Integer.parseInt(id), brand, model,
                    chassieNumber, color, date, price, registrationCode));
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Save Successfully").show();
                clearTextFields();
                genereateId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Vehicle Not Saved").show();
            }
        }catch (NullPointerException e){
            System.out.println("Values are missing.");
            new Alert(Alert.AlertType.WARNING,"Please check Values Again").show();
        }
    }
    public void getVehicleOnAction(MouseEvent mouseEvent) {
        String chassies = txtChassieNumber.getText();
        VehicleDto vehicleValues = VehicleModel.getVehicleValues(chassies);
        if (vehicleValues != null) {
            txtId.setText(String.valueOf(vehicleValues.getId()));
            txtBrand.setText(vehicleValues.getBrand());
            txtModel.setText(vehicleValues.getModel());
            txtChassieNumber.setText(vehicleValues.getChassie_number());
            txtColor.setText(vehicleValues.getColor());
            txtDate.setValue(LocalDate.parse(vehicleValues.getDate()));
            txtPrice.setText(String.valueOf(vehicleValues.getPrice()));
            txtRegisterCode.setText(vehicleValues.getRegistration_code());
            btnSave.setText("Upate");
        } else {
            new Alert(Alert.AlertType.WARNING, "Not Value Founded").show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

}

