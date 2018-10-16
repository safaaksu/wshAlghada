package com.example.abodi.wshalghada;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import java.sql.*;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
public class Login extends AppCompatActivity {

    SharedPreferences sp;
    ResultSet resultSet = null;
    String userNameDB ;
    String passwordDB;
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
        sp = getSharedPreferences("login",Context.MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
        login();

    }
    private void login() {

        Button LoginButton = findViewById(R.id.enter3);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText UserName =  findViewById(R.id.username1);
                EditText Password =  findViewById(R.id.password1);
                String userName = UserName.getText().toString();
                String password = Password.getText().toString();
                if (!(password.equals("") || userName.equals(""))) {

                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                         Class.forName("com.mysql.jdbc.Driver");

                        Connection con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);


                        Statement statement = con.createStatement(); //Statement is used to write queries. Read more about it.
                        resultSet = statement.executeQuery("select Username,Password from user"); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
                        while (resultSet.next()) // Until next row is present otherwise it return false
                        {
                            userNameDB = resultSet.getString("Username"); //fetch the values present in database
                            passwordDB = resultSet.getString("Password");
                            if (userName.equals(userNameDB) && password.equals(passwordDB)) {
                                final SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean("logged",true).apply();
                                editor.putString("username",userNameDB);
                                editor.commit();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else  Toast.makeText(Login.this, "اسم المستخدم او كلمة المرور خطأ",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();} catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }

                else   Toast.makeText(Login.this, "يجب تعبئية جميع الحقول المطلوبه",
                        Toast.LENGTH_LONG).show();
            }











        });
    }
}



