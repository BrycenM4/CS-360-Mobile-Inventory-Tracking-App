package com.example.cs_360modulethreeassignment_brycenmceuen; // replace with your package

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

public class reset_password extends AppCompatActivity {

    DatabaseHelper dbHelper;
    String username; // Store username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);

        resetPassword(); // Enter username
    }

    private void resetPassword() {
        setContentView(R.layout.login_reset_password_main);

        EditText usernameInput = findViewById(R.id.nameText);
        Button nextButton = findViewById(R.id.buttonSayHello);
        nextButton.setEnabled(false);

        // Enable button only when input is not empty
        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(!s.toString().trim().isEmpty());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        // Error message
        nextButton.setOnClickListener(v -> {
            String user = usernameInput.getText().toString().trim();
            if (dbHelper.userExists(user)) { // Check if username exists
                username = user;
                newPassword();
            } else {
                Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void newPassword() {
        setContentView(R.layout.login_reset_password_final);

        EditText passwordInput = findViewById(R.id.nameText);
        Button nextButton = findViewById(R.id.buttonSayHello);
        nextButton.setEnabled(false);

        // Enable button only when input is not empty
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(!s.toString().trim().isEmpty());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        // Confirmation message
        nextButton.setOnClickListener(v -> {
            String newPassword = passwordInput.getText().toString().trim();
            boolean success = dbHelper.updatePassword(username, newPassword);

            if (success) {
                Toast.makeText(this, "Password updated!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to login page
            } else {
                Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

