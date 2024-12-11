package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.EliminateEmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EliminateEmployeeModel {
    public static ArrayList<EliminateEmployeeDto> getAllEliminates() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM eliminate_employee";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<EliminateEmployeeDto> eliminateEmployeeDtos = new ArrayList<>();
            while (rst.next()) {
                eliminateEmployeeDtos.add(new EliminateEmployeeDto(rst.getInt(1), rst.getString(2), rst.getString(3),
                        rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9),
                        rst.getString(10), rst.getString(11), rst.getString(12), rst.getString(13)));
            }
            return eliminateEmployeeDtos;
        }catch (SQLException e){
            System.out.println("Get All Eliminate Employee Issue..");
        }
        return null;
    }

    public static ArrayList<EliminateEmployeeDto> searchEmployee(String text) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM eliminate_employee WHERE id LIKE '%" + text + "%' OR name LIKE '%" + text + "%' OR address LIKE '%" + text + "%' OR " +
                    "contact LIKE '%" + text + "%' OR email LIKE '%" + text + "%' OR dob LIKE '%" + text + "%' OR job_role LIKE '%" + text + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<EliminateEmployeeDto> employeeDtos = new ArrayList<>();
            while (rst.next()) {
                employeeDtos.add(new EliminateEmployeeDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
                        rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9),
                        rst.getString(10), rst.getString(11), rst.getString(12), rst.getString(13)));
            }
            return employeeDtos;
        }catch (SQLException e){
            System.out.println("Search Eliminate Employee Issue..");
        }
        return null;
    }

    public static boolean saveEliminateEmployee(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO eliminate_employee (name, address, contact, email, dob, job_role, department, nic, status, hire_date, period, period_date) SELECT name, address, contact, email, dob, job_role, department, nic, status, hire_date, period, period_date FROM employee WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Insert Eliminate Employee Issue..");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean replaceEliminateEmployee(int id) {
        try {
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                boolean isSave = EmployeeModel.saveEmployees(id);
                if (isSave) {
                    boolean isDelete = deleteEliminateEmployee(id);
                    if (isDelete) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.out.println("Transaction Eliminate Employee Issue..");
                connection.rollback();
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            }
        }catch (SQLException e){
            System.out.println("Transaction Issue.");
        }
        return false;
    }

    private static boolean deleteEliminateEmployee(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM eliminate_employee WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Delete Eliminate Employee Issue..");
        }
        return false;
    }
}
