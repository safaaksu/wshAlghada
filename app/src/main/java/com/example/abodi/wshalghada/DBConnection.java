package com.example.abodi.wshalghada;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
        public static Connection createConnection()
        {
            Connection con = null;
            String url = "jdbc:mysql://192.168.100.14/wshalghada"; //MySQL URL and followed by the database name
            String username = "root"; //MySQL username
            String password = null; //MySQL password

            try
            {
                try
                {
                    Class.forName("com.mysql.jdbc.Driver"); //loading mysql driver
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                con = DriverManager.getConnection(url,username,password); //attempting to connect to MySQL database
                System.out.println("Printing connection object "+con);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return con;
        }
    }

