package com.example.cs_360modulethreeassignment_brycenmceuen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Toast;
import android.content.Intent;


public class login_sign_up extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    Button registerButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Link UI ID's
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        registerButton = findViewById(R.id.buttonSayHello);

        // Enable button only if both fields filled
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = usernameInput.getText().toString().trim();
                String pass = passwordInput.getText().toString().trim();
                registerButton.setEnabled(!user.isEmpty() && !pass.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        usernameInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);

        // Input user credentials
        registerButton.setOnClickListener(v -> {
            String user = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();

            // Attempt to register user
            boolean success = dbHelper.registerUser(user, pass);
            // Messages related to signing-up
            if (success) {
                Toast.makeText(login_sign_up.this, "Account created!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(login_sign_up.this, login_main.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(login_sign_up.this, "Username already exists!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
