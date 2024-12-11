package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerPaymentTm {
    private int id;
    private String method;
    private String cardNumber;
    private int vehicleId;
    private String rentalPeriod;
    private String locationDistance;
    private int driverId;
}
