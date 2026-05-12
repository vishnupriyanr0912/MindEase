package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail;
    private Button btnRegister;
    private TextView tvLoginRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect);

        AppDatabase db = AppDatabase.getInstance(this);

        btnRegister.setOnClickListener(v -> {

            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if username already exists
            User existingUser = db.userDao().getUser(username);
            if (existingUser != null) {
                Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register
            User newUser = new User(username, password, email);
            db.userDao().register(newUser);

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

            // Redirect to login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        tvLoginRedirect.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );
    }
}
