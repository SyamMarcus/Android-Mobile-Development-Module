package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleListingActivity extends AppCompatActivity {

    int id;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView dateTextView;
    private TextView authorTextView;
    private TextView categoryTextView;
    private TextView summaryTextView;
    private Button openMapButton;
    private ImageView listingImageView;
    Animation animFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_listing);

        // Setting variables
        titleTextView = findViewById(R.id.titleTextView);
        priceTextView = findViewById(R.id.priceTextView);
        dateTextView = findViewById(R.id.dateTextView);
        authorTextView = findViewById(R.id.authorTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        summaryTextView = findViewById(R.id.summaryTextView);
        listingImageView = findViewById(R.id.listingImageView);

        // Create a new DbHelper object and set the id for the intent
        DbHelper dbHelper = new DbHelper(SingleListingActivity.this);
        id = getIntent().getIntExtra("id", 1);
        ListingModel listingModel = dbHelper.getListingById(id);

        titleTextView.setText(listingModel.getTitle());
        priceTextView.setText(String.valueOf(listingModel.getPrice()));
        dateTextView.setText(listingModel.getDate());
        authorTextView.setText(listingModel.getAuthor());
        categoryTextView.setText(listingModel.getCategory());
        summaryTextView.setText(listingModel.getSummary());
        Bitmap bitmap = BitmapFactory.decodeByteArray(listingModel.getImage(), 0, listingModel.getImage().length);
        listingImageView.setImageBitmap(bitmap);

        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        listingImageView.setVisibility(View.VISIBLE);
        listingImageView.startAnimation(animFadeIn);

        // Create new button object for the openMap function
        openMapButton = (Button) findViewById(R.id. openMapButton);
        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(dbHelper);
            }
        });

    }
    public void openMap(DbHelper dbHelper) {
        ListingModel listingModel = dbHelper.getListingById(id);

        if (listingModel.getLat() != 0.0 && listingModel.getLng() != 0.0) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("lat", listingModel.getLat());
            intent.putExtra("lng", listingModel.getLng());
            startActivity(intent);
        } else {
            Toast.makeText(this, "This Listing does not include a location", Toast.LENGTH_SHORT).show();
        }
    }
}