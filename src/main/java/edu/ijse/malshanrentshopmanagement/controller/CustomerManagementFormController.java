package edu.ijse.malshanrentshopmanagement.controller;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.CustomerDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.CustomerTm;
import edu.ijse.malshanrentshopmanagement.model.CustomerModel;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CustomerManagementFormController implements Initializable {
    public AnchorPane AncCustomer;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<CustomerTm,Integer> colId;
    public TableColumn<CustomerTm,String> colName;
    public TableColumn<CustomerTm,String> colAddress;
    public TableColumn<CustomerTm,String> colContact;
    public TableColumn<CustomerTm,String> colNic;
    public TableColumn<CustomerTm,String> colDob;
    public TableColumn<CustomerTm,String> colEmail;
    public TextField txtSearch;
    static ImageView image;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        ArrayList<CustomerDto> allCustomer = CustomerModel.getAllCustomer();
        ObservableList<CustomerTm> customers = FXCollections.observableArrayList();
        for (CustomerDto customerDto:allCustomer) {
            customers.add(new CustomerTm(customerDto.getId(),customerDto.getName(),
                    customerDto.getAddress(),customerDto.getContact(),customerDto.getNic(),customerDto.getDob(),customerDto.getEmail()));
        }
        tblCustomer.setItems(customers);

    }

    public void backOnAction(MouseEvent mouseEvent) {
        loadUI("DashBoardForm.fxml");
        DashBoardFormController dash=new DashBoardFormController();
    }

    public void addCustomerOnAction(MouseEvent mouseEvent) {
        loadUI("AddCustomerForm.fxml");
    }

    public void managePaymentOnAction(MouseEvent mouseEvent) {
        loadUI("ManageCustomerPaymentForm.fxml");
    }
    public void loadUI(String location){
        try{
            AncCustomer.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + location));
            AncCustomer.getChildren().add(load);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
        }
    }

    public void searchOnAction(KeyEvent actionEvent) {
        String text = txtSearch.getText();
        ArrayList<CustomerDto> customerDtos = CustomerModel.searchCustomer(text);
        ObservableList<CustomerTm> customers = FXCollections.observableArrayList();
        for (CustomerDto customerDto:customerDtos){
            customers.add(new CustomerTm(customerDto.getId(),customerDto.getName(),customerDto.getAddress(),
                    customerDto.getContact(),customerDto.getNic(),customerDto.getDob(),customerDto.getEmail()));
        }
        tblCustomer.setItems(customers);
    }

    public void getCustomerOnActon(MouseEvent mouseEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Delete it?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get()==ButtonType.YES) {
            CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
            boolean isDelete = CustomerModel.deleteCustomer(selectedItem.getId());
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Customer Delete Successfully..").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Customer Not Deleted..").show();
            }
        }
    }

    public void paymentHistoryOnAction(MouseEvent mouseEvent) {
        loadUI("CustomerPaymentHistoryForm.fxml");
    }

    public void generateReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Customer_Report.jrxml");
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
