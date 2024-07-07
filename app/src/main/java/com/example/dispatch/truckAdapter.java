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

public class truckAdapter extends RecyclerView.Adapter<truckAdapter.ViewHolder> {
    private List<truck> truckList;
    private Context context;
    private DatabaseReference databaseReference;

    public truckAdapter(List<truck> truckList, Context context) {
        this.truckList = truckList;
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("trucks");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.truck_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        truck currentTruck = truckList.get(position);
        holder.tvtruckId.setText(currentTruck.getId());
        holder.tvtruckName.setText(currentTruck.getName());
        holder.tvtruckStatus.setText(currentTruck.getStatus());
        holder.tvtruckModel.setText(currentTruck.getModel());
        holder.itemView.setOnLongClickListener(v -> {
            showUpdateDialog(currentTruck);
            return true;
        });
    }

    private void showUpdateDialog(truck currentTruck) {
        AlertDialog.Builder updatedialog = new AlertDialog.Builder(context);
        updatedialog.setTitle("Update Truck Record");
        View view = LayoutInflater.from(context).inflate(R.layout.updatetruck_form, null, false);
        updatedialog.setView(view);

        EditText ettid = view.findViewById(R.id.ettid);
        EditText ettname = view.findViewById(R.id.ettname);
        EditText ettstatus = view.findViewById(R.id.ettstatus);
        EditText ettmodel = view.findViewById(R.id.ettmodel);

        ettid.setText(currentTruck.getId());
        ettname.setText(currentTruck.getName());
        ettstatus.setText(currentTruck.getStatus());
        ettmodel.setText(currentTruck.getModel());

        updatedialog.setPositiveButton("Update", (dialog, which) -> {
            String Id = ettid.getText().toString().trim();
            String Name = ettname.getText().toString().trim();
            String Status = ettstatus.getText().toString().trim();
            String Model = ettmodel.getText().toString().trim();

            if (Id.isEmpty() || Name.isEmpty() || Status.isEmpty() || Model.isEmpty()) {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                updateTruckInFirebase(currentTruck.getId(), Id, Name, Status, Model);
            }
        });

        updatedialog.setNegativeButton("Cancel", null);
        updatedialog.show();
    }

    private void updateTruckInFirebase(String originalId, String newId, String newName, String newStatus, String newModel) {
        DatabaseReference truckRef = databaseReference.child(originalId);
        truckRef.child("id").setValue(newId);
        truckRef.child("name").setValue(newName);
        truckRef.child("status").setValue(newStatus);
        truckRef.child("model").setValue(newModel)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Truck updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to update truck: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return truckList.size();
    }

    public void addTruck(truck newTruck) {
        // Add truck to the local list
        truckList.add(newTruck);

        // Update Firebase database
        databaseReference.push().setValue(newTruck)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Truck added successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add truck: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    truckList.remove(newTruck); // Rollback on failure
                });

        // Notify RecyclerView of data change
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtruckId, tvtruckName, tvtruckStatus, tvtruckModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtruckId = itemView.findViewById(R.id.tvtruckId);
            tvtruckName = itemView.findViewById(R.id.tvtruckName);
            tvtruckStatus = itemView.findViewById(R.id.tvtruckStatus);
            tvtruckModel = itemView.findViewById(R.id.tvtruckModel);
        }
    }
}
