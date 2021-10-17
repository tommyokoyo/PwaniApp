package com.example.pwaniapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedprefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //constructor
    public SharedprefManager(Context context){
        sharedPreferences = context.getSharedPreferences("Appkey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //set login method
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    //get login method
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    //set username method
    public void setUsername(String username) {
        editor.putString("KEY_USERNAME", username);
        editor.commit();
    }

    //get username method
    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME", "");
    }

    //set password method
    public void setPassword(String password) {
        editor.putString("KEY_PASSWORD", password);
        editor.commit();
    }

    //get password method
    public String getPassword(){
        return sharedPreferences.getString("KEY_PASSWORD", "");
    }
}
