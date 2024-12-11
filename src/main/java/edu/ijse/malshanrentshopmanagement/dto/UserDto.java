package edu.ijse.malshanrentshopmanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String id;
    private String password;
    private String showPassword;
}
