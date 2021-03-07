package com.example.geartrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "users";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String EMAIL = "EMAIL";
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ " " + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+PASSWORD+" VARCHAR(225) ,"+EMAIL+" VARCHAR(255));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String TAG = "db";

    public DbHelper(@Nullable Context context) {
        super(context, "GearTrader.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            Log.e(TAG,"Failed to create database table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Log.e(TAG,"Failed to upgrade database table");
        }
    }

    public boolean createUser(RegisterActivity registerActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME, registerActivity.getUsername());
        cv.put(PASSWORD, registerActivity.getPassword());
        cv.put(EMAIL, registerActivity.getEmail());

        try {
            db.insert(TABLE_NAME, null, cv);
            Toast.makeText(registerActivity, "Created new user", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(registerActivity, "Failed to create new user" , Toast.LENGTH_SHORT).show();
        }

        db.close();
        return true;
    }
}
