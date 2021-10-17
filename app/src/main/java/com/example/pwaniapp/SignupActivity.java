package com.example.pwaniapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText etusername, etemail, etpassword;
    private Button btn_signup;
    private TextView link_signin;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etusername = findViewById(R.id.etusername);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btn_signup = findViewById(R.id.btn_signup);
        link_signin = findViewById(R.id.link_signup);
        loading = findViewById(R.id.loading);

        link_signin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        btn_signup.setOnClickListener(v -> signup());

    }

    private void signup() {
        //on press toggle te visisbility
        btn_signup.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);


        final String username = this.etusername.getText().toString().trim();
        final String password = this.etpassword.getText().toString().trim();
        final String email =  this.etemail.getText().toString().trim();

        //check for null entries
        if (TextUtils.isEmpty(username)){
            etusername.setError("enter your username");
            btn_signup.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            etusername.requestFocus();
        }

        if(TextUtils.isEmpty(email)){
            etemail.setError("enter email");
            etemail.requestFocus();
            loading.setVisibility(View.GONE);
            btn_signup.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(password)){
            etpassword.setError("enter password");
            etpassword.requestFocus();
            loading.setVisibility(View.GONE);
            btn_signup.setVisibility(View.VISIBLE);
        }

        //intiate a volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //create a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, response -> {
            //JSONob to get response

            try {
                JSONObject obj = new JSONObject(response);
                boolean error = obj.getBoolean("error");

                //check if error is false
                if (!error){
                    Toast.makeText(SignupActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this,StudentDashboard.class));
                    finish();
                }
                //if error is true
                if (error){
                    Toast.makeText(SignupActivity.this,obj.getString("message"), Toast.LENGTH_SHORT).show();
                    btn_signup.setVisibility(View.VISIBLE);
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
            Toast.makeText(SignupActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.GONE);
            btn_signup.setVisibility(View.VISIBLE);

        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}