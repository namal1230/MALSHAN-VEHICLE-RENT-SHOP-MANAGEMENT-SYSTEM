package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeSalaryDto {
    private int id;
    private String salary;
    private String bank_account;
    private String tax;
    private String probation_period;
    private String date;
}
