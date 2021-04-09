package com.example.geartrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Session {

    // Create new private shared preference variable
    private SharedPreferences prefs;

    private static final String TAG = "Session";

    public Session(Context context) {
        // Set shared preference
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        Log.d(TAG,"set username");
        prefs.edit().putString("username", username).commit();
    }

    public String getUsername() {
        Log.d(TAG,"get username");
        String usename = prefs.getString("username","");
        return usename;
    }
}