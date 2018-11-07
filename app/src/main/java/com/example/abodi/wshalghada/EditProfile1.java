package com.example.abodi.wshalghada;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfile1 extends AppCompatActivity {

    private TextView UserName;
    private EditText DisplayName;
    private EditText email;
    private EditText Password;
    private EditText Password1;
    //SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
    //String userLogin = sp.getString("username",null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile1);

        UserName = (TextView) findViewById(R.id.username1);
        DisplayName = (EditText) findViewById(R.id.DisplayName);
        email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.Password);
        Password1 = (EditText) findViewById(R.id.Password1);
        DisplayInfo();
    }


    public void DisplayInfo()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con;
        Statement stmt;
        String sql;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            stmt = con.createStatement();
////////////////
            sql = "SELECT * FROM user WHERE Username='Mona'";
            ResultSet resultSet  = stmt.executeQuery(sql);

            while (resultSet.next()) {
                UserName.setText(resultSet.getString("Username"));
                DisplayName.setText(resultSet.getString("DisplayName"));
                email.setText(resultSet.getString("Email"));
                Password.setText(resultSet.getString("Password"));
                Password1.setText(resultSet.getString("Password"));
            }
            stmt.close();
            con.close();
        }
            catch (SQLException se){
                Toast errorToast = Toast.makeText(EditProfile1.this, "يجب أن تكون متصلا ًبالانترنت "+se.getMessage() ,Toast.LENGTH_SHORT);
                errorToast.show();
            }
            catch (Exception e){
                Toast errorToast = Toast.makeText(EditProfile1.this, " "+e.getMessage() ,Toast.LENGTH_SHORT);
                errorToast.show();
            }
    }


    public void EditButton(View view) {
        String displayname = DisplayName.getText().toString();
        String Email = email.getText().toString();
        String pass = Password.getText().toString();
        String pass1 = Password1.getText().toString();
        boolean isValid = ValidateInputs(displayname,Email,pass,pass1);
        if(isValid){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection con;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
                stmt = con.createStatement();
//////////////////////
                 String sql = "UPDATE user SET Password='"+pass+"' , DisplayName='"+displayname+"' , Email='"+Email+"' WHERE Username='Mona";
                int result = stmt.executeUpdate(sql);
                if (result == 1) {
                    Toast done = Toast.makeText(EditProfile1.this, " تم التعديل ", Toast.LENGTH_SHORT);
                    done.show();
                    finish();
                    startActivity(new Intent(EditProfile1.this, ProfileFragment.class));
                } else {
                    Toast done = Toast.makeText(EditProfile1.this, " عذرًاً" + "\n" + "حدث مشكلة أثناء التعديل حاول مجددًاً ", Toast.LENGTH_SHORT);
                    done.show();
                }
                stmt.close();
                con.close();
            }
            catch (SQLException se){
                Toast errorToast = Toast.makeText(EditProfile1.this, "يجب أن تكون متصلا ًبالانترنت! " ,Toast.LENGTH_SHORT);
                errorToast.show();
            }
            catch (Exception e){
                Toast errorToast = Toast.makeText(EditProfile1.this, " "+e.getMessage() ,Toast.LENGTH_SHORT);
                errorToast.show();
            }
        }
    }


    public boolean ValidateInputs(String displaynameV, String emailV, String passwordV, String passwordV1){
        if(displaynameV.equals("")){
            DisplayName.setError("يجب ملء الخانة");
            return false;
        }
        if(emailV.equals("")){
            email.setError("يجب ملء الخانة");
            return false;
        }
        if(passwordV.equals("")){
            Password.setError("يجب ملء الخانة");
            return false;
        }
        if(passwordV1.equals("")){
            Password1.setError("يجب ملء الخانة");
            return false;
        }
        if(passwordV.length()<8){
            Password.setError("يجب ان يتكون رمز الدخول من ثمانية حروف او اكثر");
            return false;
        }
        if(!passwordV.equals(passwordV1)){
            Password1.setError("رمز الدخول غير متطابقين، حاول مجددًا");
            return false;
        }
        try{
            if(emailV.contains("@")){
                String emailValidation = emailV.substring(emailV.indexOf('@'));
                switch (emailValidation){
                    case "@hotmail.com": return true;
                    case "@gmail.com": return true;
                    case "@outlook.com": return true;
                    case "@icloud.com": return true;
                    case "@yahoo.com": return true;
                    default: email.setError("البريدالالكتروني غير صحيح");
                    return false;
                }
            }
            else{
                email.setError("البريدالالكتروني غير صحيح");
                return false;
            }
        }
        catch (Exception e){
            Toast.makeText(EditProfile1.this, " "+e.getMessage() ,Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public void back(View view) {
        Intent intent = new Intent(this, ProfileFragment.class);
        startActivity(intent);
    }


    public void reset(View view) {
        DisplayName.setText("");
        email.setText("");
        Password.setText("");
        Password1.setText("");
    }
}
