package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    // Create class-member variables
    private Button openRegisterButton;
    private Button openListingButton;
    private Button displayListingsButton;
    private ListView listingsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set variables
        listingsList = findViewById(R.id.listingListView);

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        displayAllListings(dbHelper);

        // Create new button object for the openRegister function
        openRegisterButton = (Button) findViewById(R.id.openRegisterButton);
        openRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        // Create new button object for the openListing function
        openListingButton = (Button) findViewById(R.id.openListingButton);
        openListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateListing();
            }
        });

        // Create new button object for the displayListings function
        displayListingsButton = (Button) findViewById(R.id.displayListingsButton);
        displayListingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllListings(dbHelper);
            }
        });
    }

    // Create new intent to start Register Activity
    public void openRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // Create new intent to start CreateListing Activity
    public void openCreateListing() {
        Intent intent = new Intent(this, CreateListingActivity.class);
        startActivity(intent);
    }

    public void displayAllListings(DbHelper dbHelper) {
        ArrayAdapter listingsArrayAdapter = new ArrayAdapter<ListingModel>(MainActivity.this, android.R.layout.simple_list_item_1, dbHelper.getAllListings());
        listingsList.setAdapter(listingsArrayAdapter);
    }

}