package application.backend.dao;

import application.backend.exceptions.UserNotFoundException;

import java.sql.SQLException;

public interface LoginDao {

    String getUserRole(String id, String password) throws SQLException, UserNotFoundException;
}
