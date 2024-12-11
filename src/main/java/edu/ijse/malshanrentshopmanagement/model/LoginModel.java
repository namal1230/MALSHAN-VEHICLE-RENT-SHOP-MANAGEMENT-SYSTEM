package edu.ijse.malshanrentshopmanagement.model;

import edu.ijse.malshanrentshopmanagement.db.DBConnection;
import edu.ijse.malshanrentshopmanagement.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public boolean checkCredentials(UserDto userDto) {
        try {
            Connection conn= DBConnection.getInstance().getConnection();
            String sql="SELECT * FROM user WHERE id=? AND password=? OR password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userDto.getId());
            statement.setString(2,userDto.getPassword());
            statement.setString(3,userDto.getShowPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
