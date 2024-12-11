package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeSalaryTm {
    private int id;
    private String salary;
    private String bankAccount;
    private String tax;
    private String probationPeriod;
    private String date;
}
