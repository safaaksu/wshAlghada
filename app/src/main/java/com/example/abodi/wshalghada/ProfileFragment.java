package com.example.abodi.wshalghada;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        //UserName =  v.findViewById(R.id.UserNameTV);
        //DisplayName =  v.findViewById(R.id.DisplayNameTV);
        //email =  v.findViewById(R.id.EmailTV);

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
        return v;
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
            con = DriverManager.getConnection("jdbc:mysql://172.20.10.14/wshalghada","root",null);
            Statement statement  = con.createStatement(); //Statement is used to write queries. Read more about it.
            resultSet = statement.executeQuery("select * from user where Username='"+username+"'"); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
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

