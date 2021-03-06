package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class PopularList extends Fragment {
    ResultSet resultSet = null;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<byte[]> mImageUrls = new ArrayList<>();
    private Blob imageBlob;
    private RecyclerView recyclerView;
    private String URL=DBConnection.serverSideURL+"PopularList";
    public PopularList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_popular_list, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
         recyclerView = v.findViewById(R.id.popularList);
        recyclerView.setLayoutManager(layoutManager);
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
            if(output.equals("there is no popularRecipes")){Toast.makeText(getActivity(), "اسم المستخدم او كلمة المرور خطأ",
                    Toast.LENGTH_LONG).show();}

            else{
                RecipeC[] popularRecipes;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    popularRecipes= (RecipeC[]) objectMapper.readValue(output, RecipeC[].class);
                    if(popularRecipes!=null)

                            for (int j = 0; j <popularRecipes.length ; j++) {
                                mImageUrls.add(popularRecipes[j].getRimage());
                                mNames.add(popularRecipes[j].getRname());
                            }
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mNames, mImageUrls);
                    recyclerView.setAdapter(adapter);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }





}
