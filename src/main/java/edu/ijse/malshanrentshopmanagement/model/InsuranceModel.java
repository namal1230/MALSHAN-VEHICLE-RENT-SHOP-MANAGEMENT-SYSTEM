package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.InsuranceDto;
import edu.ijse.malshanrentshopmanagement.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class InsuranceModel {
    public static boolean saveInsurance(InsuranceDto insuranceDto) {
        try {
            return CrudUtil.execute("INSERT INTO insurance VALUES (?,?,?,?,?,?,?)",
                    insuranceDto.getId(), insuranceDto.getInsu_provider(), insuranceDto.getVehicle_chassis()
            , insuranceDto.getInsu_policy_number(), insuranceDto.getInsu_policy_type(), insuranceDto.getStartDate()
            , insuranceDto.getExpiryDate());
        }catch (SQLException e){
            System.out.println("Save Insurance issue..");
        }
        return false;
    }

    public static int getInsuranceId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM insurance ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
        }catch (SQLException e){
            System.out.println("InsuranceGet Id Issue..");
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<InsuranceDto> getAllInsurance() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM insurance");
            ArrayList<InsuranceDto> insuranceDtos = new ArrayList<>();
            while (rst.next()) {
                InsuranceDto insuranceDto = new InsuranceDto(rst.getInt(1), rst.getString(2), rst.getString(3),
                        rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
                insuranceDtos.add(insuranceDto);
            }
            return insuranceDtos;
        }catch (SQLException e){
            System.out.println("Get All Insurance Issue..");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<InsuranceDto> searchInsurance(String searchInsurance) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM insurance WHERE id LIKE '%" + searchInsurance + "%' OR insu_provider LIKE '%" + searchInsurance + "%' OR vehicle_chassis LIKE '%" + searchInsurance + "%' OR " +
                            "insu_policy_number LIKE '%" + searchInsurance + "%' OR insu_policy_type LIKE '%" + searchInsurance + "%' OR startDate LIKE '%" + searchInsurance + "%' OR expiryDate LIKE '%" + searchInsurance + "%'");
            ArrayList<InsuranceDto> insurance = new ArrayList<>();
            while (resultSet.next()) {
                insurance.add(new InsuranceDto(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)));
            }
            return insurance;
        }catch (SQLException e){
            System.out.println("Search Insurance Issue..");
        }
        return null;
    }

    public static boolean deleteInsurance(String id) {
        try {
            return CrudUtil.execute("DELETE FROM insurance WHERE id=?",Integer.parseInt(id));
        }catch (SQLException e){
            System.out.println("Insurance delete issue..");
        }
        return false;
    }

    public static boolean checkId(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM insurance WHERE id=?",Integer.parseInt(id));
            if (resultSet.next()) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Chek id Issue..");
        }
        return false;
    }

    public static boolean updateInsurance(InsuranceDto insuranceDto) {
        try {
            return CrudUtil.execute(" UPDATE insurance SET insu_provider=? , vehicle_chassis=? , insu_policy_number = ? , insu_policy_type=? , startDate = ?, expiryDate=? WHERE id=?;"
            , insuranceDto.getInsu_provider(), insuranceDto.getVehicle_chassis(), insuranceDto.getInsu_policy_number(), insuranceDto.getInsu_policy_type()
                    , insuranceDto.getStartDate(), insuranceDto.getExpiryDate(), insuranceDto.getId());
        }catch (SQLException e){
            System.out.println("Insurance Update Issue..");
        }
        return false;
    }
}
