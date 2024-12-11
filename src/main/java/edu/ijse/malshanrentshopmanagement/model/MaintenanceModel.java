package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.MaintenanceDto;
import edu.ijse.malshanrentshopmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceModel {
    public static boolean saveMaintenance(MaintenanceDto maintenanceDto) {
        try {
            return CrudUtil.execute("INSERT INTO vehicle_maintenance VALUES(?,?,?,?,?,?)"
            , maintenanceDto.getId(), maintenanceDto.getVehicle_chassis(), maintenanceDto.getMaintenance_type()
            , maintenanceDto.getLast_maintenance(), maintenanceDto.getNext_maintenance(), maintenanceDto.getRepairCost());
        }catch (SQLException e){
            System.out.println("Maintenance Save Issue");
        }
        return false;
    }

    public static int getId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM vehicle_maintenance ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            System.out.println("Get MaintainanceId Issue..");
        }
        return 0;
    }

    public static ArrayList<MaintenanceDto> getAll() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM vehicle_maintenance");
            ArrayList<MaintenanceDto> objects = new ArrayList<>();
            while (rst.next()) {
                objects.add(new MaintenanceDto(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDouble(6)));
            }
            return objects;
        }catch(SQLException e) {
            System.out.println("Get Maintenance Isuue..");
        }
        return null;
    }

    public static boolean searchId(String id) {
        try{
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM vehicle_maintenance WHERE id=?",id);
            if (resultSet.next()) return true;
        }catch (SQLException e){
            System.out.println("Vehicle Maintenance SearchId Issue..");
        }
        return false;
    }

    public static boolean updateMaintenance(MaintenanceDto maintenanceDto) {
        try {
            return CrudUtil.execute("UPDATE vehicle_maintenance SET vehicle_chassis=? , maintenance_type=?, last_maintenance=?, next_maintenance=?, repairCost=? WHERE id=?",
             maintenanceDto.getVehicle_chassis(), maintenanceDto.getMaintenance_type(), maintenanceDto.getLast_maintenance()
            , maintenanceDto.getNext_maintenance(), maintenanceDto.getRepairCost(), maintenanceDto.getId());
        }catch (SQLException e){
            System.out.println("Maintenance Update Issue..");
        }
        return false;
    }

    public static boolean deleteMaintenance(int id) {
        try {
            return CrudUtil.execute("DELETE FROM vehicle_maintenance WHERE id=?", id);
        } catch (SQLException e){
            System.out.println("MAintenance DElete Issue..");
        }
        return false;
    }

    public static ArrayList<MaintenanceDto> searchMaintenance(String searchMaintenance) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM vehicle_maintenance WHERE id LIKE '%" + searchMaintenance + "%' OR vehicle_chassis LIKE '%" + searchMaintenance + "%' OR maintenance_type LIKE '%" + searchMaintenance + "%' OR " +
                    "last_maintenance LIKE '%" + searchMaintenance + "%' OR next_maintenance LIKE '%" + searchMaintenance + "%' OR repairCost LIKE '%" + searchMaintenance + "%'");
            ArrayList<MaintenanceDto> objects = new ArrayList<>();
            while (resultSet.next()){
                objects.add(new MaintenanceDto(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6)));
            }
            return objects;
        }catch(SQLException e){
            System.out.println("Search Maintenance Issue..");
        }
        return null;
    }
}
