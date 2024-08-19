package application.backend.dao;

import application.backend.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao{

    @Override
    public String getUserRole(String id, String password) throws SQLException,UserNotFoundException {
        String query = "SELECT role FROM Users WHERE user_id = ? AND password = ?";
        String role="";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1,id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    role= rs.getString("role"); // Return the role if authentication is successful
                }
            }
            if(role!=null) return role;
            else throw new UserNotFoundException("User not Found in Records.");
        }
    }
}
