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
        String sql,sql1, sql2;
        List<String> IngredientList = null;
        ResultSet resultSet, resultIngredients, resultSet2;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            stmt = con.createStatement();

            /////////////////RecipeID
            sql = "SELECT * FROM recipe WHERE RecipeID = '1' ";
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                recipeName.setText(resultSet.getString("RecipeName"));
                userName.setText(resultSet.getString("Username"));
                dateON.setText(resultSet.getString("Date"));
                image.setImageURI(Uri.parse(resultSet.getString("Photo")));
                steps.setText(resultSet.getString("Instruction"));
                serving.setText(resultSet.getString("NumOfServing"));
                time.setText(resultSet.getString("CookingTime"));
            }

            /////////////////IngredientID
            sql1 = "SELECT * FROM Ingredient WHERE IngredientID = '1' ";
            resultIngredients = stmt.executeQuery(sql1);

            while (resultIngredients.next()) {
                IngredientList.add(resultIngredients.getString("IngredientName"));
            }
            for(int i=0; i<IngredientList.size();i++){
                ingredients.setText(IngredientList.get(i)+"\n");
            }

            /////////////////RecipeID AND Username
            sql2 = "SELECT * FROM favor WHERE Username='safa' AND RecipeID= '1' ";
            resultSet2 = stmt.executeQuery(sql2);
            if(resultSet2 != null){
                favorite.setBackgroundResource(R.drawable.full_heart);
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
                sql = "INSERT INTO Favor (Username, RecipeID) VALUES ( 'safa', '1' )";
                favorite.setBackgroundResource(R.drawable.full_heart);
                addToFavorite.setText("@string/deletefromf");
                /////////////
                sql = "UPDATE recipe SET NumOfF=NumOfF+1 WHERE RecipeID = '1' ";
            }
            else{
                /////////////////RecipeID
                sql = "DELETE FROM Favor WHERE Username='safa' AND RecipeID= '1' ";
                favorite.setBackgroundResource(R.drawable.ic_heart);
                addToFavorite.setText("@string/Add_to_fav");
                /////////////
                sql = "UPDATE recipe SET NumOfF=NumOfF-1 WHERE RecipeID = '1' ";
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
