package com.example.abodi.wshalghada;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddedRecipe extends AppCompatActivity {

    private TextView noRecipe;
    private byte[] image;

    //private ImageButton photo;
    //private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_recipes);

        noRecipe = (TextView) findViewById(R.id.textView48);
        noRecipe.setVisibility(View.INVISIBLE);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Connection con = null;
            Statement stmt =null;
            String sql;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DBConnection.driverName, DBConnection.username, DBConnection.password);
                stmt = con.createStatement();
                sql = "SELECT RecipeID, Photo FROM Recipe WHERE Username=" + Login.getUserID(getApplicationContext());
                ResultSet resultSet = stmt.executeQuery(sql);

                //SQLiteDatabase DB = this.getWritableDatabase();
               // Cursor cursor = DB.rawQuery("SELECT RecipeID, Photo FROM Recipe WHERE Username=" + Login.getUserID(getApplicationContext()));

                if (resultSet == null){
                    noRecipe.setVisibility(View.VISIBLE);
                    noRecipe.setText("لا توجد وصفات مضافة ");
                }
                else{
                    while (resultSet.next()) {
                        //photo.setImage(resultSet.("Photo"));
                    }
                }

                stmt.close();
                con.close();
            }
            catch (SQLException se){
                Toast errorToast = Toast.makeText(AddedRecipe.this, "يجب أن تكون متصلا ًبالانترنت "+se.getMessage() ,Toast.LENGTH_SHORT);
                errorToast.show();
            }
            catch (Exception e){
                Toast errorToast = Toast.makeText(AddedRecipe.this, " "+e.getMessage() ,Toast.LENGTH_SHORT);
                errorToast.show();
            }
    }

    public void addNewRecipe (View view) {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }


    public void back (View view) {
        Intent intent = new Intent(this, ProfileFragment.class);
        startActivity(intent);
    }

}
