package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DriverTm {
    private String name;
    private String address;
    private String contact;
    private String licenseNumber;
    private String licenseExpiry;
    private String licenseType;
    private String medical;
    private String status;
}
