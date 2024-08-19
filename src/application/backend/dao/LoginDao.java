package application.backend.dao;

import java.sql.SQLException;

public interface LoginDao {

    String getUserRole(String id, String password) throws SQLException;
}
