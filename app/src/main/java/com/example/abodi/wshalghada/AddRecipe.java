package com.example.abodi.wshalghada;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class AddRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] Cuisine = {"عربي","إيطالي","هندي","فرنسي","صيني"};
    String ID_Selected="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner CuisineSpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Cuisine);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CuisineSpinner.setAdapter(ada);
        CuisineSpinner.setOnItemSelectedListener(this);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0: ID_Selected="1";
                break;
            case 1: ID_Selected="2";
                break;
            case 2: ID_Selected="3";
                break;
            case 3: ID_Selected="4";
                break;
            case 4: ID_Selected="5";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
