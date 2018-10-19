package com.example.abodi.wshalghada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    private String Username;
    private String DisplayName;
    private String Email;
    private String Password;

    public User(){}

    public User(String username, String displayName, String email, String password) {
        Username = username;
        DisplayName = displayName;
        Email = email;
        Password = password;

    }

    public String getUsername() {
        return Username;    }

        public void setUsername(String username) {
            Username = username;    }

        public String getDisplayNamel() {
        return DisplayName;    }

        public void setDisplayName(String displayName) {
            DisplayName = displayName;    }

        public String getEmail() {
        return Email;    }

        public void setEmail(String email) {
            Email = email;    }

        public String getPassword() {
        return Password;    }

        public void setPassword(String password) {
            Password = password;    }



        /*public  void Logout(){
        //clear id
              SignIn.user_id="";
               SignIn.user=null;
               SignUp.user=null;    }

               public String Login(String password, String UserEmail){
        try {            //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                //STEP 3: Open a connection
              Connection conn = DriverManager.getConnection(DB_Info.DB_URL, DB_Info.USER, DB_Info.PASS);
              Statement stmt2 = conn.createStatement();
              String sql = "SELECT * FROM user where UserEmail='"+UserEmail+"' AND Password='"+password+"'";
              ResultSet result = stmt2.executeQuery(sql);
              int count = 0;
              String user_id=null;
              while (result.next()) {
                  user_id = result.getString(1);
                  User_ID = user_id;
                  User_Email = UserEmail;
                  Password = password;
                  Gender = result.getString("Gender");
                  UserName = result.getString("UserName");
                  ++count;
              }
              if (count == 1) {
                  result.close();
                  stmt2.close();
                  conn.close();
                  return user_id;
              } else {
                  return null;
              }
        }catch(SQLException se){
            return null;
        }catch(Exception e){
            return null;
        }     } */


        public  String Register(String username, String displayName, String email, String password) {
            //SETUP CONNECTION
            Connection con = null;
            Statement stmt, stmt1;
            try {
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                //STEP 3: Open a connection
                con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
                //check if userName not use
                stmt = con.createStatement();
                String q= "SELECT * FROM User WHERE Username='"+username+"'";
                ResultSet resultSet=stmt.executeQuery(q);
                int isthere=0;
                while (resultSet.next()){
                    isthere++;
                }
                if(isthere>0){
                    stmt.close();
                    con.close();
                    return null;
                }

                else {
                    //STEP 4: Execute a query
                    stmt1 = con.createStatement();
                    String sql, username1 = null;
                    sql = "INSERT INTO User (Username, Password, DisplayName, Email) VALUES('" + username + "','" + password + "','" + displayName + "','" + email + "')";
                    int result = stmt.executeUpdate(sql);
                    //ResultSet keys = stmt1.getGeneratedKeys();
                    if (result == 1) {
                        /*while (keys.next()) {
                            username1 = keys.getString(1);
                        }*/
                        Username = username;
                        DisplayName = displayName;
                        Email = email;
                        Password = password;

                        stmt.close();
                        con.close();
                        return username;
                        //return username1;
                    } else {
                        stmt.close();
                        con.close();
                        return null;
                    }
                }
            } catch (SQLException se) {
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
}