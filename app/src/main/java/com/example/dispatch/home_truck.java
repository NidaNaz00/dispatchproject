package com.example.dispatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home_truck extends Fragment {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewtrucks;
    private List<truck> truckList;
    private truckAdapter truckAdapter;
    private EditText truck_id;
    private EditText truck_name;
    private EditText truck_status;
    private EditText truck_model;
    private Button submit_button;

    public home_truck() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_truck, container, false);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("trucks");

        // Initialize RecyclerView and Item List
        recyclerViewtrucks = view.findViewById(R.id.recyclerViewtrucks);
        recyclerViewtrucks.setLayoutManager(new LinearLayoutManager(getContext()));
        truckList = new ArrayList<>();
        truckAdapter = new truckAdapter(truckList, getContext());
        recyclerViewtrucks.setAdapter(truckAdapter);
// In onCreateView or onViewCreated


        // Initialize EditText fields and Submit button
        truck_id = view.findViewById(R.id.truck_id);
        truck_name = view.findViewById(R.id.truck_name);
        truck_status = view.findViewById(R.id.truck_status);
        truck_model = view.findViewById(R.id.truck_model);
        submit_button = view.findViewById(R.id.submit_button);

        // Swipe to delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String truckId = truckList.get(position).getId();
                sharedviewmodel viewModel = new ViewModelProvider(requireActivity()).get(sharedviewmodel.class);
// Assuming truckID is obtained from somewhere, set it in the ViewModel
                viewModel.setTruckId(truckId);
                truckList.remove(position);
                truckAdapter.notifyItemRemoved(position);
                deleteTruckFromFirebase(truckId);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewtrucks);

        // Retrieve existing trucks from Firebase Database
        retrieveTrucksFromFirebase();

        // Handle Submit button click
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTruckToFirebase();
            }
        });

        return view;
    }

    private void retrieveTrucksFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truckList.clear(); // Clear list before adding updated data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    truck truck = snapshot.getValue(truck.class);
                    truckList.add(truck);
                }
                truckAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(getContext(), "Failed to retrieve trucks: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTruckToFirebase() {
        // Get data from EditText fields
        String truckId = truck_id.getText().toString().trim();
        String truckName = truck_name.getText().toString().trim();
        String truckStatus = truck_status.getText().toString().trim();
        String truckModel = truck_model.getText().toString().trim();

        // Validate input fields (Add more validation as per your requirements)
        if (truckId.isEmpty() || truckName.isEmpty() || truckStatus.isEmpty() || truckModel.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new truck object
        truck newTruck = new truck(truckId, truckName, truckStatus, truckModel);

        // Add truck to Firebase using push().setValue() to generate unique key
        databaseReference.push().setValue(newTruck)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Truck added successfully", Toast.LENGTH_SHORT).show();
                    // Clear EditText fields after submission
                    truck_id.setText("");
                    truck_name.setText("");
                    truck_status.setText("");
                    truck_model.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to add truck: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteTruckFromFirebase(String truckId) {
        databaseReference.child(truckId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Truck deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to delete truck: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
