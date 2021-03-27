package com.example.geartrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getUsename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}