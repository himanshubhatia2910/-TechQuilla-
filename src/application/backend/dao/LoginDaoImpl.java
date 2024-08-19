package application.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao{

    @Override
    public String getUserRole(String id, String password) throws SQLException {
        String query = "SELECT role FROM Users WHERE user_id = ? AND password = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1,id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role"); // Return the role if authentication is successful
                }
                return null; // Return null if no user is found
            }
        }
    }
}
