package com.example.abodi.wshalghada;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
//// new edit
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.Button;
import java.lang.String;

public class AddRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] Cuisine = {"عربي", "إيطالي", "هندي", "فرنسي", "صيني"};
    String ID_Selected = "";
    String[] IDs = new String[6];
    public EditText RcipeName;
    public EditText Instruction;
    public EditText CookingTime;
    public IngredientAdapter Ingredientadapter;
    public static final int GET_FROM_GALLERY = 1;
    public String imagepath = null;
    public EditText NumOfServing;
    public ArrayList<IngredientCategory> IngredientCategories;
    public ArrayList<String> Categories;
    public ArrayList<Ingredient> Ingredients;
    public ArrayList<String> Ingredient;
    public RecyclerView ingredientView;

    ////// edit
    public ImageView imgV;
    public byte[] FinalBytes;
    public EditText Instruction1;
    public ImageButton AddInstruction;
    public LinearLayout Collection;
    public ArrayList<EditText> AllInstruction;
    public int NumOfInstructions = 1;
    private Object view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        NumOfServing = ((EditText) findViewById(R.id.editText7));
        RcipeName = ((EditText) findViewById(R.id.editText2));
        // Instruction = ((EditText)findViewById(R.id.editText5));
        CookingTime = ((EditText) findViewById(R.id.editText6));
        imgV = ((ImageView) (findViewById(R.id.imageView10)));
        Spinner CuisineSpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Cuisine);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CuisineSpinner.setAdapter(ada);
        CuisineSpinner.setOnItemSelectedListener(this);

        FillSpinners();
        ingredientView = (RecyclerView) findViewById(R.id.RecyclerView1);
        Ingredientadapter = new IngredientAdapter(this);
        ingredientView.setAdapter(Ingredientadapter);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));
        Instruction1 = (EditText) findViewById(R.id.Instruction1);
        AddInstruction = (ImageButton) findViewById(R.id.AddNewInstruction);
        Collection = (LinearLayout) findViewById(R.id.collection);
        AllInstruction = new ArrayList<>();
        AllInstruction.add(Instruction1);


    }

    public void chekedcategoryid() {
        CheckBox check1 = (CheckBox) findViewById(R.id.checkBox);
        if (check1.isChecked()) {

            IDs[0] = "2"; //add id in
        }
        else
            IDs[0] = null;

        CheckBox check2 = (CheckBox) findViewById(R.id.checkBox2);
        if (check2.isChecked()) {

            IDs[1] = "3"; //add id in
        }
        else
            IDs[1] = null;


        CheckBox check3 = (CheckBox) findViewById(R.id.checkBox3);
        if (check3.isChecked()) {

            IDs[2] = "1"; //add id in
        }
        else
            IDs[2] = null;


        CheckBox check4 = (CheckBox) findViewById(R.id.checkBox4);
        if (check4.isChecked()) {

            IDs[3] = "4"; //add id in
        }
        else
            IDs[3] = null;


        CheckBox check5 = (CheckBox) findViewById(R.id.checkBox5);
        if (check5.isChecked()) {

            IDs[4] = "5"; //add id in
        }
        else
            IDs[4] = null;


        CheckBox check6 = (CheckBox) findViewById(R.id.checkBox6);
        if (check6.isChecked()) {

            IDs[5] = "6"; //add id in

        }
        else
            IDs[5] = null;

    }


    public void AddNewInstruction(View view) {
        NumOfInstructions++;
        EditText newInstruction = new EditText(this);
        newInstruction.setHint("الخطوة " + (NumOfInstructions));
        Collection.addView(newInstruction, NumOfInstructions - 1);
        AllInstruction.add(newInstruction);


    }


    public void add(View view) {
        String rcipeName = RcipeName.getText().toString();
        chekedcategoryid();

        if (isValid()) {

            String AllInstructions = ""; //this will be save in DB
            for(int i =0 ; i<AllInstruction.size();i++){
                AllInstructions += (i + 1) + "." +AllInstruction.get(i).getText().toString()+"\n"; //check if AllInstruction.get(i).getText() not null and not “” in Validation method Not here

            }

            //SETUP CONNECTION
            Connection con = null;
            Statement stmt2, statement;
            try {
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                //STEP 3: Open a connection
                con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);

                stmt2 = con.createStatement();
                String today = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", new Date());

                String query = "INSERT INTO Recipe ( RecipeName, Instruction, Photo, CookingTime, NumOfServing, Date, NumOfF, CuisineID, Username ) VALUES ( '" + RcipeName.getText() + "', '" + AllInstructions + "','" + imagepath + "','" + CookingTime.getText() + "','" + NumOfServing.getText() + "','" + today + "' , '0' ,'" + ID_Selected + "','zenah')";
                int rs = stmt2.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                ResultSet keys = stmt2.getGeneratedKeys();
                String RecipeID = "";
                if (rs == 1) {//num of row affected
                    //get Generated id
                    while (keys.next()) {
                        RecipeID = keys.getString(1);
                    }
                    stmt2.close();
                    statement = con.createStatement();
                    for (int i = 0; i < IDs.length; i++) {
                        if (IDs[i] != null) {
                            String query2 = "INSERT INTO RecipeBelongCategory (RecipeID, CategoryID ) VALUES ( '" + RecipeID + "', '" + IDs[i] + "' )";
                            int resultSet = statement.executeUpdate(query2);
                        }
                    }

                    statement.close();

                    Statement statement2 = con.createStatement();
                    for (int j = 0; j < Ingredientadapter.getItemCount(); j++) {
                        Ingredient in = Ingredientadapter.Values.get(j);
                        String query2 = "INSERT INTO Contain (RecipeID, IngredientID, Number, Unit ) VALUES ( '" + RecipeID + "', '" + in.getID() + "' , '" + in.getNumber() + "' , '" + in.getUnit() + "' )";
                        int resultSet = statement2.executeUpdate(query2);
                    }
                    statement2.close();

                    Toast.makeText(this, "تمت إضافة الوصفة بنجاح", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (AddRecipe.this,ProfileFragment.class));

                } else {
                    Toast.makeText(this, "!!!!!", Toast.LENGTH_SHORT).show();
                }


                con.close();

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ID_Selected = "1";
                break;
            case 1:
                ID_Selected = "2";
                break;
            case 2:
                ID_Selected = "3";
                break;
            case 3:
                ID_Selected = "4";
                break;
            case 4:
                ID_Selected = "5";
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
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
        }
        ////// edit
        Bitmap image = BitmapFactory.decodeFile(imagepath);
        FinalBytes = getBytes(image); // this will be save in DB
        Bitmap getIt = getBitmap(FinalBytes);
        imgV.setImageBitmap(getIt);

        ///

    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void AddIngredient(View view) {
        FillSpinners();
        Ingredientadapter.AddView();
        Ingredientadapter.notifyItemInserted(Ingredientadapter.Size - 1);
    }


    public void FillSpinners() {

        IngredientCategories = new ArrayList<IngredientCategory>();
        Categories = new ArrayList<String>();
        CategorySpinner(IngredientCategories, Categories);
        Ingredients = new ArrayList<Ingredient>();
        Ingredient = new ArrayList<String>();
        IngredientSpinner(Ingredients, Ingredient);

    }


    /////////////////
    static void CategorySpinner(ArrayList<IngredientCategory> IngredientCategories, ArrayList<String> Categories) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        IngredientCategories.clear();
        Categories.clear();
        //SETUP CONNECTION
        Connection con = null;
        Statement stmt;
        try {
            Categories.add(0, "اختر قسم المكون...");
            IngredientCategories.add(new IngredientCategory("", ""));

            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            //check if userName not use
            stmt = con.createStatement();
            String q = "SELECT * FROM ingredientcategory ";
            ResultSet resultSet = stmt.executeQuery(q);
            while (resultSet.next()) {
                Categories.add(resultSet.getString("CategoryName"));
                IngredientCategories.add(new IngredientCategory(resultSet.getString("CategoryID"), resultSet.getString("CategoryName")));
            }
            stmt.close();
            con.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static void IngredientSpinner(ArrayList<Ingredient> Ingredients, ArrayList<String> Ingredient) {

        //SETUP CONNECTION
        Connection con = null;
        Statement stmt2;
        try {
            Ingredient.add(0, "اختر المكون...");
            Ingredients.add(new Ingredient());

            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);

            stmt2 = con.createStatement();
            String query = "SELECT * FROM ingredient Where CategoryID='1' ";
            ResultSet result = stmt2.executeQuery(query);
            while (result.next()) {
                Ingredient.add(result.getString("IngredientName"));
                Ingredients.add(new Ingredient(result.getString("IngredientID"), result.getString("IngredientName"), result.getString("CategoryID")));
            }
            stmt2.close();

            con.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
////////////////


    public void reset2(View view) {



        startActivity(new Intent(AddRecipe.this, AddRecipe.class));
        this.finish();
       /* RcipeName.setText("");
        Instruction.setText("");
        CookingTime.setText("");
        NumOfServing.setText("");
        imagepath = null;
        CheckBox check1 = (CheckBox) findViewById(R.id.checkBox);
        check1.setChecked(false);
        CheckBox check2 = (CheckBox) findViewById(R.id.checkBox2);
        check2.setChecked(false);
        CheckBox check3 = (CheckBox) findViewById(R.id.checkBox3);
        check3.setChecked(false);
        CheckBox check4 = (CheckBox) findViewById(R.id.checkBox4);
        check4.setChecked(false);
        CheckBox check5 = (CheckBox) findViewById(R.id.checkBox5);
        check5.setChecked(false);
        CheckBox check6 = (CheckBox) findViewById(R.id.checkBox6);
        check6.setChecked(false); */

    }

    ////// edit
    public Bitmap getBitmap(byte[] Bytes) {

        return BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);
    }


    public boolean isValid() {
        //validate all inputs
        if (RcipeName.getText().length()== 0) {
            RcipeName.setError("يجب ملئ الخانة");
            return false;
        }

        if (CookingTime.getText().length()== 0) {
            CookingTime.setError("يجب ملئ الخانة");
            return false;
        }

        if (NumOfServing.getText().length()== 0) {
            NumOfServing.setError("يجب ملئ الخانة");
            return false;
        }

       /* if (IDs == null) {
            IDs.setError("يجب اختيار نوع الوصفة");
            return false;
        } */


       int flag = 0;
       for(int k = 0 ; k < IDs.length ; k++)
           if(IDs[k] == null)
               flag++;

       if(flag == 6)
       {  Toast.makeText(this, "يجيب اختيار نوع الوصفة", Toast.LENGTH_SHORT).show();
           return false; }



        //String AllInstructions = ""; //this will be save in DB
        for (int i = 0; i < AllInstruction.size(); i++) {
            if (AllInstruction.get(i).getText().length() == 0)//check if AllInstruction.get(i).getText() not null and not “” in Validation method Not here
            {
                AllInstruction.get(i).setError("يجب ملئ الخانة");
                return false;
            }

        }



            for (int j = 0; j < Ingredientadapter.getItemCount(); j++) {
                Ingredient in = Ingredientadapter.Values.get(j);
                //System.out.println("Unit " + in.getUnit());
                //System.out.println("ID " + in.getID());
                if(in.getCategoryID()=="")
                {
                    Toast.makeText(this, "يجب اختيار قسم المكون", Toast.LENGTH_SHORT).show();
                return false; }


                if(in.getID() == null)
                {
                    Toast.makeText(this, "يجب اختيار المكون", Toast.LENGTH_SHORT).show();
                return false; }


                if(in.getUnit() == null) {
                    Toast.makeText(this, "يجب اختيار وحدة القسم", Toast.LENGTH_SHORT).show();
                return false; }

                if(in.getNumber() == 0.0) {
                    Toast.makeText(this, "يجب تحديد كمية لجميع المكونات", Toast.LENGTH_SHORT).show();
                    return false; }



            }


        return true;
    }
}






