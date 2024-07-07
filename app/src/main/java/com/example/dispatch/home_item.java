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

public class home_item extends Fragment {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewItems;
    private List<items> itemList;
    private itemAdapter itemAdapter;
    private EditText item_id;
    private EditText item_name;
    private EditText item_status;
    private Button submit_button;

    public home_item() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_item, container, false);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");

        // Initialize RecyclerView and Item List
        recyclerViewItems = view.findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>();
        itemAdapter = new itemAdapter(itemList, getContext());
        recyclerViewItems.setAdapter(itemAdapter);

        // Swipe to delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String itemId = itemList.get(position).getId();
                sharedviewmodel viewModel = new ViewModelProvider(requireActivity()).get(sharedviewmodel.class);
// Assuming itemID is obtained from somewhere, set it in the ViewModel
                viewModel.setProductId(itemId);
                itemList.remove(position);
                itemAdapter.notifyItemRemoved(position);
                deleteItemFromFirebase(itemId);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewItems);

        // Initialize EditText fields and Submit button
        item_id = view.findViewById(R.id.item_id);
        item_name = view.findViewById(R.id.item_name);
        item_status = view.findViewById(R.id.item_status);
        submit_button = view.findViewById(R.id.submit_button);

        // Retrieve existing items from Firebase Database
        retrieveItemsFromFirebase();

        // Handle Submit button click
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToFirebase();
            }
        });

        return view;
    }

    private void retrieveItemsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear(); // Clear list before adding updated data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    items item = snapshot.getValue(items.class);
                    itemList.add(item);
                }
                itemAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(getContext(), "Failed to retrieve items: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItemToFirebase() {
        // Get data from EditText fields
        String itemId = item_id.getText().toString().trim();
        String itemName = item_name.getText().toString().trim();
        String itemStatus = item_status.getText().toString().trim();

        // Validate input fields (Add more validation as per your requirements)
        if (itemId.isEmpty() || itemName.isEmpty() || itemStatus.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new item object
        items newItem = new items(itemId, itemName, itemStatus);

        // Add item to Firebase using push().setValue() to generate unique key
        databaseReference.child(itemId).setValue(newItem)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                    // Clear EditText fields after submission
                    item_id.setText("");
                    item_name.setText("");
                    item_status.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to add item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteItemFromFirebase(String itemId) {
        databaseReference.child(itemId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to delete item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
