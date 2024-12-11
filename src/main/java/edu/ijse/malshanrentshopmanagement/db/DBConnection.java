package edu.ijse.malshanrentshopmanagement.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Getter
public class DBConnection {
    private Connection connection;
    private static DBConnection dbconnection;
    private DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRentalShop", "root", "PHW#84#jeor");
        } catch (SQLException e) {
            System.out.println("Database Connection Issue");
        }
    }
    public static DBConnection getInstance(){
        if(dbconnection==null){
            dbconnection=new DBConnection();
        }
        return dbconnection;
    }
}
