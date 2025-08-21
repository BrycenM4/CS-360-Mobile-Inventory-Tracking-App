package com.example.cs_360modulethreeassignment_brycenmceuen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;
import android.content.Intent;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private ArrayList<Item> itemList;
    private Context context;
    private DatabaseHelper dbHelper;

    // Constructor
    public InventoryAdapter(ArrayList<Item> itemList, Context context, DatabaseHelper dbHelper) {
        this.itemList = itemList;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageButton deleteButton;
        TextView itemQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }

    @NonNull
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.ViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.itemName.setText(currentItem.getName());
        holder.itemQuantity.setText(String.valueOf(currentItem.getQuantity()));

        // Item click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, inventory_item_detail.class);
            intent.putExtra("name", currentItem.getName());
            intent.putExtra("description", currentItem.getDescription());
            intent.putExtra("quantity", currentItem.getQuantity());
            intent.putExtra("itemId", currentItem.getId());
            context.startActivity(intent);
        });

        // Deletion confirmation message
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete from database
                        dbHelper.deleteItem(currentItem.getId());
                        // Remove from list and notify adapter
                        itemList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, itemList.size());
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
