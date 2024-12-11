package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.CustomerDto;
import edu.ijse.malshanrentshopmanagement.util.CrudUtil;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean saveCustomer(CustomerDto customerDto) {
        try {
            return CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    customerDto.getId(), customerDto.getName(), customerDto.getAddress(),
                    customerDto.getContact(), customerDto.getNic(), customerDto.getDob(),
                    customerDto.getEmail(), customerDto.getDriverId(), customerDto.getLicen_number(), customerDto.getVehicleId(),customerDto.getDate());
        }catch (SQLException e){
            System.out.println("Save Customer Issue..");
            e.printStackTrace();
        }
        return false;
    }

    public static String generateId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String format = String.format("%03d", (id+1));
                return format;
            }
        }catch(SQLException e){
            System.out.println("Generate Customer id Issue..");
        }
        return "001";
    }

    public static ArrayList<CustomerDto> getAllCustomer() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM customer");
            ArrayList<CustomerDto> objects = new ArrayList<>();
            while (rst.next()) {
                objects.add(new CustomerDto(rst.getInt(1), rst.getString(2),
                        rst.getString(3), rst.getString(4), rst.getString(5),
                        rst.getString(6), rst.getString(7), rst.getInt(8),
                        rst.getString(9), rst.getInt(10),rst.getString(11)));
            }
            return objects;
        } catch (SQLException e) {
            System.out.println("Get All Customers Issue..");
        }
        return null;
    }

    public static ArrayList<CustomerDto> searchCustomer(String text) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM customer WHERE id LIKE '%" + text + "%' OR name LIKE '%" + text + "%' OR address LIKE '%" + text + "%' OR " +
                    "contact LIKE '%" + text + "%' OR nic LIKE '%" + text + "%' OR dob LIKE '%" + text + "%' OR email LIKE '%" + text + "%' OR driverId LIKE '%" + text + "%' OR licen_number LIKE '%" + text + "%' OR vehicleid LIKE '%" + text
                    + "%'");
            ArrayList<CustomerDto> customer = new ArrayList<>();
            while (rst.next()) {
                customer.add(new CustomerDto(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),
                    rst.getString(5),rst.getString(6),rst.getString(7),rst.getInt(8),rst.getString(9),rst.getInt(10),rst.getString(11)));
            }
            return customer;
        }catch (SQLException e){
            System.out.println("Get All Customer Issue..");
        }
        return null;
    }

    public static boolean searchCustomerFromId(String id) {
        try {
            ResultSet rst=CrudUtil.execute("SELECT * FROM customer WHERE id=?",id);
            if (rst.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Get All Customer From Id Issue..");
        }
        return false;
    }

    public static boolean updateCustomer(CustomerDto customerDto) {
        try {
            return CrudUtil.execute("UPDATE customer SET name=?,address=?,contact=?,nic=?,dob=?,email=?,driverId=?,licen_number=?,vehicleid=?,date=? WHERE id=?",
            customerDto.getName(),customerDto.getAddress(),customerDto.getContact(),customerDto.getNic(),
                    customerDto.getDob(), customerDto.getEmail(),customerDto.getDriverId(),customerDto.getLicen_number(),
                    customerDto.getVehicleId(),customerDto.getDate(),customerDto.getId());
        }catch (SQLException e){
            System.out.println("Update Customer Issue..");
        }
        return false;
    }

    public static boolean deleteCustomer(int id) {
        try {
            return CrudUtil.execute("DELETE FROM customer WHERE id=?",id);
        }catch (SQLException e){
            System.out.println("DELETE Customer Issue..");
        }
        return false;
    }

    public static CustomerDto getAllCustomerNic(String nic) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM customer WHERE nic=?",nic);
            if (rst.next()) {
                return new CustomerDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
                        rst.getString(5), rst.getString(6), rst.getString(7), rst.getInt(8), rst.getString(9), rst.getInt(10),rst.getString(11));
            }
        }catch (SQLException e){
            System.out.println("Get Customer From nic Issue..");
        }
        return null;
    }

    public static String getCustomerFromId(int id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT name FROM customer WHERE id=?",id);
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        }catch (SQLException e){
            System.out.println("Get Customer NAme from Id Issue..");
        }
        return null;
    }
}