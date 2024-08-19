package application.backend.service;

import application.backend.exceptions.UserNotFoundException;

import java.sql.SQLException;

public interface LoginService {
    String getUserRole(String id, String password) throws SQLException, UserNotFoundException;
}
