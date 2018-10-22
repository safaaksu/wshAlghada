package com.example.abodi.wshalghada;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import pub.devrel.easypermissions.EasyPermissions;


import android.widget.AdapterView;



import android.app.Activity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;


import java.io.ByteArrayOutputStream;



public class AddRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] Cuisine = {"عربي","إيطالي","هندي","فرنسي","صيني"};
    String ID_Selected="";
    String[] IDs= new String [6];
    public EditText RcipeName ;
    public EditText Instruction ;
    public EditText CookingTime ;
    public EditText NumOfServing ;
    public static final int GET_FROM_GALLERY=1;
private String imagepath=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        RcipeName = ((EditText)findViewById(R.id.editText2));
        Instruction = ((EditText)findViewById(R.id.editText5));
        CookingTime = ((EditText)findViewById(R.id.editText6));
        NumOfServing = ((EditText)findViewById(R.id.editText7));
        Spinner CuisineSpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Cuisine);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CuisineSpinner.setAdapter(ada);
        CuisineSpinner.setOnItemSelectedListener(this);
    }
    public void chekedcategoryid()
    {
        CheckBox check1 = (CheckBox) findViewById (R.id.checkBox);
        if (check1.isChecked()) {

            IDs[0]= "2"; //add id in
        }

        CheckBox check2 = (CheckBox) findViewById (R.id.checkBox2);
        if (check2.isChecked()) {

            IDs[1]= "3"; //add id in
        }

        CheckBox check3 = (CheckBox) findViewById (R.id.checkBox3);
        if (check3.isChecked()) {

            IDs[2]= "1"; //add id in
        }

        CheckBox check4 = (CheckBox) findViewById (R.id.checkBox4);
        if (check4.isChecked()) {

            IDs[3]= "4"; //add id in
        }

        CheckBox check5 = (CheckBox) findViewById (R.id.checkBox5);
        if (check5.isChecked()) {

            IDs[4]= "5"; //add id in
        }

        CheckBox check6 = (CheckBox) findViewById (R.id.checkBox6);
        if (check6.isChecked()) {

            IDs[5]= "6"; //add id in
        }

    }
    public void add(View view){
        String rcipeName = RcipeName.getText().toString();
        chekedcategoryid();

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

    public void AddPic(View view) {

        String[] galleryPermissions = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            galleryPermissions = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, galleryPermissions)) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_FROM_GALLERY);
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK && data!=null) {
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
        }
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void reset2(View view) {
        RcipeName.setText("");
        Instruction.setText("");
        CookingTime.setText("");
        NumOfServing.setText("");
        //imagepath.setText("");
       // IDs[].setText("");
       // Cuisine.setText("");
    }

}
