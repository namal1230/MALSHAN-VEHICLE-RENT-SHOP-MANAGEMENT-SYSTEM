package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerPaymentDto {
    private int customerId;
    private String method;
    private String cardNumber;
    private int vehicleId;
    private String rentalPeriod;
    private String locationDistance;
    private int driverId;
}
