package com.example.dispatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {
    private EditText emailField;
    private EditText passwordField;
    private Button btnlogin;
    private TextView btnsign;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        btnlogin = findViewById(R.id.btnlogin);
        btnsign = findViewById(R.id.btnsign);
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    showError2();
                }
                String storedEmail = sharedPreferences.getString("email", "");
                String storedPassword = sharedPreferences.getString("password", "");
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    Intent intent = new Intent(login.this, home.class);
                    startActivity(intent);
                } else {
                    showError1();
                }
            }


            private void onSignUpClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
            private void showError1() {
                Toast.makeText(login.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
            }
            private void showError2() {
                Toast.makeText(login.this,"Something is missing",Toast.LENGTH_SHORT).show();
            }
        });
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

    }
}