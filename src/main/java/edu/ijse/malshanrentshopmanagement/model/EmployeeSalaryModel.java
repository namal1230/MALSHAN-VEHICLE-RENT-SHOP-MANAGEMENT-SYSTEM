package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.EmployeeSalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeSalaryModel {
    public static boolean saveEmployee(EmployeeSalaryDto employeeSalaryDto) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO employee_salary VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, employeeSalaryDto.getId());
            pst.setString(2, employeeSalaryDto.getSalary());
            pst.setString(3, employeeSalaryDto.getBank_account());
            pst.setString(4, employeeSalaryDto.getTax());
            pst.setString(5, employeeSalaryDto.getProbation_period());
            pst.setString(6, employeeSalaryDto.getDate());
            int i = pst.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Save Employee Issue..");
        }
        return false;
    }

    public static ArrayList<EmployeeSalaryDto> getAllEmployeeSalary() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee_salary";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<EmployeeSalaryDto> employeeSalaryDtos = new ArrayList<>();
            while (rst.next()) {
                employeeSalaryDtos.add(new EmployeeSalaryDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6)));
            }
            return employeeSalaryDtos;
        }catch (SQLException e){
            System.out.println("Get All Employee Salary Issue..");
        }
        return null;
    }

    public static boolean deleteEmployeeSalary(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM employee_salary WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,  id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Delete Employee Salary Issue..");
        }
        return false;
    }

    public static boolean searchEmployeeId(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee_salary WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Search Employee Id Issue..");
        }
        return false;
    }

    public static boolean updateEmployeeSalary(EmployeeSalaryDto employeeSalaryDto) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "UPDATE FROM employee_salary SET salary=?, bank_account=?, tax=?, probation_period=?, date=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeSalaryDto.getSalary());
            preparedStatement.setString(2, employeeSalaryDto.getBank_account());
            preparedStatement.setString(3, employeeSalaryDto.getTax());
            preparedStatement.setString(4, employeeSalaryDto.getProbation_period());
            preparedStatement.setString(5, employeeSalaryDto.getDate());
            preparedStatement.setInt(6, employeeSalaryDto.getId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Employee Update Issue..");
        }
        return false;
    }

    public static ArrayList<EmployeeSalaryDto> searchEmployeeSalary(String text) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employee_salary WHERE id LIKE '%" + text + "%' OR salary LIKE '%" + text + "%' OR bank_account LIKE '%" + text + "%' OR " +
                    "tax LIKE '%" + text + "%' OR probation_period LIKE '%" + text + "%' OR date LIKE '%" + text + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rst = preparedStatement.executeQuery();
            ArrayList<EmployeeSalaryDto> employeeSalaryDtos = new ArrayList<>();
            while (rst.next()) {
                employeeSalaryDtos.add(new EmployeeSalaryDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6)));
            }
            return employeeSalaryDtos;
        }catch (SQLException e){
            System.out.println("Search Employee Salary Issue..");
        }
        return null;
    }
}
