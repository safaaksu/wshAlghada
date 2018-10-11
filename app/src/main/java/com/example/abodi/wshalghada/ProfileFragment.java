package com.example.abodi.wshalghada;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
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


    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //setup connection
        Connection con = null;
        Statement stmt = null;
        String username;
        String sql;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(DBConnection.driverName, DBConnection.username, DBConnection.password);
            //username=((Login)getActivity()).getApplicationContext();
            username= "safa";
            stmt = con.createStatement();
            sql = "SELECT * FROM User WHERE Username=" + username;
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                UserName.setText(resultSet.getString("Username"));
                DisplayName.setText(resultSet.getString("DisplayName"));
                email.setText(resultSet.getString("Email"));
            }
            stmt.close();
            con.close();
        } catch (SQLException se) {
            Toast.makeText(getActivity(),"يجب أن تكون متصلا ًبالانترنت!"+ se.getMessage(),Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getActivity()," "+ e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
}

