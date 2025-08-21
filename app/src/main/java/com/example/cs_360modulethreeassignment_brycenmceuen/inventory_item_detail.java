package com.example.cs_360modulethreeassignment_brycenmceuen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;


public class inventory_item_detail extends AppCompatActivity {

    TextView itemNameText, itemDescriptionText, itemQuantity;
    EditText itemQuantityEdit;
    Button returnButton, saveQuantityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_item_detail);

        itemNameText = findViewById(R.id.itemName);
        itemDescriptionText = findViewById(R.id.itemDescription);
        itemQuantity = findViewById(R.id.itemQuantity);
        itemQuantityEdit = findViewById(R.id.itemQuantityEdit);
        saveQuantityButton = findViewById(R.id.saveQuantityButton);
        returnButton = findViewById(R.id.ReturnToInventory);

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int quantity = intent.getIntExtra("quantity", 0);


        // Set views
        itemNameText.setText(name);
        itemDescriptionText.setText(description);
        itemQuantity.setText(String.valueOf(quantity));
        itemQuantityEdit.setText(String.valueOf(quantity));

        DatabaseHelper dbHelper = new DatabaseHelper(this); // Initialize DB helper
        int itemId = intent.getIntExtra("itemId", -1); // Pass item ID via intent

        saveQuantityButton.setOnClickListener(v -> {
            try {
                int newQuantity = Integer.parseInt(itemQuantityEdit.getText().toString());
                dbHelper.updateQuantity(itemId, newQuantity);
                itemQuantity.setText(String.valueOf(newQuantity));
                Toast.makeText(this, "Quantity updated!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            }
        });


        returnButton.setOnClickListener(v -> finish()); // Go back to inventory grid
    }
}


