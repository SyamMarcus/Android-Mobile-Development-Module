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

public class LoginActivity extends AppCompatActivity {
    // Create class-member variables
    EditText Username, Pass;
    private Button loginButton;
    private static final String TAG = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set variables
        Username = findViewById(R.id.userEditTextView);
        Pass = findViewById(R.id.passEditTextView);

        // Create new button object for the login function
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Login button clicked");
                DbHelper dbHelper = new DbHelper(LoginActivity.this);
                if (validateCreateUser()) {
                    Toast.makeText(LoginActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
                }
                // Reset editText boxes
                Username.setText("");
                Pass.setText("");
            }
        });
    }

    // Getter functions for createUser
    public String getUsername() { return Username.getText().toString(); }
    public String getPassword() { return Pass.getText().toString(); }

    // Validate the strings for the createUser function
    public boolean validateCreateUser() {
        String username = Username.getText().toString();
        String pass = Pass.getText().toString();
        if (username.length() < 4 || username.length() > 16) {
            Toast.makeText(LoginActivity.this, "Incorrect Username Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.length() < 6 || pass.length() > 16)  {
            Toast.makeText(LoginActivity.this, "Incorrect Password Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}