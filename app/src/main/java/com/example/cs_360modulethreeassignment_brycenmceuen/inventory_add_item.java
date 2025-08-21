package com.example.cs_360modulethreeassignment_brycenmceuen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;


public class inventory_add_item extends AppCompatActivity {

    EditText itemNameEditText, itemDescEditText, itemQuantEditText;
    Button addItemButton, selectImageButton;

    DatabaseHelper dbHelper;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_add_item);

        // Initialize views
        itemNameEditText = findViewById(R.id.itemName);
        itemDescEditText = findViewById(R.id.itemDescription);
        itemQuantEditText = findViewById(R.id.itemQuantity);
        addItemButton = findViewById(R.id.addItem);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = itemNameEditText.getText().toString().trim();
                String desc = itemDescEditText.getText().toString().trim();
                addItemButton.setEnabled(!name.isEmpty() && !desc.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        itemNameEditText.addTextChangedListener(textWatcher);
        itemDescEditText.addTextChangedListener(textWatcher);


        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Get current user from intent
        currentUser = getIntent().getStringExtra("username");

        // Add item to database when button clicked
        addItemButton.setOnClickListener(v -> {
            String name = itemNameEditText.getText().toString().trim();
            String desc = itemDescEditText.getText().toString().trim();
            String quant = itemQuantEditText.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(inventory_add_item.this, "Item name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = 0;
            if (!quant.isEmpty()) {
                try {
                    quantity = Integer.parseInt(quant);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            boolean success = dbHelper.addItem(currentUser, name, desc, quantity);
            // Confirmation message
            if (success) {
                Toast.makeText(inventory_add_item.this, "Item added!", Toast.LENGTH_SHORT).show();
                finish(); // return to inventory grid
            } else {
                Toast.makeText(inventory_add_item.this, "Failed to add item.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}