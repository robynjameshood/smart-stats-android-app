package com.example.smart_stats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button register = findViewById(R.id.register_button);

        authentication = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();

                if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput)) {
                    Toast.makeText(Registration.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(emailInput, passwordInput);
                }
            }
        });
    }

    private void registerUser(String emailInput, String passwordInput) {

        authentication.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registration.this, MainActivity.class));
                }
                else {
                    Toast.makeText(Registration.this, "Registration Failed - Email Address May Already Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}