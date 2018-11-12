package com.example.abodi.wshalghada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String urlstring = "jdbc:mysql://mysql6001.site4now.net/db_a41b75_zyoonn";
    public static final String username = "a41b75_zyoonn";
    public static final String password = "ksu12345678";
    public static final String serverSideURL = "http://10.6.196.181:8080/severside/";


  public static Connection con;

    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
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
