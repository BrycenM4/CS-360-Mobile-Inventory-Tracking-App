package com.example.cs_360modulethreeassignment_brycenmceuen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;


public class login_main extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    Button loginButton;
    TextView resetPassword;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Match IDs
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        resetPassword = findViewById(R.id.resetPassword);

        // Enable login button only when both fields are filled
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = usernameInput.getText().toString().trim();
                String pass = passwordInput.getText().toString().trim();
                loginButton.setEnabled(!user.isEmpty() && !pass.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        usernameInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);

        // Login messages
        loginButton.setOnClickListener(v -> {
            String user = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();

            if (dbHelper.loginUser(user, pass)) {
                Toast.makeText(login_main.this, "Login successful!", Toast.LENGTH_SHORT).show();

                // Example: Go to another activity after login
                Intent intent = new Intent(login_main.this, inventory_main.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish(); // optional: close login screen
            } else {
                Toast.makeText(login_main.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
            }
        });
        // Take to sign-up page
        TextView signUpText = findViewById(R.id.signUp);
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(login_main.this, login_sign_up.class);
            startActivity(intent);
        });

        // Take to reset password page
        resetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(login_main.this, reset_password.class);
            startActivity(intent);
        });
    }
}
