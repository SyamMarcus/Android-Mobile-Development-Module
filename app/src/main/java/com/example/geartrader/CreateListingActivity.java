package com.example.geartrader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateListingActivity extends AppCompatActivity {
    // Create class-member variables
    EditText Title, Summary, Price;
    private Button openMainButton;
    private Button createListing;
    private ImageView listingImageView;
    private Button selectImageButton;
    private Button openMapsButton;
    private static final String TAG = "3";

    final int galleryRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        // Set variables
        Title = (EditText) findViewById(R.id.titleEditTextView);
        Summary = (EditText) findViewById(R.id.summaryEditTextView);
        Price = (EditText) findViewById(R.id.priceEditTextView);
        listingImageView = (ImageView) findViewById(R.id.listingImageView);

        // Create new button object for the openMain function
        openMainButton = (Button) findViewById(R.id.openMainButton);
        openMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        openMapsButton = (Button) findViewById(R.id.openMapsButton);
        openMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });

        // Create new button object to request permission to read from gallery storage
        selectImageButton = (Button) findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(CreateListingActivity.this,
                        new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        galleryRequestCode
                );
            }
        });

        // Create new button object for the createListing function
        createListing = (Button) findViewById(R.id.createListing);
        createListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"createListing button clicked");
                DbHelper dbHelper = new DbHelper( CreateListingActivity.this);
                if (validateCreateListing()) {
                    dbHelper.createListing(CreateListingActivity.this);
                    Title.setText("");
                    Summary.setText("");
                    Price.setText("");
                    listingImageView.setImageBitmap(Bitmap.createBitmap(200, 200,  Bitmap.Config.ARGB_8888));
                }
            }
        });
    }

    // Create new intent to start Main Activity
    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openMaps() {
        Intent intent = new Intent(this,  MapsActivityCurrentPlace.class);
        startActivity(intent);
    }

    // Convert ImageView to byte array
    public byte[] imageViewToByte (ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

    // Getter functions for createListing
    public String getListingTitle() { return Title.getText().toString(); }
    public String getSummary() { return Summary.getText().toString(); }
    public String getPrice() { return Price.getText().toString(); }
    public byte[] getImage() { return imageViewToByte(listingImageView); }

    // Validate the strings for the createListing function
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

    // If Permissions are requested && its for the galleryRequestCode start intent and start activity for finding image
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == galleryRequestCode) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, galleryRequestCode);
            }
            else {
                Toast.makeText(getApplicationContext(), "You do not have permission to access storage", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // If the result code for new activity is ok, set the listingImageView to the selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == galleryRequestCode && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                listingImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException error) {
                error.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}