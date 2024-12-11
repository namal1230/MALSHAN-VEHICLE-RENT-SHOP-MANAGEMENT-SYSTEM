package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VehicleMaintainTm {
    private int Id;
    private String VehicleChassisNumber;
    private String MaintenanceType;
    private String LastMaintenance;
    private String NextMaintenance;
    private double RepairCost;
}
