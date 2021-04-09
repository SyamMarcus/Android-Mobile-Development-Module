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
    private static final String CATEGORY = "CATEGORY";
    private static final String ACTIVE = "ACTIVE";
    private static final String AUTHOR = "AUTHOR";
    private static final String CREATE_LISTINGS_TABLE = "CREATE TABLE "+LISTINGS_TABLE+ " " + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+TITLE+" VARCHAR(255) NOT NULL, "+PRICE+" REAL NOT NULL, "+SUMMARY+" VARCHAR(225) NOT NULL,"+DATE_CREATED+" DATETIME DEFAULT CURRENT_TIMESTAMP, "+IMAGE+" BLOB, "+LAT+" REAL, "+LNG+" REAL,"+CATEGORY+" VARCHAR(225) NOT NULL, "+ACTIVE+" BOOL, "+AUTHOR+" VARCHAR(225));";

    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+ USERS_TABLE;
    private static final String TAG = "DbHelper";

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
            Log.e(TAG,"Failed to create new user");
            Toast.makeText(registerActivity, "Failed to create new user" , Toast.LENGTH_SHORT).show();
        }

        db.close();
        return true;
    }

    // Method for creating a new user in the users table
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USERS_TABLE + " WHERE " + NAME + " = '" + username + "' AND " + PASSWORD + " = '" + password + "';";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    // Method to check a listing exists in the DB using listing ID
    public boolean checkListingExists(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.e("db", "ID: " + id);
        String query = "SELECT * FROM " + LISTINGS_TABLE + " WHERE ID = " + id;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    // Method to delete a listing from the DB using listing ID
    public boolean deleteListing(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Setup database query from readable database
        String query = "DELETE FROM " + LISTINGS_TABLE + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return  false;
        }
    }

    // Method for creating a new listing in the listings table
    public boolean createListing(CreateListingActivity createListingActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Get query data from CreateListingActivity
        cv.put(TITLE, createListingActivity.getListingTitle());
        cv.put(PRICE, createListingActivity.getPrice());
        cv.put(SUMMARY, createListingActivity.getSummary());
        cv.put(IMAGE, createListingActivity.getImage());
        cv.put(LAT, createListingActivity.getLat());
        cv.put(LNG, createListingActivity.getLng());
        cv.put(CATEGORY, createListingActivity.getCategory());
        cv.put(AUTHOR, createListingActivity.getAuthor());

        // Response toast messages for the insert result
        try {
            db.insert(LISTINGS_TABLE, null, cv);
            Toast.makeText(createListingActivity, "Created new Listing", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG,"Failed to create new listing");
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
                float Price = cursor.getFloat(2);
                String Category = cursor.getString(8);

                ListingModel listingModel = new ListingModel(Id, Title, Price, Category);
                returnList.add(listingModel);

            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"Failed to find entry");
        }

        cursor.close();
        db.close();
        return returnList;
    }

    // Method for getting all  ListingModel objects in LISTINGS_TABLE matching the author
    public List<ListingModel> getAllListingByAuthor(String author) {

        SQLiteDatabase db = this.getReadableDatabase();

        // Initialise new array list to return an array of ListingModel objects
        List<ListingModel> returnList = new ArrayList<>();

        // Setup database query from readable database
        String query = "SELECT * FROM " + LISTINGS_TABLE + " WHERE " + AUTHOR + " = '" + author + "';";
        Cursor cursor = db.rawQuery(query, null);

        // Move to first entry in Listings table and append ListingModel objects to the return list
        // Continue moving through the Listing table until there are no more entries.
        if (cursor.moveToFirst()) {
            do {
                int Id = cursor.getInt(0);
                String Title = cursor.getString(1);
                float Price = cursor.getFloat(2);
                String Category = cursor.getString(8);

                ListingModel listingModel = new ListingModel(Id, Title, Price, Category);
                returnList.add(listingModel);

            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"Failed to find entry");
        }

        cursor.close();
        db.close();
        return returnList;
    }

    // Method for getting all entries in LISTINGS_TABLE using a list of ListingModel objects
    public List<ListingModel> getListingByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Initialise new array list to return an array of ListingModel objects
        List<ListingModel> returnList = new ArrayList<>();

        // Setup database query from readable database
        String query = "SELECT * FROM " + LISTINGS_TABLE + " WHERE " + CATEGORY + " = '" + category + "';";

        Cursor cursor = db.rawQuery(query, null);
        // Move to first entry in Listings table and append ListingModel objects to the return list
        // Continue moving through the Listing table until there are no more entries.
        if (cursor.moveToFirst()) {
            do {
                int Id = cursor.getInt(0);
                String Title = cursor.getString(1);
                float Price = cursor.getFloat(2);
                String Category = cursor.getString(8);

                ListingModel listingModel = new ListingModel(Id, Title, Price, Category);
                returnList.add(listingModel);

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();
        return returnList;
    }

    // Method from receiving detailed listing information from the DB using listing ID
    public ListingModel getListingById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Setup database query from readable database
        String query = "SELECT * FROM " + LISTINGS_TABLE + " WHERE ID = " + id;

        Cursor cursor = db.rawQuery(query, null);

        // Move to first entry in Listings table and append ListingModel objects to the return list
        // Continue moving through the Listing table until there are no more entries.
        cursor.moveToFirst();
        int Id = cursor.getInt(0);
        String Title = cursor.getString(1);
        float Price = cursor.getFloat(2);
        String Summary = cursor.getString(3);
        String Date = cursor.getString(4);
        byte[] Image = cursor.getBlob(5);
        double Lat = cursor.getDouble(6);
        double Lng = cursor.getDouble(7);
        String Category = cursor.getString(8);
        String Author = cursor.getString(10);

        ListingModel listingModel = new ListingModel(Id, Title, Price, Summary, Date, Image, Lat, Lng, Category, Author);

        cursor.close();
        db.close();
        return listingModel;
    }
}


