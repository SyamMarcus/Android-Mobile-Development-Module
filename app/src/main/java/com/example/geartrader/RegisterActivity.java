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
    EditText Username, Pass, Email;
    private Button openMainButton;
    private Button createUser;
    private static final String TAG = "3";

    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Username = (EditText) findViewById(R.id.userEditTextView);
        Pass = (EditText) findViewById(R.id.passEditTextView);
        Email = (EditText) findViewById(R.id.emailEditTextView);

        openMainButton = (Button) findViewById(R.id.openMainButton);
        openMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"openMain button clicked");
                openMain();
            }
        });

        createUser = (Button) findViewById(R.id.createUser);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"createUser button clicked");
                DbHelper dbHelper = new DbHelper(RegisterActivity.this);
                dbHelper.createUser(RegisterActivity.this);
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String getUsername() {
        String username = Username.getText().toString();
        Username.setText("");
        return username;
    }

    public String getPassword() {
        String pass = Pass.getText().toString();
        Pass.setText("");
        return pass;
    }

    public String getEmail() {
        String email = Email.getText().toString();
        Email.setText("");
        return email;
    }
}