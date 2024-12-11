package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MaintenanceDto {
    private int id;
    private String vehicle_chassis;
    private String maintenance_type;
    private String last_maintenance;
    private String next_maintenance;
    private double repairCost;
}
