package edu.ijse.malshanrentshopmanagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.ijse.malshanrentshopmanagement.AppInitializer;
import edu.ijse.malshanrentshopmanagement.dto.UserDto;
import edu.ijse.malshanrentshopmanagement.model.LoginModel;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginFormController implements Initializable {
    public AnchorPane Anclogin;
    public JFXPasswordField txtPassword;
    public JFXTextField txtUserId;

    private int passwordNumber=0;
    private static LoginModel loginModel = new LoginModel();
    public JFXTextField txtShownPassword;

    public void loginOnAction(ActionEvent event){
        boolean isValids=true;
        String userId = txtUserId.getText();
        if (Pattern.matches("^[a-zA-Z][a-zA-Z0-9_-]{4,19}$",userId)){
            new Alert(Alert.AlertType.WARNING,"User Id Pattern is Invalid").show();
            txtUserId.setStyle("-fx-text-fill: RED");
            isValids=false;
        }else {
            txtUserId.setStyle("");
        }
        String password=txtPassword.getText();
        String shownPassword = txtShownPassword.getText();
        if (Pattern.matches("^[a-zA-Z][a-zA-Z0-9_-]*[!@#$%^&*(),.?\":{}|<>].{8,20}$",password) || Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,20}$",shownPassword)){
            new Alert(Alert.AlertType.WARNING,"User Password is Invalid").show();
            txtPassword.setStyle("-fx-text-fill: RED");
            txtShownPassword.setStyle("-fx-text-fill: RED");
            isValids=false;
        }else {
            txtPassword.setStyle("");
            txtShownPassword.setStyle("");
        }
        if (userId.isEmpty() || (password.isEmpty() && shownPassword.isEmpty())){
            new Alert(Alert.AlertType.WARNING,"Please Fill All Values").show();
            return;
        }
        if (!isValids){
            return;
        }
        boolean isValid=loginModel.checkCredentials(new UserDto(userId,password,shownPassword));
        if(isValid) {
            try {
                Anclogin.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
                Anclogin.getChildren().add(load);
            } catch (IOException e) {
                System.out.println("Class Cannot Load");
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure Your Username and Password is Valid?");
            alert.setX(1000);
            alert.setY(600);
            alert.setHeaderText("Invalid User Id Or Password");
            alert.show();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShownPassword.setVisible(false);
    }

    public void shownPasswordOnAction(MouseEvent mouseEvent) {
        if(passwordNumber==0) {
            String shownPassword = txtPassword.getText();
            txtShownPassword.setText(shownPassword);
            txtPassword.setVisible(false);
            txtShownPassword.setVisible(true);
            passwordNumber = 1;
        }else{
            String hiddenPassword=txtShownPassword.getText();
            txtPassword.setText(hiddenPassword);
            txtShownPassword.setVisible(false);
            txtPassword.setVisible(true);
            passwordNumber=0;
        }
    }

    public void exitOnAction(MouseEvent mouseEvent){
        System.exit(0);
    }
}
