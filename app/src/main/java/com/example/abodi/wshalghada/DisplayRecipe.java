package com.example.abodi.wshalghada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DisplayRecipe extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

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
        Statement stmt;
        String sql,sql1;
        List<String> IngredientList = null;
        try {
            Class.forName(DBConnection.urlstring);

            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);

            stmt = con.createStatement();
            /////////////////RecipeID
            sql = "SELECT * FROM Recipe  WHERE RecipeID =" + Login.getUserID(getApplicationContext());
            ResultSet resultSet = stmt.executeQuery(sql);
            /////////////////RecipeID
            sql1 = "SELECT * FROM Ingredient  WHERE RecipeID =" + Login.getUserID(getApplicationContext());
            ResultSet resultIngredients = stmt.executeQuery(sql1);

            while (resultSet.next()) {
                recipeName.setText(resultSet.getString("RecipeName"));
                userName.setText(resultSet.getString("Username"));
                dateON.setText(resultSet.getString("Date"));
                image.setImageURI(Uri.parse(resultSet.getString("Photo")));
                steps.setText(resultSet.getString("Instruction"));
                serving.setText(resultSet.getString("NumOfServing"));
                time.setText(resultSet.getString("CookingTime"));
            }
            while (resultIngredients.next()) {
                IngredientList.add(resultIngredients.getString("IngredientName"));
            }
            for(int i=0; i<IngredientList.size();i++){
                ingredients.setText(IngredientList.get(i)+"\n");
            }
            stmt.close();
            con.close();
        }
        catch (SQLException se){
            Toast errorToast = Toast.makeText(DisplayRecipe.this, "يجب أن تكون متصلا ًبالانترنت "+se.getMessage() ,Toast.LENGTH_SHORT);
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
        String sql;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            stmt = con.createStatement();

            if(favorite.getBackground().equals(R.drawable.ic_heart)) {
                /////////////////RecipeID
                sql = "INSERT INTO Favor (Username, RecipeID) VALUES (" + Login.getUserID(getApplicationContext()) + "," + Login.getUserID(getApplicationContext()) + ");";
                favorite.setBackgroundResource(R.drawable.full_heart);
                addToFavorite.setText("الغاء من مفضلتي");
            }
            else{
                /////////////////RecipeID
                sql = "DELETE FROM Favor WHERE Username=" + Login.getUserID(getApplicationContext()) + "AND RecipeID=" + Login.getUserID(getApplicationContext()) + ";";
                favorite.setBackgroundResource(R.drawable.ic_heart);
                addToFavorite.setText("إضافة الى مفضلتي");
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
        favorite.setBackgroundResource(R.drawable.full_heart);
    }
}
