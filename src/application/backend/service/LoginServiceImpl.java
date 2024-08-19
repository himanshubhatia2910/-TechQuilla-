package application.backend.service;

import application.backend.dao.LoginDao;
import application.backend.dao.LoginDaoImpl;
import application.backend.exceptions.UserNotFoundException;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    LoginDao loginDao;
    public LoginServiceImpl() {
        loginDao = new LoginDaoImpl();
    }

    @Override
    public String getUserRole(String id, String password) throws SQLException, UserNotFoundException {
        try{
            return loginDao.getUserRole(id,password);
        }catch(UserNotFoundException e){
            throw new UserNotFoundException();
        }
    }
}
