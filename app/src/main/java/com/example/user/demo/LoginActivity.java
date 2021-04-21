package com.example.user.demo;


import android.content.Intent;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    int loginCheck;
    GPSTracker gps;
    Button login, register;
    EditText id, pass;
    TextView message;
    String mac;
    HashMap<String,String> remap;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login  =  (Button) findViewById(R.id.login);
        register  = (Button) findViewById(R.id.register);
        id = (EditText) findViewById(R.id.staff_id);
        pass = (EditText)findViewById(R.id.password);
        message = (TextView) findViewById(R.id.message);

       String json = new SQLHelper(this).getAllOfflineRecords();
       Log.d(TAG,"This is a log ........"+ json);
        logLargeString(json);
        //new SQLHelper(this).update_all_property_status();
       // new SQLHelper(LoginActivity.this).deleteProperty();


        new ImageSQLHelper(LoginActivity.this).Retrive();
        loginCheck = new ImageSQLHelper(this).getCounts();
        if(loginCheck == 0){
            message.setText("New User Please Sign Up");
            login.setVisibility(View.INVISIBLE);
        }else {
            message.setText("Enter Login Details");
            register.setVisibility(View.INVISIBLE);
        }

        mac = new InternetConnectionClass(getApplicationContext()).getMacAddr();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(LoginActivity.this);
                // Check if GPS enabled
                if(gps.canGetLocation()) {
                    boolean status = new ImageSQLHelper(LoginActivity.this).insertUser(id.getText().toString(), pass.getText().toString(), mac);
                    if (status == true) {
                        //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        message.setText("Registration Failed");

                    }
                }else{
                    gps.showSettingsAlert();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String col = new SQLHelper(LoginActivity.this).getDataFromAnyColumn(19);
                //Log.d(LoginActivity.class.getSimpleName(), "Value is " +col);
                gps = new GPSTracker(LoginActivity.this);
                // Check if GPS enabled
                if(gps.canGetLocation()) {
                    int status = new ImageSQLHelper(LoginActivity.this).loginUser(id.getText().toString(), pass.getText().toString(),mac);
                    if (status > 0){
                        // Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        message.setText("Login Failed (Invalid User Details)");
                    }

                }else{
                    gps.showSettingsAlert();
                }
            }
        });
    }
    private void writeToSDFile(){
        String string = "What Nonesanse";
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("Quote.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "EKIRSLUC");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "ABS"+ timeStamp + ".txt");
    }

    public void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.i(TAG, str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.i(TAG, str); // continuation
        }
    }
    }

