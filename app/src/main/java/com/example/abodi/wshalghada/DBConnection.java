package com.example.abodi.wshalghada;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password =null;
    public static Connection con;
    private static String urlstring="jdbc:mysql://192.168.100.14/wshalghada";

    public static Connection createConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(urlstring, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }
}