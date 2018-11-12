package com.example.abodi.wshalghada;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.NameValuePair;

public class Login extends AppCompatActivity {

    SharedPreferences sp;
    ResultSet resultSet = null;
    String userName ;
String password;
    RequestParams pr;
    static final String username="Username";
String URL=DBConnection.serverSideURL+"Login";
    EditText UserName ;
    EditText Password;
    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getUserID(Context applicationContext) {
        return getSharedPreferences(applicationContext).getString(username, "");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("login",Context.MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
        login();

    }
    private void login() {

        Button LoginButton = findViewById(R.id.enter3);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 UserName =  findViewById(R.id.username1);
                 Password =  findViewById(R.id.password1);
                String userName = UserName.getText().toString();
                String password = Password.getText().toString();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                if (!(password.equals("") || userName.equals(""))) {
                    GetXMLTask task = new GetXMLTask();
                    task.execute( URL);
                }

                else   {Toast.makeText(Login.this, "يجب تعبئية جميع الحقول المطلوبه",
                        Toast.LENGTH_LONG).show();}
            }











        });
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
            userName=UserName.getText().toString();
            String postParameters="username="+UserName.getText().toString()+"&Password="+Password.getText().toString();

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
            if(output.equals("unsuccessful"))
            {  Toast.makeText(Login.this, "اسم المستخدم او كلمة المرور خطأ",
                                 Toast.LENGTH_LONG).show();}
                                 else{

                final SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean("logged",true).apply();
                                editor.putString("username",userName);
                                editor.commit();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);


            }

        }
    }
    }




