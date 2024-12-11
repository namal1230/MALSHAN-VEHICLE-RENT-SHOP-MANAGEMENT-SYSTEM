package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.DriverDto;
import edu.ijse.malshanrentshopmanagement.dto.tm.DriverTm;
import edu.ijse.malshanrentshopmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {

    public static boolean saveDriver(DriverDto driverDto) {
        try {
            return CrudUtil.execute("INSERT INTO driver (name,address,contact,email,dob,nic,license_number,license_exp,license_type,medical,status) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
            driverDto.getName(), driverDto.getAddress(), driverDto.getContact(), driverDto.getEmail()
            , driverDto.getDob(), driverDto.getNic(), driverDto.getLicense_number(), driverDto.getLicense_exp()
            , driverDto.getLicense_type(), driverDto.getMedical(), driverDto.getStatus());
        }catch (SQLException e){
            System.out.println("Driver Save Issue..");
        }
        return false;
    }

    public static boolean deleteDriver(String nic) {
        try {
            return CrudUtil.execute("DELETE FROM driver WHERE nic=?", nic);
        }catch (SQLException e){
            System.out.println("Driver Delete Issue..");
        }
        return false;
    }

    public static boolean searchNic(String nic) {
        try {
            ResultSet rst= CrudUtil.execute("SELECT * FROM driver WHERE nic=?", nic);
            if (rst.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Search Driver Nic Issue..");
        }
        return false;
    }

    public static boolean updateDriver(DriverDto driverDto) {
        try {
            return CrudUtil.execute("UPDATE driver SET name=? , address=?, contact=?, email=?, dob=? , liense_number=?,license_exp=?, license_type=?, medical=?, status=? WHERE nic=?",
                    driverDto.getName(), driverDto.getAddress(), driverDto.getContact(), driverDto.getEmail()
            , driverDto.getDob(), driverDto.getLicense_number(), driverDto.getLicense_exp(), driverDto.getLicense_type()
            , driverDto.getMedical(), driverDto.getStatus(), driverDto.getNic());
        }catch (SQLException e){
            System.out.println("Driver Upate Issue..");
        }
        return false;
    }

    public static ArrayList<DriverDto> getAllDrivers() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM driver");
            ArrayList<DriverDto> drivers = new ArrayList<>();
            while (rst.next()) {
                DriverDto driverDto=new DriverDto(rst.getInt(1),rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),rst.getString(6)
                        , rst.getString(7), rst.getString(8), rst.getString(9), rst.getString(10), rst.getString(11), rst.getString(12));
                drivers.add(driverDto);
            }
            return drivers;
        } catch (SQLException e){
            System.out.println("Get All Drivers Issue..");
        }
        return null;
    }

    public static DriverDto getAllDrivers(String licenseNumber) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM driver WHERE license_number=?", licenseNumber);
            if (rst.next()) {
                return new DriverDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
                        rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9), rst.getString(10), rst.getString(11), rst.getString(12));
            }
        } catch (SQLException e) {
            System.out.println("Get All Drivers From license Issue..");
        }
        return null;
    }

    public static ArrayList<DriverDto> searchDriver(String text) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM driver WHERE id LIKE '%" + text + "%' OR name LIKE '%" + text + "%' OR address LIKE '%" + text + "%' OR " +
                    "contact LIKE '%" + text + "%' OR email LIKE '%" + text + "%' OR dob LIKE '%" + text + "%' OR nic LIKE '%" + text + "%' OR license_number LIKE '%" + text + "%' OR license_exp LIKE '%" + text + "%' OR license_type LIKE '%" + text
                    + "%' OR medical LIKE '%" + text + "%' OR status LIKE '%" + text + "%'");
            ArrayList<DriverDto> drivers = new ArrayList<>();

            while (rst.next()) {
                drivers.add(new DriverDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
                        rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9), rst.getString(10), rst.getString(11), rst.getString(12)));
            }
            return drivers;
        } catch (SQLException e) {
            System.out.println("Search Driver Issue..");
        }
        return null;
    }

    public static ArrayList<Integer> getAllDriversId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT id FROM driver");
            ArrayList<Integer> objects = new ArrayList<>();
            while (rst.next()) {
                objects.add(rst.getInt(1));
            }
            return objects;
        }catch (SQLException e){
            System.out.println("Get All ID From Drivers Issue..");
        }
        return null;
    }

    public static String getName(int selectedItem) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT name FROM driver WHERE id=?",selectedItem);
            if (resultSet.next()) return resultSet.getString("name");
        }catch (SQLException e){
            System.out.println("Get Driver Name Issue..");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteDrivers(int driverId) {
        try {
            return CrudUtil.execute("DELETE FROM driver WHERE id=?", driverId);
        }catch (SQLException e){
            System.out.println("Driver Delete Issue..");
        }
        return false;
    }
}
