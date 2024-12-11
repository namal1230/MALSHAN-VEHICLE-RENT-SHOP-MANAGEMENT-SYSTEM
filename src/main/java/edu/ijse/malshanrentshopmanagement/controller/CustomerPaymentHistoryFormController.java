package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.CustomerPaymentDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.CustomerPaymentTm;
import edu.ijse.malshanrentshopmanagement.model.CustomerPaymentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomerPaymentHistoryFormController implements Initializable {

    public AnchorPane AncCustomer;
    public TableView<CustomerPaymentTm> tblPaymentHistory;
    public TableColumn<CustomerPaymentTm,Integer> colCustomerId;
    public TableColumn<CustomerPaymentTm,String> colMethod;
    public TableColumn<CustomerPaymentTm,String> colCard;
    public TableColumn<CustomerPaymentTm,Integer> colVehicle;
    public TableColumn<CustomerPaymentTm,String> colRental;
    public TableColumn<CustomerPaymentTm,String> colLocation;
    public TableColumn<CustomerPaymentTm,Integer> colDriverId;
    public JFXTextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colCard.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        colVehicle.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colRental.setCellValueFactory(new PropertyValueFactory<>("rentalPeriod"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("locationDistance"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        loadTables();
    }

    private void loadTables() {
        ArrayList<CustomerPaymentDto> allCustomerPayment = CustomerPaymentModel.getAllCustomerPayment();
        ObservableList<CustomerPaymentTm> customerPayment = FXCollections.observableArrayList();
        for (CustomerPaymentDto customerPaymentDto:allCustomerPayment){
            customerPayment.add(new CustomerPaymentTm(customerPaymentDto.getCustomerId(),customerPaymentDto.getMethod(),customerPaymentDto.getCardNumber(),
                    customerPaymentDto.getVehicleId(),customerPaymentDto.getRentalPeriod(),customerPaymentDto.getLocationDistance(),customerPaymentDto.getDriverId()));
        }
        tblPaymentHistory.setItems(customerPayment);
    }

    public void searchOnAction(MouseEvent mouseEvent) {
        String text = txtSearch.getText();
        ArrayList<CustomerPaymentDto> customerPaymentDtos = CustomerPaymentModel.searchCustomer(text);
        ObservableList<CustomerPaymentTm> customerPayment = FXCollections.observableArrayList();
        for (CustomerPaymentDto customerPaymentDto:customerPaymentDtos){
            customerPayment.add(new CustomerPaymentTm(customerPaymentDto.getCustomerId(),customerPaymentDto.getMethod(),customerPaymentDto.getCardNumber(),
                    customerPaymentDto.getVehicleId(),customerPaymentDto.getRentalPeriod(),customerPaymentDto.getLocationDistance(),customerPaymentDto.getDriverId()));
        }
        tblPaymentHistory.setItems(customerPayment);
    }

    public void backOnAction(MouseEvent mouseEvent) {
        try{
            AncCustomer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CustomerManagementForm.fxml"));
            AncCustomer.getChildren().add(load);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
        }
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Customer_payment.jrxml");
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
