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

public class home_dispatch extends Fragment {

    private EditText etproductid, ettruckid, etsurce, etdestination, etstatus;
    private Button btnDispatch;
    private sharedviewmodel viewModel;

    public home_dispatch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_dispatch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etproductid = view.findViewById(R.id.etproductid);
        ettruckid = view.findViewById(R.id.ettruckid);
        etsurce = view.findViewById(R.id.etsource);
        etdestination = view.findViewById(R.id.etdestination);
        etstatus = view.findViewById(R.id.etstatus);
        btnDispatch = view.findViewById(R.id.btnDispatch);

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(sharedviewmodel.class);

        // Observe changes in product ID and truck ID
        viewModel.getProductId().observe(getViewLifecycleOwner(), productId -> {
            // Update UI or store itemId
            etproductid.setText(productId); // Set product ID to EditText
        });

        viewModel.getTruckId().observe(getViewLifecycleOwner(), truckId -> {
            // Update UI or store truckId
            ettruckid.setText(truckId); // Set truck ID to EditText
        });

        btnDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchItem();
            }
        });
    }

    private void dispatchItem() {
        String productID = etproductid.getText().toString().trim();
        String truckID = ettruckid.getText().toString().trim();
        String source = etsurce.getText().toString().trim();
        String destination = etdestination.getText().toString().trim();
        String status = etstatus.getText().toString().trim();

        // Check if all fields are entered
        if (productID.isEmpty() || truckID.isEmpty() || source.isEmpty() || destination.isEmpty() || status.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform dispatch operation (add your dispatch logic here)
        Toast.makeText(getContext(), "Item dispatched successfully", Toast.LENGTH_SHORT).show();

        // Simulate dispatch completion
        updateStatus(true);
    }

    private void updateStatus(boolean completed) {
        String status = completed ? "Completed" : "Pending";
        // Navigate to StatusActivity or update UI to show status
        // For demonstration, let's show a toast
        Toast.makeText(getContext(), "Dispatch status: " + status, Toast.LENGTH_SHORT).show();
    }
}
