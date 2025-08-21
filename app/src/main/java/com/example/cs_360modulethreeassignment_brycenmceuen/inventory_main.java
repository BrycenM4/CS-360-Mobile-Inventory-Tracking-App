package com.example.cs_360modulethreeassignment_brycenmceuen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class inventory_main extends AppCompatActivity {

    RecyclerView inventoryRecyclerView;
    Button addItemButton;
    ArrayList<Item> itemList;
    InventoryAdapter adapter;


    DatabaseHelper dbHelper;
    String currentUser; // Current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_grid_main);

        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);
        addItemButton = findViewById(R.id.addItemButton);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Get current user
        currentUser = getIntent().getStringExtra("username");

        // Load user's inventory from database
        itemList = dbHelper.getItems(currentUser);

        // Set up RecyclerView
        adapter = new InventoryAdapter(itemList, this, dbHelper);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventoryRecyclerView.setAdapter(adapter);

        // Add Item button click
        addItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(inventory_main.this, inventory_add_item.class);
            intent.putExtra("username", currentUser);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh inventory when returning from AddItemActivity
        itemList.clear();
        itemList.addAll(dbHelper.getItems(currentUser));
        adapter.notifyDataSetChanged();
    }

}
