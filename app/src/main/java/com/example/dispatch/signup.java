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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signup extends AppCompatActivity {
    private TextView btnlogin1;
    private EditText name;
    private EditText eid;
    private EditText pass;
    private EditText cp;
    private Button sign;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        btnlogin1=findViewById(R.id.btnlogin1);
        name=findViewById(R.id.name);
        eid=findViewById(R.id.eid);
        pass=findViewById(R.id.pass);
        cp=findViewById(R.id.cp);
        sign=findViewById(R.id.sign);
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eid.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String Name = name.getText().toString().trim();
                String Confirmpassword = cp.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)|| TextUtils.isEmpty(Name)|| TextUtils.isEmpty(Confirmpassword)) {
                    showError3();
                } else if (password.length()<8) {
                    showError4();
                } else if (!password.equals(Confirmpassword)) {
                    showError5();
                }
                else {
                    Intent intent = new Intent(signup.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }

            private void showError3() {
                Toast.makeText(signup.this,"Something is missing",Toast.LENGTH_SHORT).show();
            }
            private void showError4() {
                Toast.makeText(signup.this,"Password atleast 8 words",Toast.LENGTH_SHORT).show();
            }
            private void showError5() {
                Toast.makeText(signup.this,"Password don't match",Toast.LENGTH_SHORT).show();
            }
        });
        btnlogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
    }
}