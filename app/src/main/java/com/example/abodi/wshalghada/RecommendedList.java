package com.example.abodi.wshalghada;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class RecommendedList extends Fragment {

    ResultSet resultSet = null;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<byte[]> mImageUrls = new ArrayList<>();
    private Blob imageBlob;


    public RecommendedList() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_recommended_list, container, false);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DBConnection.createConnection(); //establishing connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            con = DriverManager.getConnection("jdbc:mysql://172.20.10.14/wshalghada", "root", null);
            Statement statement = con.createStatement(); //Statement is used to write queries. Read more about it.
            resultSet = statement.executeQuery("SELECT RecipeName, Photo FROM recipe ORDER BY NumOfF DESC"); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
            while (resultSet.next()) // Until next row is present otherwise it return false
            {
                imageBlob=resultSet.getBlob("Photo");
                mImageUrls.add(imageBlob.getBytes(1,(int)imageBlob.length()));
                mNames.add(resultSet.getString("RecipeName"));}

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        RecyclerView recyclerView = v.findViewById(R.id.recommendedList);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);

        return v;

    }

}
