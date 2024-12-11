package edu.ijse.malshanrentshopmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {
    public AnchorPane AncDashBoard;
    public AnchorPane AncDash;
    public Label selectlbl;
    public ImageView imgBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgBack.setVisible(false);
    }

    public void loadUI(String location){

        try{
            AncDashBoard.setVisible(true);
            AncDashBoard.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/" + location));
            AncDashBoard.getChildren().add(load);
            imgBack.setVisible(true);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
        }
    }


    public void logOutOnAction(MouseEvent mouseEvent) {
        selectlbl.setText("Please Select One Of This");
        AncDashBoard.setVisible(false);
        imgBack.setVisible(false);
    }

    public void employeeOnAction(MouseEvent mouseEvent) {
        loadUI("EmployeeManagementForm.fxml");
    }

    public void vehicleOnAction(MouseEvent mouseEvent) {
        loadUI("VehicleManagementForm.fxml");
    }

    public void customerOnAction(MouseEvent mouseEvent) {
        loadUI("CustomerManagementForm.fxml");
    }

    public void logoutDashOnAction(MouseEvent mouseEvent) {
        try {
            AncDash.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
            AncDash.getChildren().add(load);
        }catch (IOException e){
            System.out.println("Cannot load Ui");
        }
    }

    public void customerOnMouse(MouseEvent mouseEvent) {
        selectlbl.setText("Customer Management Form");
    }

    public void vehicleOnMouse(MouseEvent mouseEvent) {
        selectlbl.setText("Vehicle Management Form");
    }

    public void employeeOnMouse(MouseEvent mouseEvent) {
        selectlbl.setText("Employee Management Form");
    }
}
