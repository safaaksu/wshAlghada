package com.example.abodi.wshalghada; //ok
	import android.app.AlertDialog;
	import android.content.Context;
	import android.content.DialogInterface;
	import android.content.Intent;
	import android.net.ConnectivityManager;
	import android.net.NetworkInfo;
	import android.os.StrictMode;
	import android.provider.Settings;
	import android.support.v7.app.AppCompatActivity;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.EditText;
	import android.widget.Toast;

	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;

import java.lang.String;

    public class Register extends AppCompatActivity {
        public EditText Username = null;
        public EditText DisplayName= null;
        public EditText Email= null;
        public EditText Password = null;
        public EditText Password2 = null;
        public static User user = null;
       // check password
        private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"; //ok


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            //check wifi connection
            isConnected();
            Username = ((EditText)findViewById(R.id.username2));
            DisplayName= ((EditText)findViewById(R.id.displayName));
            Email= ((EditText)findViewById(R.id.email1));
            Password = ((EditText)findViewById(R.id.password2));
            Password2 = ((EditText)findViewById(R.id.surePass));
        }//ok


        public void Register(View view) {
            //Validate inputs
            String usern = Username.getText().toString();
            String userd = DisplayName.getText().toString();
            String usere = Email.getText().toString();
            String userp = Password.getText().toString();
            String users = Password2.getText().toString();

            //Validate Password
           /* public boolean validate( String Password){
                return PASSWORD_PATTERN.matches(Password);
            }
*/
            if (isValid(usern, userd, usere, userp, users)) {

                //database connection
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String hashpass = md5(userp);
                user = new User();
                String username = user.Register(usern,userd,usere,hashpass);
                if (username!=null) {
                    	//SaveLogin.setUsername(getApplicationContext(),Username);
                    	//redirect to home activity (spicalty)
                    	//startActivity(new Intent(HomeFragment.this, Specialties.class));
                    Intent intent = new Intent(Register.this,ProfileFragment.class);
                    startActivity(intent);
                        } else {
                        //error message
                    	Toast errorToast = Toast.makeText(Register.this, "البريد الموجود مسجل مسبقاً", Toast.LENGTH_SHORT);
                        errorToast.show();
                    	}


            }
        }


            public boolean isValid(String username , String displayName , String email , String password , String password2 ) {
                //validate all inputs
                	if (username.equals("")) {
                        Username.setError("يجب ملئ الخانة");
                    		return false; }

                    if (displayName.equals("")) {
                        DisplayName.setError("يجب ملئ الخانة");
                    return false; }

                    if (email.equals("")) {
                        Email.setError("يجب ملئ الخانة");
                    return false; }

                     if (password.equals("")) {
                         Password.setError("يجب ملئ الخانة");
                    return false; }

                    if (password2.equals("")) {
                        Password2.setError("يجب ملئ الخانة");
                    return false; }

                if(username.length()==11){
                    Username.setError("يجب ألا يزيد أسم المستخدم عن ١٠ أحرف");
                    return false; }

                if(displayName.length()==30){
                    DisplayName.setError("يجب ألا يزيد الأسم الظاهر عن ٣٠ أحرف");
                    return false; }

                if(email.length()==30){
                    Email.setError("يجب ألا يزيد البريد الإلكتروني عن ٣٠ أحرف");
                    return false; }

                if(password.length()==20){
                    Password.setError("يجب ألا تزيد كلمة المرور عن ٢٠ أحرف");
                    return false; }

                if(password2.length()==20){
                    Password2.setError("يجب ألا تزيد كلمة المرور عن ٢٠ أحرف");
                    return false; }

                if(!password .equals(password2)){
                Password2.setError("كلمة المرور غير مطابقة");
                return false; }
                if(password.length()<8){
                    Password.setError("يجب ان يتكون رمز الدخول من ثمانيه احروف او اكثر");
                    return false;
                }

                	try {
                    	if(email.contains("@")) {
                    	    String Emailvalidation = email.substring(email.indexOf('@'));
                        	String pattren = Emailvalidation.toLowerCase();
                        	switch (Emailvalidation) {
                            	case "@hotmail.com":
                                return true;
                            	case "@gmail.com":
                                	return true;
                            	case "@outlook.com":
                                	return true;
                            	case "@icloud.com":
                                	return true;
                            	case "@yahoo.com":
                                	return true;
                            	default: Email.setError("البريد الإلكتروني غير صحيح");
                                	return false;
                            	}
                        	}
                    	else {
                            Email.setError("البريد الإلكتروني غير صحيح");
                        	return false;
                        	}

                    	}catch (Exception e ){
                        Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                    	}

                	return true;
                	}


        public void isConnected() {
            	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            	NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            	NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            	if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
               	}
               	else {
                	showDialog();
            	}
           	}


        private void showDialog()
        {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage ("أنت غير متصل بالانترنت .. هل تريد الاتصال أم إغلاق التطبيق؟")
        .setCancelable(false)
        	.setPositiveButton("اﻻﺗﺼﺎل", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int id) {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }


        })
        .setNegativeButton("إﻏﻼق", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        		Intent intent = new Intent(Intent.ACTION_MAIN);
        		intent.addCategory(Intent.CATEGORY_HOME);
        		startActivity(intent);
        		}
        		});
        	AlertDialog alert = builder.create();
        	alert.show();
        	}


        public String md5(String s) {
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                for (int i=0; i<messageDigest.length; i++)
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }

        public void reset(View view) {
            Username.setText("");
            DisplayName.setText("");
            Email.setText("");
            Password.setText("");
            Password2.setText("");
        }

    }


