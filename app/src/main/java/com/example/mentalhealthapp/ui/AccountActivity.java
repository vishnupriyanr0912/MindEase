package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.User;

public class AccountActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail;
    private ImageButton btnBack;   // IMPORTANT — must be ImageButton since XML uses ImageButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // MATCH XML IDs
        btnBack = findViewById(R.id.btnBack);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);

        // Back button action
        btnBack.setOnClickListener(v -> finish());

        // Get logged in username from SharedPreferences
        String username = getSharedPreferences("user_session", MODE_PRIVATE)
                .getString("username", null);

        if (username != null) {
            User user = AppDatabase.getInstance(this).userDao().getUser(username);

            if (user != null) {
                tvUsername.setText(user.username);
                tvEmail.setText(user.email);
            }
        }
    }
}
