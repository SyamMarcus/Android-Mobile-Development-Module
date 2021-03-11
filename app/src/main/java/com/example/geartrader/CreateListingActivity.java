package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateListingActivity extends AppCompatActivity {
    EditText Title, Summary;
    private Button openMainButton;
    private Button createListing;
    private static final String TAG = "3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        Title = (EditText) findViewById(R.id.titleEditTextView);
        Summary = (EditText) findViewById(R.id.summaryEditTextView);

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
                if (validateCreateListing()) {
                    dbHelper.createListing(CreateListingActivity.this);
                }
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String getListingTitle() { return Title.getText().toString(); }
    public String getSummary() { return Summary.getText().toString(); }

    private boolean validateCreateListing() {
        String title = Title.getText().toString();
        String summary = Summary.getText().toString();
        if (title.length() < 4 || title.length() > 16) {
            Toast.makeText(CreateListingActivity.this, "Incorrect Title Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (summary.length() < 6 || summary.length() > 100)  {
            Toast.makeText(CreateListingActivity.this, "Incorrect Summary Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}