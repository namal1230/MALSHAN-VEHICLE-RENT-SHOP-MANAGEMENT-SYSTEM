package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class InsuranceDto {
    private int id;
    private String insu_provider;
    private String vehicle_chassis;
    private String insu_policy_number;
    private String insu_policy_type;
    private String startDate;
    private String expiryDate;
}
