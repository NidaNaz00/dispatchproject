package com.example.dispatch;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;
            if (itemId == R.id.nav_home) {
                selectedFragment = new home_home();
            } else if (itemId == R.id.nav_item) {
                selectedFragment = new home_item();
            } else if (itemId == R.id.nav_truck) {
                selectedFragment = new home_truck();
            }
            else if (itemId==R.id.nav_dispatch)
            {
                selectedFragment=new home_dispatch();
            }
            else if (itemId == R.id.nav_account) {
                selectedFragment = new home_account();
            } else {
                return false;
            }
            loadFragment(selectedFragment);
            return true;
        });

        if (savedInstanceState == null) {
            loadFragment(new home_home());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}