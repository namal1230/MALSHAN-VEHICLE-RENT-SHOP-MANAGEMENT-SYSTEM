package edu.ijse.malshanrentshopmanagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeTm {
    private int id;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String dob;
    private String role;
}
