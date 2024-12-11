package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.CustomerPaymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerPaymentModel {
    public static boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto) {
        try {
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                boolean isSave = savePayment(customerPaymentDto);
                if (isSave) {
                    boolean isDelete = DriverModel.deleteDrivers(customerPaymentDto.getDriverId());
                    if (isDelete) {
                        boolean isRemoveVehicle = VehicleModel.deleteVehicles(customerPaymentDto.getVehicleId());
                        if (isRemoveVehicle) {
                            connection.commit();
                            return true;
                        } else {
                            connection.rollback();
                        }
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.out.println("Place Customer Payment Issue..");
                if (connection != null) {
                    connection.rollback();
                }
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Transaction Issue..");
        }
        return false;
    }

    private static boolean savePayment(CustomerPaymentDto customerPaymentDto) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO customer_payment VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerPaymentDto.getCustomerId());
            preparedStatement.setString(2, customerPaymentDto.getMethod());
            preparedStatement.setString(3, customerPaymentDto.getCardNumber());
            preparedStatement.setInt(4, customerPaymentDto.getVehicleId());
            preparedStatement.setString(5, customerPaymentDto.getRentalPeriod());
            preparedStatement.setString(6, customerPaymentDto.getLocationDistance());
            preparedStatement.setInt(7, customerPaymentDto.getDriverId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) return true;
        } catch (SQLException e) {
            System.out.println("Customer Payment Save Issue..");
        }
        return false;
    }

    public static ArrayList<Integer> loadCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT id FROM customer";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> customersId = new ArrayList<>();
            while (resultSet.next()) {
                customersId.add(resultSet.getInt("id"));
            }
            return customersId;
        } catch (SQLException e) {
            System.out.println("Get All Customer Id Issue..");
        }
        return null;
    }

    public static ArrayList<CustomerPaymentDto> getAllCustomerPayment() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM customer_payment";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<CustomerPaymentDto> customerPaymentDtos = new ArrayList<>();
            while (rst.next()) {
                customerPaymentDtos.add(new CustomerPaymentDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getString(6), rst.getInt(7)));
            }
            return customerPaymentDtos;
        } catch (SQLException e) {
            System.out.println("Get all customer Payment Issue..");
        }
        return null;
    }

    public static ArrayList<CustomerPaymentDto> searchCustomer(String text) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM customer_payment WHERE customer_id LIKE '%" + text + "%' OR method LIKE '%" + text + "%' OR card_number LIKE '%" + text + "%' OR " +
                    "vehicle_id LIKE '%" + text + "%' OR rental_period LIKE '%" + text + "%' OR location_distance LIKE '%" + text + "%' OR driver_id LIKE '%" + text + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<CustomerPaymentDto> customerPaymentDtos = new ArrayList<>();
            while (rst.next()) {
                customerPaymentDtos.add(new CustomerPaymentDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getString(6),
                        rst.getInt(7)));
            }
            return customerPaymentDtos;
        }catch (SQLException e){
            System.out.println("Searh Customer Payment Issue..");
        }
        return null;
    }
}


