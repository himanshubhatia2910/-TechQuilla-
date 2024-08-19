package application.backend.service;

import java.sql.SQLException;

public interface LoginService {
    String getUserRole(String id, String password) throws SQLException;
}
