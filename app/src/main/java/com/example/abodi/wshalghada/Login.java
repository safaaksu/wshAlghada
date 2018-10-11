package com.example.abodi.wshalghada;

import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import java.sql.*;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.abodi.wshalghada.DBConnection;
public class Login extends AppCompatActivity {

    SharedPreferences sp;
    ResultSet resultSet = null;
    String userNameDB = "root";
    String passwordDB = "";
    static final String username="Username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
         login();

    }
  private void login() {

        Button LoginButton =(Button) findViewById(R.id.enter3);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText UserName = (EditText) findViewById(R.id.username1);
                EditText Password = (EditText) findViewById(R.id.password1);
                String userName = UserName.getText().toString();
                String password = Password.getText().toString();
                if (!(password.equals(null)|| userName.equals(null) )& !(password.equals("") || userName.equals(""))) {

                    try {
                       Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DBConnection.createConnection(); //establishing connection
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        con = DriverManager.getConnection("jdbc:mysql://172.20.10.14/wshalghada", "root", null);
                        Statement statement = con.createStatement(); //Statement is used to write queries. Read more about it.
                        resultSet = statement.executeQuery("select Username,Password from user"); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
                        while (resultSet.next()) // Until next row is present otherwise it return false
                        {
                            userNameDB = resultSet.getString("Username"); //fetch the values present in database
                            passwordDB = resultSet.getString("Password");
                            if (userName.equals(userNameDB) && password.equals(passwordDB)) {
                                sp.edit().putBoolean("logged",true).apply();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else  Toast.makeText(Login.this, "اسم المستخدم او كلمة المرور خطأ",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }

                else   Toast.makeText(Login.this, "يجب تعبئية جميع الحقول المطلوبه",
                        Toast.LENGTH_LONG).show();
            }











        });
    }
}
