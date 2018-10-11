package com.example.abodi.wshalghada;


public class DBConnection {


    public static final String urlstring = "jdbc:mysql://mysql6001.site4now.net/db_a41b75_zyoonn";
    public static final String username = "a41b75_zyoonn";
    public static final String password ="ksu12345678";

   /* public static Connection con;
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
   */