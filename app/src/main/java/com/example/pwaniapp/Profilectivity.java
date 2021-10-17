package com.example.pwaniapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Profilectivity extends AppCompatActivity {

    CardView signout;
    TextView titlename;
    SharedprefManager sharedprefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilectivity);

        sharedprefManager = new SharedprefManager(getApplicationContext());

        signout = findViewById(R.id.signout);
        titlename = findViewById(R.id.title);

        titlename.setText(sharedprefManager.getUsername());

        signout.setOnClickListener(v -> alertDialog());
    }

    public void alertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Profilectivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> logout());

        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.theme_color));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.theme_color));

    }

    public void logout(){

        sharedprefManager = new SharedprefManager(getApplicationContext());

        //clear the shared pref details
        sharedprefManager.setLogin(false);
        sharedprefManager.setUsername("");
        sharedprefManager.setPassword("");

        //start login activity
        Intent intent = new Intent(Profilectivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Profilectivity.this, StudentDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
        finish();
    }
}