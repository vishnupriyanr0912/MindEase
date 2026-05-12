package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.User;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvEmail;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnBack = findViewById(R.id.btnBack);

        // get current logged in username
        String username = getSharedPreferences("user_session", MODE_PRIVATE)
                .getString("username", "Unknown");

        // fetch user from DB
        User user = AppDatabase.getInstance(this).userDao().getUser(username);

        if (user != null) {
            tvUsername.setText(user.username);
            tvEmail.setText(user.email);
        }

        btnBack.setOnClickListener(v -> finish());
    }
}
