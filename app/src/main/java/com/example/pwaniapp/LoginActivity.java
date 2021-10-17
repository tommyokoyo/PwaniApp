package com.example.pwaniapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    private EditText etusername, etpassword;
    private Button btn_login;
    private TextView link_signup;
    private ProgressBar loading;


    //create sharedpref session manager
    SharedprefManager sharedprefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedprefManager = new SharedprefManager(getApplicationContext());

        //checks if user is logged in
        if (checkLogin()){

            //switch to dashboard
            openDashboard();

        } else {

            setContentView(R.layout.activity_login);

            etusername = findViewById(R.id.etusername);
            etpassword = findViewById(R.id.etpassword);
            btn_login = findViewById(R.id.btn_login);
            link_signup = findViewById(R.id.link_signup);
            loading = findViewById(R.id.loading);

            link_signup.setOnClickListener(v -> {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();

            });

            btn_login.setOnClickListener(v -> {
                //calls the login method
                login();
            });

        }


        }

        public void openDashboard() {

        Intent intent = new Intent(this, StudentDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        }

        public Boolean checkLogin (){

            sharedprefManager = new SharedprefManager(getApplicationContext());

            return sharedprefManager.getLogin();

        }

        public void saveData(String username, String password){
        sharedprefManager = new SharedprefManager(getApplicationContext());

            //store the variables fo the session
            sharedprefManager.setLogin(true);
            sharedprefManager.setUsername(username);
            sharedprefManager.setPassword(password);

        }

        private void login() {
            btn_login.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);

            final String username = this.etusername.getText().toString().trim();
            final String password = this.etpassword.getText().toString().trim();

            //check for null values
            if (TextUtils.isEmpty(username)) {
                etusername.setError("Enter Username");
                etusername.requestFocus();
                btn_login.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

            } else if (TextUtils.isEmpty(password)) {

                etpassword.setError("enter password");
                etpassword.requestFocus();
                btn_login.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

            } else {

                //if everything is fine initiate the request queue

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                //call the request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN, response -> {

                    try {
                        JSONObject obj = new JSONObject(response);
                        int success = obj.getInt("success");

                        //check for error
                        if (success == 1){

                            //display toast message
                            Toast.makeText(LoginActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();

                            // save data
                            saveData(username,password);

                            //switch activity to dashboard
                            openDashboard();

                        }

                        else if (success == 2){

                            Toast.makeText(LoginActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();

                            // save data
                            saveData(username,password);

                            //switch activity to dashboard
                            openDashboard();

                        } else {

                            Toast.makeText(LoginActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                            btn_login.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);

                        }

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }, error -> {

                    // As of f605da3 the following should work
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();

                        }

                    }

                    Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);

                }){

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String,String> params = new HashMap<>();
                        params.put("username",username);
                        params.put("password",password);
                        return params;

                    }

                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<>();
                        params.put("content-type","application/x-www-form-urlencoded");
                        return params;

                    }

                };

                //add the request to the queue
                requestQueue.add(stringRequest);

            }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}