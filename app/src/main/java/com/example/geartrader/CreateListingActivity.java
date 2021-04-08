package com.example.geartrader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CreateListingActivity extends AppCompatActivity {
    // Create class-member variables
    EditText Title, Summary, Price;
    private Button selectCategoryButton;
    private Button createListing;
    private Button openCameraButton;
    private ImageView listingImageView;
    private Button selectImageButton;
    private Button openMapsButton;
    private Double Lat = 0.0;
    private Double Lng = 0.0;
    private String Category = "Other";
    private TextView categoryTextView;
    private TextView locationTextView;
    private Session session;


    private static final String TAG = "Create Listing";

    final int cameraRequestCode = 300;
    final int galleryRequestCode = 100;
    final int mapsRequestCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        session = new Session(this);

        // Set variables
        Title = findViewById(R.id.titleEditTextView);
        Summary = findViewById(R.id.summaryEditTextView);
        Price = findViewById(R.id.priceEditTextView);
        listingImageView = findViewById(R.id.listingImageView);
        categoryTextView = findViewById(R.id.categoryTextView);
        locationTextView = findViewById(R.id.locationTextView);


        selectCategoryButton = findViewById(R.id.selectCategoryButton);
        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCategory();
            }
        });


        // Create new button object for the openCamera function
        openCameraButton = findViewById(R.id.openCameraButton);
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        // Create new button object to open the maps activity
        openMapsButton = findViewById(R.id.openMapsButton);
        openMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });

        // Create new button object to request permission to read from gallery storage
        selectImageButton = findViewById(R.id.selectImageButton);
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
        createListing = findViewById(R.id.createListing);
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
                    Lat = 0.0;
                    Lng = 0.0;
                    categoryTextView.setText("category");
                    listingImageView.setImageBitmap(Bitmap.createBitmap(200, 200,  Bitmap.Config.ARGB_8888));
                }
            }
        });
    }

    public void selectCategory() {
        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(CreateListingActivity.this, selectCategoryButton);
        popupMenu.getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Set the category equal to the selected menu item
                Category = menuItem.getTitle().toString().trim();
                categoryTextView.setText(Category);
                Toast.makeText(CreateListingActivity.this, menuItem.getTitle().toString() + " selected", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenu.show();
    }

    // Open the Maps Current Place activity
    public void openMaps() {
        Intent intent = new Intent(this,  MapsActivityCurrentPlace.class);
        startActivityForResult(intent, mapsRequestCode);
    }

    // Convert ImageView to byte array
    public byte[] imageViewToByte (ImageView imageView) {
        if (imageView == null) {
            return new byte[0];
        }
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

    // Getter functions for createListing
    public String getListingTitle() { return Title.getText().toString().trim(); }
    public String getSummary() { return Summary.getText().toString().trim(); }
    public String getPrice() { return Price.getText().toString().trim(); }
    public byte[] getImage() { return imageViewToByte(listingImageView); }
    public double getLat() { return Lat; }
    public double getLng() { return Lng; }
    public String getCategory() { return Category; }
    public String getAuthor() { return session.getUsername(); }



    // Validate the strings for the createListing function
    private boolean validateCreateListing() {
        String title = Title.getText().toString().trim();
        String summary = Summary.getText().toString().trim();
        String price = Price.getText().toString();

        if (title.length() < 4 || title.length() > 24) {
            Toast.makeText(CreateListingActivity.this, "Incorrect Title Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (summary.length() < 10 || summary.length() > 150)  {
            Toast.makeText(CreateListingActivity.this, "Incorrect Summary Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (price.length() <= 0 || price.length() > 10)  {
            Toast.makeText(CreateListingActivity.this, "Incorrect Price Details", Toast.LENGTH_SHORT).show();
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
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryRequestCode && resultCode == RESULT_OK && data != null) {
            Log.e(TAG,"ResultCode==galleryRequestCode");
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                listingImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException error) {
                error.printStackTrace();
            }
        }



        if (requestCode == mapsRequestCode && data != null) {
            Log.e(TAG,"RequestCode==mapsRequestCode");
            if (data != null) {
                double[] latlng = data.getDoubleArrayExtra("latlng");
                Lat = latlng[0];
                Lng = latlng[1];
                locationTextView.setText("Location Set");
            } else {
                Log.e(TAG,"Maps Activity Error");
            }
        }
    }

    
    public void openCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}