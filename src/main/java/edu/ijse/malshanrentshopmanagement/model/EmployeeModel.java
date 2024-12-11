package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static boolean saveEmployee(EmployeeDto employeeDto) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, employeeDto.getId());
            pst.setString(2, employeeDto.getName());
            pst.setString(3, employeeDto.getAddress());
            pst.setString(4, employeeDto.getContact());
            pst.setString(5, employeeDto.getEmail());
            pst.setString(6, employeeDto.getDob());
            pst.setString(7, employeeDto.getJobRole());
            pst.setString(8, employeeDto.getDepartment());
            pst.setString(9, employeeDto.getNic());
            pst.setString(10, employeeDto.getStatus());
            pst.setString(11, employeeDto.getHire());
            pst.setString(12, employeeDto.getPeriod());
            pst.setString(13, employeeDto.getPeriodDate());
            int i = pst.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Employee Save Issue..");
        }
        return false;
    }

    public static String generateId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT id FROM employee ORDER BY id DESC LIMIT 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String format = String.format("%03d", (id + 1));
                return format;
            }
        } catch (SQLException e) {
            System.out.println("Employee Generate Id Issue..");
        }
        return "001";
    }

    public static ArrayList<EmployeeDto> getAllEmployee() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<EmployeeDto> employee = new ArrayList<>();
            while (rst.next()) {
                employee.add(new EmployeeDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
                        rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9),
                        rst.getString(10), rst.getString(11), rst.getString(12), rst.getString(13)));
            }
            return employee;
        } catch (SQLException e) {
            System.out.println("Get All Employee Issue..");
        }
        return null;
    }

    public static boolean deleteEmployee(int id) {
        try {
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                boolean isSave = EliminateEmployeeModel.saveEliminateEmployee(id);
                if (isSave) {
                    boolean isDelete = deleteEmployees(id);
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
                System.out.println("Eliminate Employee TransAction Issue..");
                if (connection != null) {
                    connection.rollback();
                }
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            }
        }catch (SQLException e){
            System.out.println("Transaction Issue..");
        }
        return false;
    }

    private static boolean deleteEmployees(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM employee WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Delete Employee Issue..");
        }
        return false;
    }

    public static ArrayList<EmployeeDto> searchEmployee(String text) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee WHERE id LIKE '%" + text + "%' OR name LIKE '%" + text + "%' OR address LIKE '%" + text + "%' OR " +
                    "contact LIKE '%" + text + "%' OR email LIKE '%" + text + "%' OR dob LIKE '%" + text + "%' OR job_role LIKE '%" + text + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
            while (rst.next()) {
                employeeDtos.add(new EmployeeDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
                        rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9),
                        rst.getString(10), rst.getString(11), rst.getString(12), rst.getString(13)));
            }
            return employeeDtos;
        }catch (SQLException e){
            System.out.println("Search Employee Issue..");
        }
        return null;
    }

    public static ArrayList<Integer> getAllId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT id FROM employee";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<Integer> employee = new ArrayList<>();
            while (rst.next()) {
                employee.add(rst.getInt(1));
            }
            return employee;
        }catch (SQLException e){
            System.out.println("Get All Employee Id Issue..");
        }
        return null;
    }

    public static String getPeriodDate(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT period_date FROM employee WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("period_date");
            }
        }catch (SQLException e){
            System.out.println("Get Employee periodDate from Id Issue..");
        }
        return null;
    }

    public static EmployeeDto getEmployeeValues(String nic) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee WHERE nic=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nic);
            ResultSet rst = preparedStatement.executeQuery();
            if (rst.next()) {
                return new EmployeeDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
                        rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9),
                        rst.getString(10), rst.getString(11), rst.getString(12), rst.getString(13));
            }
        }catch (SQLException e){
            System.out.println("Get Employee From Nic Issue..");
        }
        return null;
    }

    public static boolean saveEmployees(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO employee (name, address, contact, email, dob, job_role, department, nic, status, hire_date, period, period_date) SELECT name, address, contact, email, dob, job_role, department, nic, status, hire_date, period, period_date FROM eliminate_employee WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int i  = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Replace Eliminate Issue..");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean searchEmployees(String nic) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee WHERE nic=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nic);
            ResultSet rst = preparedStatement.executeQuery();
            if (rst.next()) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Search Employee From Nic Issue..");
        }
        return false;
    }

    public static boolean updateEmployee(EmployeeDto employeeDto) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE employee SET name=?, address=?, contact=?, email=?, dob=?, job_role=?, department=?, nic=?, status=?, hire_date=?, period=?, period_date=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeDto.getName());
            preparedStatement.setString(2, employeeDto.getAddress());
            preparedStatement.setString(3, employeeDto.getContact());
            preparedStatement.setString(4, employeeDto.getEmail());
            preparedStatement.setString(5, employeeDto.getDob());
            preparedStatement.setString(6, employeeDto.getJobRole());
            preparedStatement.setString(7, employeeDto.getDepartment());
            preparedStatement.setString(8, employeeDto.getNic());
            preparedStatement.setString(9, employeeDto.getStatus());
            preparedStatement.setString(10, employeeDto.getHire());
            preparedStatement.setString(11, employeeDto.getPeriod());
            preparedStatement.setString(12, employeeDto.getPeriodDate());
            preparedStatement.setInt(13,employeeDto.getId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Employee Update Issue..");
            e.printStackTrace();
        }
        return false;
    }
    public static EmployeeDto getValues(int selectedItem) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, selectedItem);
            ResultSet rst = preparedStatement.executeQuery();
            if (rst.next()) {
                return new EmployeeDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getString(7),
                        rst.getString(8),rst.getString(9),rst.getString(10),rst.getString(11),rst.getString(12),rst.getString(13));
            }
        }catch (SQLException e){
            System.out.println("Get Values Issue From Employee Salary..");
        }
        return null;
    }
}
