package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfileFragment extends Fragment {
    private TextView UserName;
    private TextView DisplayName;
    private TextView email;

    ResultSet resultSet = null;
    String userNameDB = "root";
    String passwordDB = "";
    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        UserName =  getView().findViewById(R.id.UserNameTV);
        DisplayName =  getView().findViewById(R.id.DisplayNameTV);
        email =  getView().findViewById(R.id.EmailTV);

        DisplayInfo();

//        Button btnFrang= getView().findViewById(R.id.buttonR);
//        btnFrang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                assert getFragmentManager() != null;
//                FragmentTransaction fr= getFragmentManager().beginTransaction();
//                fr.replace(R.id.fragment_container,new AddedRecipe());
//                fr.commitNow();
//            }
//        });



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void DisplayInfo(){

        //setup connection


        String username="safa";

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DBConnection.createConnection(); //establishing connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            con = DriverManager.getConnection("jdbc:mysql://192.168.154.1/wshalghada","root",null);
            Statement statement  = con.createStatement(); //Statement is used to write queries. Read more about it.
            resultSet = statement.executeQuery("select * from user where Username"+username); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
            while(resultSet.next()) // Until next row is present otherwise it return false
            {
                UserName.setText(resultSet.getString("Username"));
                DisplayName.setText(resultSet.getString("DisplayName"));
                email.setText(resultSet.getString("Email"));
                statement.close();
                con.close();
            }
        }

        catch(SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

