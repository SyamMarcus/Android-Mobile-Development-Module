package com.example.geartrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    // Create new private shared preference variable
    private SharedPreferences prefs;

    public Session(Context context) {
        // Set shared preference
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getUsername() {
        String usename = prefs.getString("username","");
        return usename;
    }
}