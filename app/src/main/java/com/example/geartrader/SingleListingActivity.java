package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleListingActivity extends AppCompatActivity {

    int id;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView dateTextView;
    private TextView summaryTextView;
    private Button openMapButton;
    private ImageView listingImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_listing);

        titleTextView = findViewById(R.id.titleTextView);
        priceTextView = findViewById(R.id.priceTextView);
        dateTextView = findViewById(R.id.dateTextView);
        listingImageView = findViewById(R.id.listingImageView);

        DbHelper dbHelper = new DbHelper(SingleListingActivity.this);
        id = getIntent().getIntExtra("id", 1);
        ListingModel listingModel = dbHelper.getListingById(id);

        titleTextView.setText(listingModel.getTitle());
        priceTextView.setText(String.valueOf(listingModel.getPrice()));
        dateTextView.setText(listingModel.getDate());
        //summaryTextView.setText(listingModel.getSummary());

        Bitmap bitmap = BitmapFactory.decodeByteArray(listingModel.getImage(), 0, listingModel.getImage().length);
        listingImageView.setImageBitmap(bitmap);

        // Create new button object for the openRegister function
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
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("lat", listingModel.getLat());
        intent.putExtra("lng", listingModel.getLng());
        startActivity(intent);
    }
}