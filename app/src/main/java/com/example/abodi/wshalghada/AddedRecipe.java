package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddedRecipe extends AppCompatActivity {

    private byte[] bytesimage;
    private Blob imageBlob;
    private RecyclerView recyclerView;
    private postAdpater postAdpater;
    private ArrayList<post> postArrayList = new ArrayList<>();
    private Bitmap bitmap;
    private TextView noRecipe;
    private String RecipeName;
    private String RecipeID;

    //SharedPreferences sp =getSharedPreferences("login", Context.MODE_PRIVATE);
    //String userLogin = sp.getString("username",null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_recipes);

        noRecipe = findViewById(R.id.noRecipe);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Connection con;
            Statement stmt;
            String sql;
            int numOfRecipes=0;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
                stmt = con.createStatement();
                sql = "SELECT RecipeID, Photo, RecipeName FROM Recipe WHERE Username='Mona'";
                ResultSet resultSet  = stmt.executeQuery(sql);

                while (resultSet.next()) {
                    numOfRecipes++;

                    imageBlob=resultSet.getBlob("Photo");
                    bytesimage=(imageBlob.getBytes(1,(int)imageBlob.length()));
                    bitmap=BitmapFactory.decodeByteArray(bytesimage,0,bytesimage.length);

                    RecipeName=resultSet.getString("RecipeName");
                    RecipeID=resultSet.getString("RecipeID");

                    postArrayList.add(new post(RecipeID, bitmap ,RecipeName, R.drawable.pencil,R.drawable.trash));
                    postAdpater = new postAdpater(this, postArrayList);
                }

                recyclerView = (RecyclerView) findViewById(R.id.RV_posts);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                recyclerView.setAdapter(postAdpater);

                if (numOfRecipes==0) noRecipe.setText(" لا توجد وصفات مضافة ");

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
