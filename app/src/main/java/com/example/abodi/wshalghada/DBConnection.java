package com.example.abodi.wshalghada;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String driverName = "com.mysql.jdbc.Driver";
    public static final String username = "root";
    public static final String password =null;

    public static Connection con;
    private static String urlstring="jdbc:mysql://172.20.10.14/wshalghada";

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