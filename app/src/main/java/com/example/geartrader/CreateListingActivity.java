package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CreateListingActivity extends AppCompatActivity {
    private Button openMainButton;
    private Button createListing;
    private static final String TAG = "3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        openMainButton = (Button) findViewById(R.id.openMainButton);
        openMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        createListing = (Button) findViewById(R.id.createListing);
        createListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"createListing button clicked");
                DbHelper dbHelper = new DbHelper( CreateListingActivity.this);
                dbHelper.createListing();
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}