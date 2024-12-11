package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VehicleTm {
    private int id;
    private String brand;
    private String model;
    private String chassie_number;
    private String color;
    private String Date;
    private double price;
    private String register_code;
}
