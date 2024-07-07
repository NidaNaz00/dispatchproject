package com.example.dispatch;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class home_account extends Fragment {
    private TextView editProfile;
    private ListView lvProfile;
    private ArrayList<profile> profiles;
    private profileAdapter adapter;

    public home_account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_account, container, false);

        editProfile = view.findViewById(R.id.editprofile);
        String underline = "<u> View and edit profile</u>";
        editProfile.setText(Html.fromHtml(underline));

        lvProfile = view.findViewById(R.id.lvprofile);
        profiles = new ArrayList<>();
        addProfiles();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),editform.class));
            }
        });
        adapter = new profileAdapter(getContext(), profiles);
        lvProfile.setAdapter(adapter);

        return view;
    }

    private void addProfiles() {
        profiles.add(new profile(R.drawable.ic_edit, "Edit Profile", R.drawable.ic_greater));
        profiles.add(new profile(R.drawable.ic_setting, "Setting", R.drawable.ic_greater));
        profiles.add(new profile(R.drawable.ic_notify, "Notifications", R.drawable.ic_greater));
        profiles.add(new profile(R.drawable.ic_orders, "Delivery Orders", R.drawable.ic_greater));
        profiles.add(new profile(R.drawable.ic_help, "Help & Support", R.drawable.ic_greater));
        profiles.add(new profile(R.drawable.ic_logout, "Logout", R.drawable.ic_greater));

    }
}
