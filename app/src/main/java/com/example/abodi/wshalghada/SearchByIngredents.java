package com.example.abodi.wshalghada;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;




public class SearchByIngredents extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;



    Button btnChoice;
    TextView TVItemSelected;





    public SearchByIngredents() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchByIngredents.
     */
    public static SearchByIngredents newInstance(String param1, String param2) {
        SearchByIngredents fragment = new SearchByIngredents();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_by_ingredents, container, false);
        btnChoice =  view.findViewById(R.id.btnVege);
        TVItemSelected= view.findViewById(R.id.tvVege);
        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // AlertDialog.Builder builder= new AlertDialog.Builder(getActivity().this);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String[] ingredientsArray=new String[]{"بطاطس","جزر","كوسه","باميا"};
            final boolean[] checkedingredientsArray=new boolean[]{false,false,false,false};
            final List<String>  ingredientsList= Arrays.asList(ingredientsArray);
            builder.setTitle("اختر المكونات");
            builder.setMultiChoiceItems(ingredientsArray, checkedingredientsArray, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    checkedingredientsArray [which]= isChecked;
                    //checkedingredientsArray [which]=true;
                    String currentItem =ingredientsList.get(which);
                  //  Toast.makeText(SearchByIngredents.this, currentItem+"" + isChecked, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), currentItem+"" + isChecked, Toast.LENGTH_SHORT).show();
                }
            });

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TVItemSelected.setText("");
                   TVItemSelected.setText("أنت اخترت هذه المكونات:"+"\n");
                   for(int i=0; i<checkedingredientsArray.length; i++){
                       boolean checked=checkedingredientsArray[i];
                       if(checked){

                           TVItemSelected.append(ingredientsList.get(i)+", ");
                           //
                       }
                   }
                }
            });

            builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ايش راح يصير لما يحط كانسل
                }
            });


            AlertDialog dialog= builder.create();
            dialog.show();


            }
        });


        return view;
    }


}
