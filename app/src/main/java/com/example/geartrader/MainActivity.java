package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    // Create class-member variables
    private Button openLoginButton;
    private Button testButton;
    private FloatingActionButton openListingButton;
    private Button displayListingsButton;
    private Button selectCategoryButton;
    private ListView listingsList;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);

        // Create new button object for the openRegister function
        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getUsername() == "") {
                    Toast.makeText(MainActivity.this, "Login to View Your Listings", Toast.LENGTH_SHORT).show();
                } else {
                    openUserList();
                }
            }
        });
        DbHelper dbHelper = new DbHelper(MainActivity.this);

        // Set variables
        listingsList = findViewById(R.id.listingListView);
        listingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = listingsList.getItemAtPosition(position).toString();
                int ID = Integer.parseInt(String.valueOf(description.charAt(0)));
                Log.d("MainActivity", "Clicked Item: " + id + " at position:" + position);
                if(dbHelper.checkListingExists(ID)) {
                    Intent intent = new Intent(view.getContext(), SingleListingActivity.class);
                    intent.putExtra("id", ID);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Listing no longer exists, please refresh", Toast.LENGTH_SHORT).show();
                }
            }
        });

        displayAllListings(dbHelper);

        // Create new button object for the openRegister function
        openLoginButton = findViewById(R.id.openLoginButton);
        openLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        // Create new button object for the openListing function
        openListingButton = findViewById(R.id.openListingButton);
        openListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.getUsername() == "") {
                    Toast.makeText(MainActivity.this, "Login to Create A Listing", Toast.LENGTH_SHORT).show();
                } else {
                    openCreateListing();
                }
            }
        });

        // Create new button object for the displayListings function
        displayListingsButton = findViewById(R.id.displayListingsButton);
        displayListingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllListings(dbHelper);
            }
        });

        selectCategoryButton = findViewById(R.id.selectCategoryButton);
        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortByCategory(dbHelper);
            }
        });
    }

    public void sortByCategory(DbHelper dbHelper) {
        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, selectCategoryButton);
        popupMenu.getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                ArrayAdapter listingsArrayAdapter = new ArrayAdapter<ListingModel>(MainActivity.this, android.R.layout.simple_list_item_1, dbHelper.getListingByCategory(menuItem.getTitle().toString()));
                listingsList.setAdapter(listingsArrayAdapter);
                Toast.makeText(MainActivity.this, menuItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenu.show();
    }

    // Create new intent to start Login Activity
    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Create new intent to start Login Activity
    public void openUserList() {
        Intent intent = new Intent(this, UserListActivity.class);
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