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
    Session session;

    private static final String TAG = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);

        // Set variables
        Username = findViewById(R.id.userEditTextView);
        Pass = findViewById(R.id.passEditTextView);

        // Create new button object for the login function
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Login button clicked");
                DbHelper dbHelper = new DbHelper(LoginActivity.this);
                if (dbHelper.authenticateUser(Username.getText().toString().trim(), Pass.getText().toString().trim())) {
                    session.setUsename(Username.getText().toString().trim());
                    Toast.makeText(LoginActivity.this, "User Exists!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "User DOESN'T Exists!", Toast.LENGTH_SHORT).show();
                }
                // Reset editText boxes
                //Username.setText("");
                //Pass.setText("");
            }
        });
    }


}