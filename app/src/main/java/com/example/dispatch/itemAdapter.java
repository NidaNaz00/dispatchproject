package com.example.dispatch;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder> {
    private List<items> itemList;
    private Context context;
    private DatabaseReference databaseReference;

    public itemAdapter(List<items> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("items");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        items currentItem = itemList.get(position);
        holder.tvitemId.setText(currentItem.getId());
        holder.tvitemName.setText(currentItem.getName());
        holder.tvitemStatus.setText(currentItem.getStatus());

        holder.itemView.setOnLongClickListener(v -> {
            showUpdateDialog(currentItem);
            return true;
        });
    }

    private void showUpdateDialog(items currentItem) {
        AlertDialog.Builder updatedialog = new AlertDialog.Builder(context);
        updatedialog.setTitle("Update Item Record");
        View view = LayoutInflater.from(context).inflate(R.layout.updateitem_form, null, false);
        updatedialog.setView(view);

        EditText etid = view.findViewById(R.id.etid);
        EditText etname = view.findViewById(R.id.etname);
        EditText etstatus = view.findViewById(R.id.etstatus);

        etid.setText(currentItem.getId());
        etname.setText(currentItem.getName());
        etstatus.setText(currentItem.getStatus());

        updatedialog.setPositiveButton("Update", (dialog, which) -> {
            String Id = etid.getText().toString().trim();
            String Name = etname.getText().toString().trim();
            String Status = etstatus.getText().toString().trim();

            if (Id.isEmpty() || Name.isEmpty() || Status.isEmpty()) {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                updateItemInFirebase(currentItem.getId(), Id, Name, Status);
            }
        });

        updatedialog.setNegativeButton("Cancel", null);
        updatedialog.show();
    }

    private void updateItemInFirebase(String originalId, String newId, String newName, String newStatus) {
        DatabaseReference itemRef = databaseReference.child(originalId);
        itemRef.child("id").setValue(newId);
        itemRef.child("name").setValue(newName);
        itemRef.child("status").setValue(newStatus)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Item updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to update item: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(items newItem) {
        itemList.add(newItem);
        databaseReference.push().setValue(newItem)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Item added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    itemList.remove(newItem);
                });
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvitemId, tvitemName, tvitemStatus;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvitemId = itemView.findViewById(R.id.tvitemId);
            tvitemName = itemView.findViewById(R.id.tvitemName);
            tvitemStatus = itemView.findViewById(R.id.tvitemStatus);
        }
    }
}