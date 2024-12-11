package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class InsuranceTm {
    private int id;
    private String provider;
    private String chassieNumber;
    private String policynumber;
    private String policyType;
    private String startDate;
    private String endDate;

}
