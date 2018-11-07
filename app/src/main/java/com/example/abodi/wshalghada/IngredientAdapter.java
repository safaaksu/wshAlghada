package com.example.abodi.wshalghada;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


// MAIN METHOD TO ADAPTER
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
   // define attribute
    public Context context;
    public ArrayList<Ingredient> Values = new ArrayList<>();
    public int Size =1;
    public ArrayList<String> IngredientIDSelected= new ArrayList<String>();
    public IngredientAdapter(Context context  ) {
        super();
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // define attribute
        public Spinner IngredientCategory;
        public Spinner Ingredient;
        public Spinner Unit;
        public EditText Quantity;
        public TextView IngrediantNum;
        public ArrayList<IngredientCategory> IngredientCategories;
        public ArrayList<String>  Categories ;
        public ArrayList<Ingredient> Ingredients;
        public ArrayList<String> IngredientArray;
        private String ID_Selected = "1";
        private  ArrayAdapter<String> adapter2;
        public ViewHolder(View itemView) {
            super(itemView);
            // read content of spinner
            IngredientCategory = (Spinner) itemView.findViewById(R.id.spinner1);
            Ingredient = (Spinner) itemView.findViewById(R.id.spinner2);
            Unit = (Spinner) itemView.findViewById(R.id.spinner3);
            Quantity = (EditText) itemView.findViewById(R.id.editText);
            IngrediantNum = (TextView) itemView.findViewById(R.id.textView5);

            IngredientCategories = new ArrayList<IngredientCategory>(); // array to store many more than one IngredientCategories
            Categories = new ArrayList<String>(); //array to store many more than one Category
            AddRecipe.CategorySpinner(IngredientCategories, Categories);

            Ingredients = new ArrayList<Ingredient>();
            IngredientArray = new ArrayList<String>();
            AddRecipe.IngredientSpinner(Ingredients, IngredientArray);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Categories);
            IngredientCategory.setAdapter(adapter);

            adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, IngredientArray);
            Ingredient.setAdapter(adapter2);

            String[] UnitArray = {"اختر وحدة القياس...","حبة","ملعقة ص", "ملعقة ك", "كأس", "رشة"};
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, UnitArray);
            Unit.setAdapter(adapter3);
        }


    }///




    @NonNull
    @Override // !!!!!!!!!!!!!!!!!
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override //
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.IngrediantNum.setText(" المكون "+(position+1));
        int pos = holder.IngredientCategory.getSelectedItemPosition();
        int pos2 = holder.Ingredient.getSelectedItemPosition();

        double num = 0.0;

        Values.add(position,new Ingredient( holder.Ingredients.get(pos2).getID(),holder.IngredientCategories.get(pos).getID(), num, (String) holder.Unit.getSelectedItem()));

        holder.IngredientCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        holder.ID_Selected = "";//default value on spinner (قسم المكون)
                        //     ((TextView)holder.IngredientCategory.getSelectedView()).setError("");
                        break;
                    case 1:
                        holder.ID_Selected = "1";
                        break;
                    case 2:
                        holder.ID_Selected = "2";
                        break;
                    case 3:
                        holder.ID_Selected = "3";
                        break;
                    case 4:
                        holder.ID_Selected = "4";
                        break;
                    case 5:
                        holder.ID_Selected = "5";
                        break;
                    case 6:
                        holder.ID_Selected = "6";
                        break;
                    case 7:
                        holder.ID_Selected = "7";
                        break;
                    case 8:
                        holder.ID_Selected = "8";
                        break;
                    case 9:
                        holder.ID_Selected = "9";
                        break;
                    case 10:
                        holder.ID_Selected = "10";
                        break;
                    case 11:
                        holder.ID_Selected = "11";
                        break;
                    case 12:
                        holder.ID_Selected = "12";
                        break;
                    case 13:
                        holder.ID_Selected = "13";
                        break;
                }
                if(holder.ID_Selected!="") {
                    changeSpinnerContent(holder.ID_Selected, holder.Ingredients, holder.IngredientArray, holder.adapter2);
                    Ingredient in = Values.get(position);
                    in.setID(holder.Ingredients.get(holder.Ingredient.getSelectedItemPosition()).getID());
                    in.setCategoryID(holder.ID_Selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.Ingredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Ingredient in = Values.get(position);

                if(pos!=0){
                    in.setID(holder.Ingredients.get(pos).getID());

                }
                else
                    in.setID(null);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.Unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos , long id) {
                Ingredient in = Values.get(position);
                if(pos!=0) {
                    in.setUnit((String) holder.Unit.getSelectedItem());
                }
                else
                    in.setUnit(null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.Quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null && s.toString()!="") {
                    Ingredient in = Values.get(position);
                    in.setNumber(Double.parseDouble(s.toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Size;
    }

    public void AddView(){Size++;}

    private void changeSpinnerContent(String ID_Selected , ArrayList<Ingredient> Ingredients , ArrayList<String> Ingredient, ArrayAdapter<String> adapter2){
        Ingredients.clear();
        Ingredient.clear();
        //SETUP CONNECTION
        Connection con = null;
        Statement stmt2 ;
        try {
            Ingredient.add(0,"اختر المكون...");
            Ingredients.add(new Ingredient());

            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);

            stmt2 = con.createStatement();
            String query= "SELECT * FROM ingredient Where CategoryID="+ID_Selected;
            ResultSet result =stmt2.executeQuery(query);
            while (result.next()){
                Ingredient.add(result.getString("IngredientName"));
                Ingredients.add(new Ingredient(result.getString("IngredientID"),result.getString("IngredientName"),result.getString("CategoryID")));
            }
            stmt2.close();

            con.close();

        } catch (SQLException se) {se.printStackTrace(); }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter2.notifyDataSetChanged();


    }

}