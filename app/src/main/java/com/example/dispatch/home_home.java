package com.example.dispatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class home_home extends Fragment {

    private EditText editTextSearch;
    private Button buttonSearch;
    private TextView textTotalTrucks, textRecentActivity, textStatusOverview;

    public home_home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_home, container, false);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        textTotalTrucks = view.findViewById(R.id.textTotalTrucks);
        textRecentActivity = view.findViewById(R.id.textRecentActivity);
        textStatusOverview = view.findViewById(R.id.textStatusOverview);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchById();
            }
        });

        return view;
    }

    public void searchById() {
        String searchText = editTextSearch.getText().toString().trim();

        // Perform search operation based on searchText
        // Example: Fetch data from database and update TextViews accordingly
        // For demonstration, let's assume updating with dummy data
        if (searchText.equals("123")) {
            textTotalTrucks.setText("Total Trucks: 50");
            textRecentActivity.setText("Truck ID 123 dispatched to location XYZ");
            textStatusOverview.setText("In Transit: 20");
        } else {
            Toast.makeText(getContext(), "Truck ID not found", Toast.LENGTH_SHORT).show();
        }
    }
}
