package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDto {
    private int id;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String dob;
    private String jobRole;
    private String department;
    private String nic;
    private String status;
    private String hire;
    private String period;
    private String periodDate;
}
