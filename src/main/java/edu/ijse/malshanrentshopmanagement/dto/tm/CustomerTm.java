package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerTm {
    private int id;
    private String name;
    private String address;
    private String contact;
    private String nic;
    private String dob;
    private String email;
}
