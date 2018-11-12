package com.example.abodi.wshalghada;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapterSearch extends RecyclerView.Adapter<RecyclerViewAdapterSearch.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> categ ;
    private ArrayList<String[]> ingred;
    private Context mContext;
private TextView TVItemSelected;
    private Set<String> ItemSelected=new HashSet<>();
    public RecyclerViewAdapterSearch(Context context, TextView TV, ArrayList<String> IngCategory, ArrayList<String[]> Ingredents) {
        categ = IngCategory;
        ingred=Ingredents;
        mContext = context;
        TVItemSelected=TV;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        final boolean[] checkedingredientsArray=new boolean[ingred.get(position).length];
//      Glide.with(mContext)
//             .asBitmap().load(mImageUrls.get(position))
//              .into(holder.image);
        for(int i=0;i<checkedingredientsArray.length;i++)
            checkedingredientsArray[i]=false;
        holder.catogB.setText(categ.get(position));
        holder.catogB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + categ.get(position));

                Toast.makeText(mContext, categ.get(position), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
               // String[] ingredientsArray=new String[]{"بطاطس","جزر","كوسه","باميا"};

                final List<String> ingredientsList= Arrays.asList(ingred.get(position));
                builder.setTitle("اختر المكونات");
                builder.setMultiChoiceItems(ingred.get(position), checkedingredientsArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        checkedingredientsArray [which]= isChecked;

             String currentItem =ingredientsList.get(which);

                        //  Toast.makeText(SearchByIngredents.this, currentItem+"" + isChecked, Toast.LENGTH_SHORT).show();
                        Toast.makeText(mContext, currentItem+"" + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        for(int i=0; i<checkedingredientsArray.length; i++){
                            boolean checked=checkedingredientsArray[i];
                            if(checked)
                                ItemSelected.add(ingredientsList.get(i));
                                        else
                                ItemSelected.remove(ingredientsList.get(i));

                                //

                        }

                        if(!ItemSelected.isEmpty()){
                            int index=1;
                            TVItemSelected.setText("المكونات المختاره:"+"\n");
                            for (String i : ItemSelected){
                        TVItemSelected.append(index+". "+i+"\n");
                                index++;
                            }
                        }
                        else  TVItemSelected.setText("");
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ايش راح يصير لما يحط كانسل
                       // checkedingredientsArray [which]= false ;

                        for(int i=0; i<checkedingredientsArray.length; i++){
                            boolean checked=checkedingredientsArray[i];
                            if(ItemSelected.contains(ingredientsList.get(i)))
                                checkedingredientsArray[i]=true;
                            else
                                checkedingredientsArray[i]=false;

                            //

                        }
                    }
                });


                AlertDialog dialog= builder.create();
                dialog.show();


            }
        });
    }






    @Override
    public int getItemCount() {
        return categ.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button catogB;


        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            catogB = itemView.findViewById(R.id.catobutton);

            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
