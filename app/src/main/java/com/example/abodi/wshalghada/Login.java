package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Login extends AppCompatActivity {

    ResultSet resultSet = null;
    String userNameDB = "root";
    String passwordDB = "";
    static final String username="Username";
    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getUserID(Context applicationContext) {
        return getSharedPreferences(applicationContext).getString(username, "");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         login();

    }

    private void login() {

        Button LoginButton =(Button) findViewById(R.id.enter3);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText UserName=(EditText) findViewById(R.id.username1);
                EditText Password=(EditText) findViewById(R.id.password1);
                String userName= UserName.getText().toString();
                String password= Password.getText().toString();

                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DBConnection.createConnection(); //establishing connection
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    con = DriverManager.getConnection("jdbc:mysql://192.168.100.14/wshalghada","root",null);
                    Statement statement  = con.createStatement(); //Statement is used to write queries. Read more about it.
                    resultSet = statement.executeQuery("select Username,Password from user"); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
                    while(resultSet.next()) // Until next row is present otherwise it return false
                    {
                        userNameDB = resultSet.getString("Username"); //fetch the values present in database
                        passwordDB = resultSet.getString("Password");
                        if(userName.equals(userNameDB) && password.equals(passwordDB))
                        {

                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }}

                catch(SQLException e)
                {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        });
    }
}
