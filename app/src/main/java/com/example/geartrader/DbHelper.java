package com.example.geartrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    // Strings to be used in database queries
    // Strings for users Table
    private static final String USERS_TABLE = "users";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String EMAIL = "EMAIL";
    private static final String CREATE_USERS_TABLE = "CREATE TABLE "+USERS_TABLE+ " " + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+NAME+" VARCHAR(255) ,"+PASSWORD+" VARCHAR(225) ,"+EMAIL+" VARCHAR(255));";

    // Strings for listings Table
    private static final String LISTINGS_TABLE = "listings";
    private static final String TITLE = "TITLE";
    private static final String PRICE = "PRICE";
    private static final String SUMMARY = "SUMMARY";
    private static final String DATE_CREATED = "DATE_CREATED";
    private static final String IMAGE = "IMAGE";
    private static final String LAT = "LAT";
    private static final String LNG = "LNG";
    private static final String ACTIVE = "ACTIVE";
    private static final String AUTHOR_ID = "AUTHOR_ID";
    private static final String CREATE_LISTINGS_TABLE = "CREATE TABLE "+LISTINGS_TABLE+ " " + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+TITLE+" VARCHAR(255) NOT NULL, "+PRICE+" REAL NOT NULL, "+SUMMARY+" VARCHAR(225) NOT NULL,"+DATE_CREATED+" DATETIME DEFAULT CURRENT_TIMESTAMP, "+IMAGE+" BLOB, "+LAT+" REAL, "+LNG+" REAL,"+ACTIVE+" BOOL, "+AUTHOR_ID+" INT);";

    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+ USERS_TABLE;
    private static final String TAG = "db";

    public DbHelper(@Nullable Context context) {
        super(context, "GearTrader.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_LISTINGS_TABLE);
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

    // Method for creating a new user in the users table
    public boolean createUser(RegisterActivity registerActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Get query data from RegisterActivity
        cv.put(NAME, registerActivity.getUsername());
        cv.put(PASSWORD, registerActivity.getPassword());
        cv.put(EMAIL, registerActivity.getEmail());

        try {
            db.insert(USERS_TABLE, null, cv);
            Toast.makeText(registerActivity, "Created new user", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(registerActivity, "Failed to create new user" , Toast.LENGTH_SHORT).show();
        }

        db.close();
        return true;
    }

    // Method for creating a new listing in the listings table
    public boolean createListing(CreateListingActivity createListingActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Get query data from CreateListingActivity
        cv.put(TITLE, createListingActivity.getListingTitle());
        cv.put(SUMMARY, createListingActivity.getSummary());
        cv.put(PRICE, createListingActivity.getPrice());
        cv.put(IMAGE, createListingActivity.getImage());
        cv.put(LAT, createListingActivity.getLat());
        cv.put(LNG, createListingActivity.getLng());

        try {
            db.insert(LISTINGS_TABLE, null, cv);
            Toast.makeText(createListingActivity, "Created new Listing", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(createListingActivity, "Failed to create new Listing" , Toast.LENGTH_SHORT).show();
        }

        db.close();
        return true;
    }

    // Method for getting all entries in LISTINGS_TABLE using a list of ListingModel objects
    public List<ListingModel> getAllListings() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Initialise new array list to return an array of ListingModel objects
        List<ListingModel> returnList = new ArrayList<>();

        // Setup database query from readable database
        String query = "SELECT * FROM " + LISTINGS_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        // Move to first entry in Listings table and append ListingModel objects to the return list
        // Continue moving through the Listing table until there are no more entries.
        if (cursor.moveToFirst()) {
            do {
                int Id = cursor.getInt(0);
                String Title = cursor.getString(1);
                int Price = cursor.getInt(2);

                ListingModel listingModel = new ListingModel(Id, Title, Price);
                returnList.add(listingModel);

            } while (cursor.moveToNext());
        } else {
            //Failure to move to find first entry
        }

        cursor.close();
        db.close();
        return returnList;


    }

    public ListingModel getListing() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Initialise new array list to return an array of ListingModel objects

        // Setup database query from readable database
        String query = "SELECT * FROM " + LISTINGS_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        // Move to first entry in Listings table and append ListingModel objects to the return list
        // Continue moving through the Listing table until there are no more entries.
        cursor.moveToFirst();
        int Id = cursor.getInt(0);
        String Title = cursor.getString(1);
        int Price = cursor.getInt(2);
        double Lat = cursor.getDouble(6);
        double Lng = cursor.getDouble(7);

        ListingModel listingModel = new ListingModel(Id, Title, Price, Lat, Lng);

        cursor.close();
        db.close();
        return listingModel;
    }
}


