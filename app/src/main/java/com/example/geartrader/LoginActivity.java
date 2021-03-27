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
    private Button openRegisterButton;
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

        // Create new button object for the openRegister function
        openRegisterButton = findViewById(R.id.openRegisterButton);
        openRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        // Create new button object for the login function
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Login button clicked");
                DbHelper dbHelper = new DbHelper(LoginActivity.this);
                if (dbHelper.authenticateUser(Username.getText().toString().trim(), Pass.getText().toString().trim())) {
                    session.setUsername(Username.getText().toString().trim());
                    Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect User Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Create new intent to start Register Activity
    public void openRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}