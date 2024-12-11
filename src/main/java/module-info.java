module edu.ijse.malshanrentshopmanagement.malshanrentshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires lombok;
    requires jasperreports;

    opens edu.ijse.malshanrentshopmanagement.dto.tm to javafx.base;
    opens edu.ijse.malshanrentshopmanagement.controller to javafx.fxml;
    exports edu.ijse.malshanrentshopmanagement;
}