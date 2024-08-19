package application.backend.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    static Connection con = null;

    //Not a good practice. Should be stored in a safer way.
    private static final String URL="jdbc:mysql://database-1.cd6coo8uue28.us-east-2.rds.amazonaws.com:3306/hospital?useSSL=false";
    private static final String USER="admin";
    private static final String PASSWORD="Hello2301";

    public static Connection getConnection() {
        try{
            if(con==null) {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection Done");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return con;
    }

    public static void CloseConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
