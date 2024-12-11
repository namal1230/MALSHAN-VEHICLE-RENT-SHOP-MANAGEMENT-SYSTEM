package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.VehicleDto;
import edu.ijse.malshanrentshopmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleModel {

    public static boolean saveVehicle(VehicleDto vehicleDto) {
        try {
            return CrudUtil.execute("INSERT INTO vehicle VALUES (?,?,?,?,?,?,?,?)"
            , vehicleDto.getId(), vehicleDto.getBrand(), vehicleDto.getModel()
            , vehicleDto.getChassie_number(), vehicleDto.getColor(), vehicleDto.getDate(), vehicleDto.getPrice()
            , vehicleDto.getRegistration_code());
        }catch (SQLException e){
            System.out.println("Vehicle save issue");
        }
        return false;
    }

    public static String getId() {
        try {
            ResultSet rst=CrudUtil.execute("SELECT id FROM vehicle ORDER BY id DESC LIMIT 1");
            if (rst.next()) {
                int id = rst.getInt(1);
                String generateId = String.format("%03d", (id + 1));
                return generateId;
            }
        }catch (SQLException e){
            System.out.println("Generate Id Issue..");
            e.printStackTrace();
        }
        return "001";
    }

    public static VehicleDto searchVehicle(String chassieNumber) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM vehicle WHERE chassie_number LIKE ?",chassieNumber);
            if(rst.next()) {
                System.out.println("In this line step 2");
                return new VehicleDto(rst.getInt(1), rst.getString(2), rst.getString(3),
                        rst.getString(4), rst.getString(5), rst.getString(6), rst.getDouble(7), rst.getString(8));
            }
        }catch (Exception e){
            System.out.println("Vehicle search issue");
        }
        return null;
    }

    public static boolean updateVehicle(VehicleDto vehicleDto) {
        try {
            return CrudUtil.execute("UPDATE vehicle SET brand=?,model=?,color=?,date=?,price=?,registration_code=? WHERE chassie_number=?"
            , vehicleDto.getBrand(), vehicleDto.getModel(), vehicleDto.getColor()
            , vehicleDto.getDate(), vehicleDto.getPrice(), vehicleDto.getRegistration_code(), vehicleDto.getChassie_number());
        }catch (SQLException e){
            System.out.println("Vehicle Update Issue.");
        }
        return false;
    }

    public static ArrayList<VehicleDto> getAllVehicle() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM vehicle");
            ArrayList<VehicleDto> vehicle=new ArrayList<>();
            while (rst.next()){
                vehicle.add(new VehicleDto(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),
                        rst.getString(5),rst.getString(6),rst.getDouble(7),rst.getString(8)));
            }return vehicle;
        }catch (SQLException e){
            System.out.println("Get All Vehicle Issue..");
        }
        return null;
    }

    public static boolean deleteVehicle(VehicleDto vehicleDto) {
        try {
            CrudUtil.execute("DELETE FROM vehicle WHERE id=?", vehicleDto.getId());
        }catch (SQLException e){
            System.out.println("Vehicle Delete issue");
        }
        return false;
    }

    public static ArrayList<VehicleDto> searchVehicleTextField(String text) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM vehicle WHERE id LIKE '%"+text+"%' OR brand LIKE '%"+text+"%' OR model LIKE '%"+text+"%' OR " +
                    "chassie_number LIKE '%"+text+"%' OR color LIKE '%"+text+"%' OR registration_code LIKE '%"+text+"%'");
            ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();
            while (rst.next()) {
                vehicleDtos.add(new VehicleDto(rst.getInt(1), rst.getString(2),
                        rst.getString(3), rst.getString(4), rst.getString(5),
                        rst.getString(6), rst.getDouble(7), rst.getString(8)));
            }
            return vehicleDtos;
        }catch (SQLException e){
            System.out.println("Search Vehicle Issue using text");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Integer> getAllVehicleId() {
        ArrayList<Integer> vehicleId = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM vehicle");
            while (resultSet.next()){
                vehicleId.add(resultSet.getInt(1));
            }
        }catch (SQLException e){
            System.out.println("Get all vehicle id Issue..");
            e.printStackTrace();
        }
        return vehicleId;
    }

    public static String getBrand(Object selectedItem) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT brand FROM vehicle WHERE id=?",selectedItem.toString());
            if(resultSet.next()){
                return resultSet.getString("brand");
            }
        } catch (SQLException e) {
            System.out.println("Get Vehicle Brand Issue...");
        }
        return null;
    }

    public static boolean deleteVehicles(int vehicleId) {
        try {
            return CrudUtil.execute("DELETE FROM vehicle WHERE id=?", vehicleId);
        }catch (SQLException e){
            System.out.println("Vehicle Delete issue");
        }
        return false;
    }

    public static VehicleDto getVehicleValues(String chassies) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM vehicle WHERE chassie_number=?",chassies);
            while (rst.next()) {
                return new VehicleDto(rst.getInt(1), rst.getString(2), rst.getString(3),
                        rst.getString(4), rst.getString(5), rst.getString(6), rst.getDouble(7), rst.getString(8));
            }
        }catch (SQLException e){
            System.out.println("get Vehicle From chassie number Issue..");
        }
        return null;
    }

    public static boolean searchVehicles(String chassieNumber) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM vehicle WHERE chassie_number=?",chassieNumber);
            if (rst.next()) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("get Vehicle From chassie number Issue..");
        }
        return false;
    }
}
