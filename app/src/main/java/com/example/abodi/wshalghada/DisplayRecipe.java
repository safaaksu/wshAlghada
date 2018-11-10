package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayRecipe extends AppCompatActivity {

    private String recipeID;
    private TextView recipeName;
    private TextView userName;
    private TextView dateON;
    private ImageView image;
    private TextView ingredients;
    private TextView steps;
    private TextView serving;
    private TextView time;
    private TextView addToFavorite;
    private Button favorite;
    int fav=0;
    int numofF;
    private Blob blobimage;
    private byte[] bytesimage;
    private Bitmap bitmap;

    //SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
    //String userLogin = sp.getString("username",null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        Bundle bundle = getIntent().getExtras();
        recipeID = bundle.getString("ID");

        recipeName = (TextView) findViewById(R.id.recipeName);
        userName = (TextView) findViewById(R.id.userName);
        dateON = (TextView) findViewById(R.id.dateON);
        image = (ImageView) findViewById(R.id.image);
        ingredients = (TextView) findViewById(R.id.ingredients);
        steps = (TextView) findViewById(R.id.steps);
        serving = (TextView) findViewById(R.id.serving);
        time = (TextView) findViewById(R.id.time);
        addToFavorite = (TextView) findViewById(R.id.addToFavorite);
        favorite = (Button) findViewById(R.id.favorite);
        DisplayInfo();
    }


    public void DisplayInfo(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con;
        Statement stmt, stmt1;
        String sql, sql1, sql2, sql3;
        String IngredientList="";
        ResultSet resultSet, resultSet2;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            stmt = con.createStatement();
            stmt1 = con.createStatement();

            sql = "SELECT * FROM recipe WHERE RecipeID = '"+recipeID+"' ";
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                recipeName.setText(resultSet.getString("RecipeName"));
                userName.setText(resultSet.getString("Username"));
                dateON.setText(resultSet.getString("Date"));

                blobimage= resultSet.getBlob("Photo");
                bytesimage=(blobimage.getBytes(1,(int)blobimage.length()));
                bitmap=BitmapFactory.decodeByteArray(bytesimage,0,bytesimage.length);
                image.setImageBitmap(bitmap);

                steps.setText(resultSet.getString("Instruction"));
                serving.setText(resultSet.getString("NumOfServing"));
                time.setText(resultSet.getString("CookingTime"));
                numofF=resultSet.getInt("NumOfF");
            }

            sql1 = "SELECT * FROM contain WHERE RecipeID = '"+recipeID+"'";
            ResultSet resultContain = stmt.executeQuery(sql1);
            while (resultContain.next()) {
                IngredientList = IngredientList.concat(resultContain.getString("Number")+" ");
                IngredientList = IngredientList.concat(resultContain.getString("Unit")+" ");
                sql2 = "SELECT IngredientName FROM ingredient WHERE IngredientID ='"+resultContain.getString("IngredientID") +"'";
                ResultSet resultIngredients = stmt1.executeQuery(sql2);
                while (resultIngredients.next()) {
                    IngredientList = IngredientList.concat(resultIngredients.getString("IngredientName")+"\n");
                }
            }
            ingredients.setText(IngredientList);

            sql3 = "SELECT * FROM favor WHERE Username='Mona' AND RecipeID= '"+recipeID+"' ";
            resultSet2 = stmt.executeQuery(sql3);
            int isthere=0;
            while (resultSet2.next()) {
                isthere++;
            }
                if (isthere>0) {
                    fav = 1;
                    addToFavorite.setText("إلغاء التفضيل");
                    favorite.setBackgroundResource(R.drawable.full_heart);
                }
                else{
                    fav=0;
                    favorite.setBackgroundResource(R.drawable.ic_heart);
                    addToFavorite.setText("إضافة الى مفضلتي");
                }

            stmt.close();
            stmt1.close();
            con.close();
        }
        catch (SQLException se){
            Toast errorToast = Toast.makeText(DisplayRecipe.this, "يجب أن تكون متصلا ًبالانترنت"+se.getMessage() ,Toast.LENGTH_SHORT);
            errorToast.show();
        }
        catch (Exception e){
            Toast errorToast = Toast.makeText(DisplayRecipe.this, " "+e.getMessage() ,Toast.LENGTH_SHORT);
            errorToast.show();
        }
    }

//////////////////////////back to what???
    public void backButton(View view) {
        Intent intent = new Intent(this, AddedRecipe.class);
        startActivity(intent);
    }


    public void favoriteButton(View view) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con;
        Statement stmt;
        String sql1, sql2;
        int result1, result2;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            stmt = con.createStatement();

            if(fav == 0) {
                sql1 = "INSERT INTO favor (Username, RecipeID) VALUES ('Mona','"+recipeID+"')";
                result1 = stmt.executeUpdate(sql1);
                numofF++;
                if (result1 == 1) {
                    sql2 = "UPDATE recipe SET NumOfF = '"+numofF+"' WHERE RecipeID = '"+recipeID+"' ";
                    result2 = stmt.executeUpdate(sql2);
                    if (result2 == 1) {
                        addToFavorite.setText("إلغاء التفضيل");
                        favorite.setBackgroundResource(R.drawable.full_heart);
                        fav=1;
                    }
                }
           }
            else{
                sql1 = "DELETE FROM favor WHERE Username='Mona' AND RecipeID= '"+recipeID+"' ";
                result1 = stmt.executeUpdate(sql1);
                numofF--;
                if (result1 == 1) {
                    sql2 = "UPDATE recipe SET NumOfF= '"+numofF+"' WHERE RecipeID = '"+recipeID+"' ";
                    result2 = stmt.executeUpdate(sql2);
                    if (result2 == 1) {
                        addToFavorite.setText("إضافة الى مفضلتي");
                        favorite.setBackgroundResource(R.drawable.ic_heart);
                        fav=0;
                    }
                }
            }
            stmt.close();
            con.close();
        }
        catch (SQLException se){
            Toast errorToast = Toast.makeText(DisplayRecipe.this, "يجب أن تكون متصلا ًبالانترنت " ,Toast.LENGTH_SHORT);
            errorToast.show();
        }
        catch (Exception e){
            Toast errorToast = Toast.makeText(DisplayRecipe.this, " "+e.getMessage() ,Toast.LENGTH_SHORT);
            errorToast.show();
        }
    }
}
