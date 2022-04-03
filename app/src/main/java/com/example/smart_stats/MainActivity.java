package com.example.smart_stats;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;
    private Button login;

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Registration.class));
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        authentication = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput)) {
                Toast.makeText(MainActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
            }
            else {
                loginUser(emailInput, passwordInput);
            }
        });
    }

    private void loginUser(String emailInput, String passwordInput) {
        authentication.signInWithEmailAndPassword(emailInput, passwordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Homepage.class));
                finish();
            }
        });

    }
}