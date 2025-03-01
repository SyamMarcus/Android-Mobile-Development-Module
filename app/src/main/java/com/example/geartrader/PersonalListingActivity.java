package com.example.geartrader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalListingActivity extends AppCompatActivity {
    // Create class-member variables
    int id;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView dateTextView;
    private TextView authorTextView;
    private TextView categoryTextView;
    private TextView summaryTextView;
    private Button deleteListingButton;
    private Button openMapButton;
    private ImageView listingImageView;
    Animation animFadeIn;

    private static final String TAG = "Personal Listing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_listing);

        // Setting variables
        titleTextView = findViewById(R.id.titleTextView);
        priceTextView = findViewById(R.id.priceTextView);
        dateTextView = findViewById(R.id.dateTextView);
        authorTextView = findViewById(R.id.authorTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        summaryTextView = findViewById(R.id.summaryTextView);
        listingImageView = findViewById(R.id.listingImageView);

        // Create a new DbHelper object and set the id for the intent
        DbHelper dbHelper = new DbHelper(PersonalListingActivity.this);
        id = getIntent().getIntExtra("id", 1);
        ListingModel listingModel = dbHelper.getListingById(id);

        // Setting the views using the listing model getter data
        titleTextView.setText(listingModel.getTitle());
        priceTextView.setText("£" + String.valueOf(listingModel.getPrice()));
        dateTextView.setText(listingModel.getDate());
        authorTextView.setText(listingModel.getAuthor());
        categoryTextView.setText(listingModel.getCategory());
        summaryTextView.setText(listingModel.getSummary());
        Bitmap bitmap = BitmapFactory.decodeByteArray(listingModel.getImage(), 0, listingModel.getImage().length);
        listingImageView.setImageBitmap(bitmap);

        // Animation to fade in listing image
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        listingImageView.setVisibility(View.VISIBLE);
        listingImageView.startAnimation(animFadeIn);

        // Create new button object for the deleteListing function
        deleteListingButton = findViewById(R.id. deleteListingButton);
        deleteListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListing(dbHelper, listingModel.getId());
            }
        });

        // Create new button object for the openMap function
        openMapButton = (Button) findViewById(R.id. openMapButton);
        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(dbHelper);
            }
        });

    }

    // Delete listing function
    public void deleteListing(DbHelper dbHelper, int id) {
        // Create alert for deletion confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Listing");
        builder.setMessage("Are you sure you want to delete this listing?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    // If the user confirms deletion, call DB function to delete by ID
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteListing(id);
                        Log.d(TAG,"delete listing confirmed");
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            // If the user cancels deletion, return
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG,"delete listing canceled");
                return;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Create new intent to start Map Activity
    public void openMap(DbHelper dbHelper) {
        Log.d(TAG,"open Map");
        // Create local listing model object
        ListingModel listingModel = dbHelper.getListingById(id);
        // If the listing contains latitude and longitude data is present, open maps activity
        if (listingModel.getLat() != 0.0 && listingModel.getLng() != 0.0) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("lat", listingModel.getLat());
            intent.putExtra("lng", listingModel.getLng());
            startActivity(intent);
        } else {
            Log.d(TAG,"open Map: no location");
            Toast.makeText(this, "This Listing does not include a location", Toast.LENGTH_SHORT).show();
        }
    }
}