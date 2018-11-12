package com.example.abodi.wshalghada;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class FavoriteFragment extends Fragment {
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<byte[]> mImageUrls = new ArrayList<>();
    private Blob imageBlob;
    private RecyclerView recyclerView;
    private String URL=DBConnection.serverSideURL+"FavoriteList";
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = v.findViewById(R.id.AllfavoritrRecipes);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));



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
                    java.net.URL url = new URL(urlString);
                    URLConnection connection = url.openConnection();
                    SharedPreferences sp=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);

                    String postParameters="username="+sp.getString("username",null);

                    try {
                        HttpURLConnection httpConnection = (HttpURLConnection) connection;
                        httpConnection.setRequestMethod("POST");
                        httpConnection.setDoOutput(true);
                        httpConnection.setDoInput(true);
                        httpConnection.setFixedLengthStreamingMode(
                                postParameters.getBytes().length);
                        PrintWriter out = new PrintWriter(httpConnection.getOutputStream());
                        out.print(postParameters);
                        out.close();
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
                    if(output.equals("there is no FavoriteRecipes")){
                        Toast.makeText(getActivity(), "لا توجد وصفات في مفضلتك",
                                Toast.LENGTH_LONG).show();}

                    else{
                        RecipeC[] FavoriteRecipes;
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            FavoriteRecipes= (RecipeC[]) objectMapper.readValue(output, RecipeC[].class);
                            if(FavoriteRecipes!=null)

                                for (int j = 0; j <FavoriteRecipes.length ; j++) {
                                    mImageUrls.add(FavoriteRecipes[j].getRimage());
                                    mNames.add(FavoriteRecipes[j].getRname());
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


