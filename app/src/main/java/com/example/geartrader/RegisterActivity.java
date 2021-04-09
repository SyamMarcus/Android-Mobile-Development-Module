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
    private Button createUser;

    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set variables
        Username = (EditText) findViewById(R.id.userEditTextView);
        Pass = (EditText) findViewById(R.id.passEditTextView);
        Email = (EditText) findViewById(R.id.emailEditTextView);


        // Create new button object for the createUser function
        createUser = (Button) findViewById(R.id.createUser);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"createUser button clicked");
                DbHelper dbHelper = new DbHelper(RegisterActivity.this);
                if (validateCreateUser(Username.getText().toString(),
                        Pass.getText().toString(),
                        Email.getText().toString())) {
                    dbHelper.createUser(RegisterActivity.this);
                    Log.d(TAG,"user created");
                    // Reset editText boxes
                    Username.setText("");
                    Pass.setText("");
                    Email.setText("");
                    finish();
                }
            }
        });
    }


    // Getter functions for createUser
    public String getUsername() { return Username.getText().toString(); }
    public String getPassword() { return Pass.getText().toString(); }
    public String getEmail() { return Email.getText().toString(); }

    // Validate the strings for the createUser function
    public boolean validateCreateUser(String username, String pass, String email) {
        Log.d(TAG,"validate create user");

        // Validate username
        if (username.length() < 4 || username.length() > 16) {
            //Toast.makeText(RegisterActivity.this, "Incorrect Username Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validate password
        if (pass.length() < 6 || pass.length() > 16)  {
            //Toast.makeText(RegisterActivity.this, "Incorrect Password Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validate email
        if (email.length() < 6 || email.length() > 36 || !email.contains("@")) {
            //Toast.makeText(RegisterActivity.this, "Incorrect Email Address Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}