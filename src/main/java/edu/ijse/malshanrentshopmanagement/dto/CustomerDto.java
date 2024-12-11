package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

import java.io.File;
import java.io.FileInputStream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {
    private int id;
    private String name;
    private String address;
    private String contact;
    private String nic;
    private String dob;
    private String email;
    private int driverId;
    private String licen_number;
    private int vehicleId;
    private String date;
}
