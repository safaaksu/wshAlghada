package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SearchByIngredents extends Fragment {
    ResultSet resultSet = null;

    private ArrayList<String> IngCategory = new ArrayList<>();
    private ArrayList<String[]> Ingredents = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView TVItemSelected;
    private SearchResult searchresult=new SearchResult();
    private Button goButton;
    private String URL=DBConnection.serverSideURL+"getcategoriesIng";
    public SearchByIngredents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search_by_ingredents, container, false);
        TVItemSelected=v.findViewById(R.id.tvVege);
        goButton=v.findViewById(R.id.button3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView = v.findViewById(R.id.Allcategories);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        goButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Bundle args = new Bundle();
                                            args.putString("ItemSelected",TVItemSelected.toString());
                                            searchresult.setArguments(args);
                                            FragmentTransaction fr= getFragmentManager().beginTransaction();

                                            fr.replace(R.id.fragment_container,searchresult).commit();
                                        }
                                    }

        );


       GetXMLTask task = new GetXMLTask();
        task.execute(URL);
        return v;

    }

    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected String doInBackground(String... urls) {
            String output = null;

            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
//            String postParameters="username="+UserName.getText().toString()+"&Password="+Password.getText().toString();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);
//                httpConnection.setFixedLengthStreamingMode(
//                        postParameters.getBytes().length);
//                PrintWriter out = new PrintWriter(httpConnection.getOutputStream());
//                out.print(postParameters);
//                out.close();
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String output) {
            if(output.equals("there is no Ingredents")){Toast.makeText(getActivity(), "يوجد خطأ",
                    Toast.LENGTH_LONG).show();}

            else{
                Ingredent[] categIng;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    categIng= (Ingredent[]) objectMapper.readValue(output, Ingredent[].class);
                    if(categIng!=null)

                        for (int j = 0; j <categIng.length ; j++) {
                            IngCategory.add(categIng[j].getCategory());
                            Ingredents.add(categIng[j].getIngredents());
                        }
RecyclerViewAdapterSearch adapter = new RecyclerViewAdapterSearch(getActivity(),TVItemSelected, IngCategory, Ingredents);
        recyclerView.setAdapter(adapter);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }





}

