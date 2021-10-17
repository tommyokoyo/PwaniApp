package com.example.pwaniapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class StudentDashboard extends AppCompatActivity {

    private TextView username_display;
    private TextView message_display;
    CardView profile;
    CardView checkin;
    SharedprefManager sharedprefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_student_dashboard);

            profile = findViewById(R.id.profile_card);
            checkin = findViewById(R.id.check_in_card);
            username_display = findViewById(R.id.username_display);
            message_display = findViewById(R.id.message_display);

            sharedprefManager = new SharedprefManager(getApplicationContext());

            //display username
            getUsername();

            //display message
            getMessage();

            //profile cardboard click listener
            profile.setOnClickListener(v -> startActivity(new Intent(StudentDashboard.this, Profilectivity.class)));
            //checkin carcboard click listener
            checkin.setOnClickListener(v -> startActivity(new Intent(StudentDashboard.this, CheckinActivity.class)));


        }

        public void getUsername(){

            String username = sharedprefManager.getUsername();

            //split name and display in textview
            String[] separate = username.split(" ");
            username_display.setText(" " + separate[0]+",");

        }

        public void getMessage(){
            String[] message_new = getResources().getStringArray(R.array.message);
            Random random = new Random();
            int n = random.nextInt(message_new.length - 1 );
            message_display.setText(message_new[n]);

        }


        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
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
            this.finish();
            super.onDestroy();
    }


}