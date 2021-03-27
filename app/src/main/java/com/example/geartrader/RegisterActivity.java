package com.example.geartrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    // Create class-member variables
    EditText Username, Pass, Email;
    private Button openMainButton;
    private Button createUser;
    private static final String TAG = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set variables
        Username = (EditText) findViewById(R.id.userEditTextView);
        Pass = (EditText) findViewById(R.id.passEditTextView);
        Email = (EditText) findViewById(R.id.emailEditTextView);

        // Create new button object for the openMain function
        openMainButton = (Button) findViewById(R.id.openMainButton);
        openMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"openMain button clicked");
                openMain();
            }
        });

        // Create new button object for the createUser function
        createUser = (Button) findViewById(R.id.createUser);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"createUser button clicked");
                DbHelper dbHelper = new DbHelper(RegisterActivity.this);
                if (validateCreateUser()) {
                    dbHelper.createUser(RegisterActivity.this);
                }
                // Reset editText boxes
                Username.setText("");
                Pass.setText("");
                Email.setText("");
                finish();
            }
        });
    }

    // Create new intent to start Main Activity
    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Getter functions for createUser
    public String getUsername() { return Username.getText().toString(); }
    public String getPassword() { return Pass.getText().toString(); }
    public String getEmail() { return Email.getText().toString(); }

    // Validate the strings for the createUser function
    public boolean validateCreateUser() {
        String username = Username.getText().toString();
        String pass = Pass.getText().toString();
        String email = Email.getText().toString();
        if (username.length() < 4 || username.length() > 16) {
            Toast.makeText(RegisterActivity.this, "Incorrect Username Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.length() < 6 || pass.length() > 16)  {
            Toast.makeText(RegisterActivity.this, "Incorrect Password Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() < 6 || email.length() > 36 || !email.contains("@")) {
            Toast.makeText(RegisterActivity.this, "Incorrect Email Address Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}