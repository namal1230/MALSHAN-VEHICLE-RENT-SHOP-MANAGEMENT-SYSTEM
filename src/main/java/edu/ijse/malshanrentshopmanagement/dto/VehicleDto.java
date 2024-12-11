package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VehicleDto {
    private int id;
    private String brand;
    private String model;
    private String chassie_number;
    private String color;
    private String date;
    private double price;
    private String registration_code;
}
