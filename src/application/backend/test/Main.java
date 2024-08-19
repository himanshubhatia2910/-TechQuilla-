package application.backend.test;

import application.backend.dao.DBUtil;
import application.backend.service.LoginService;
import application.backend.service.LoginServiceImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice=0;
        System.out.println("Enter Unique ID:");
        String id = sc.next();
        System.out.println("Enter Password:");
        String password = sc.next();
        LoginService loginService = new LoginServiceImpl();

        String role = null;
        try {
            role = loginService.getUserRole(id, password);
        } catch (SQLException e) {
            System.out.println("User Not Found");
        }
        switch (role){
            case "ADMIN":
                System.out.println("Hello Admin");
                break;
            case "USER":
                System.out.println("Hello User");
                break;
            case "DOCTOR":
                System.out.println("Hello Doctor");
                break;
            default:
                System.out.println("Login Unsuccessful");
        }
    }
}

