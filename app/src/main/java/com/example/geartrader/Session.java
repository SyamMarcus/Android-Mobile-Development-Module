package com.example.geartrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public void resetUsername() {
        prefs.edit().putString("username", "").commit();
    }

    public String getUsername() {
        String usename = prefs.getString("username","");
        return usename;
    }
}