package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.MainActivity;
import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 🌸 Initialize UI elements
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // 🌿 Database instance
        AppDatabase db = AppDatabase.getInstance(this);

        // 🌼 Handle login button click
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Check if fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password 💬", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🌷 Verify user from database
            User user = db.userDao().login(username, password);

            if (user != null) {
                getSharedPreferences("user_session", MODE_PRIVATE)
                        .edit()
                        .putString("username", username)
                        .apply();
                Toast.makeText(this, "Welcome back, " + username + " 💖", Toast.LENGTH_SHORT).show();

                // ✅ Step 1: Go to Splash (MainActivity) after successful login
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("loggedInUser", username); // Optional: pass username if needed later
                startActivity(intent);
                finish(); // close LoginActivity
            } else {
                Toast.makeText(this, "Invalid username or password ❌", Toast.LENGTH_SHORT).show();
            }
        });

        // 🌼 Navigate to registration page
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
