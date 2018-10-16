package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    int fav=0;
    int numofF;
    SharedPreferences sp =getSharedPreferences("login", Context.MODE_PRIVATE);
    String userLogin = sp.getString("username",null);

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
        String sql, sql1, sql2, sql3;
        String IngredientList=null;
        ResultSet resultSet, resultIngredients, resultContain, resultSet2;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            stmt = con.createStatement();

            /////////////////RecipeID!!!!!!!!
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
                numofF=resultSet.getInt("NumOfF");
            }

            /*
            /////////////////RecipeID
            sql1 = "SELECT * FROM contain WHERE RecipeID = '1'";
            resultContain = stmt.executeQuery(sql1);

            while (resultContain.next()) {
                IngredientList = IngredientList + (resultContain.getString("Number")+" ");
                IngredientList = IngredientList + (resultContain.getString("Unit")+" ");
                sql2 = "SELECT IngredientName FROM ingredient WHERE IngredientID ="+resultContain.getString("IngredientID");
                resultIngredients = stmt.executeQuery(sql2);
                IngredientList = IngredientList + (resultIngredients.getString("IngredientName")+"\n");
            }
            ingredients.setText(IngredientList);
            */


           /* while (resultIngredients.next()) {
                //ingredients.setText(resultContain.getString("Number")+" ");
                //ingredients.setText(resultContain.getString("Unit")+" ");
                ingredients.setText(resultIngredients.getString("IngredientName")+"\n");
            }*/

            /*for(int i=0; i<IngredientList.size(); i=i+3){
                ingredients.setText(IngredientList.get(i)+" ");
                ingredients.setText(IngredientList.get(i+1)+" ");
                ingredients.setText(IngredientList.get(i+2)+"\n");
            }*/

            /////////////////RecipeID!!!!!!!!
            sql3 = "SELECT * FROM favor WHERE Username='"+userLogin+"' AND RecipeID= '1' ";
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
            con.close();
        }
        catch (SQLException se){
            Toast errorToast = Toast.makeText(DisplayRecipe.this, "يجب أن تكون متصلا ًبالانترنت !!!!!!"+se.getMessage() ,Toast.LENGTH_SHORT);
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
                /////////////////RecipeID!!!!!!!!
                sql1 = "INSERT INTO favor (Username, RecipeID) VALUES ('"+userLogin+"','1')";
                result1 = stmt.executeUpdate(sql1);
                numofF++;
                if (result1 == 1) {
                    /////////////////RecipeID!!!!!!!!
                    sql2 = "UPDATE recipe SET NumOfF = '"+numofF+"' WHERE RecipeID = '1' ";
                    result2 = stmt.executeUpdate(sql2);
                    if (result2 == 1) {
                        addToFavorite.setText("إلغاء التفضيل");
                        favorite.setBackgroundResource(R.drawable.full_heart);
                        fav=1;
                    }
                }
           }
            else{
                /////////////////RecipeID!!!!!!!!
                sql1 = "DELETE FROM favor '"+userLogin+"' AND RecipeID= '1' ";
                result1 = stmt.executeUpdate(sql1);
                numofF--;
                if (result1 == 1) {
                    /////////////////RecipeID!!!!!!!!
                    sql2 = "UPDATE recipe SET NumOfF= '"+numofF+"' WHERE RecipeID = '1' ";
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
