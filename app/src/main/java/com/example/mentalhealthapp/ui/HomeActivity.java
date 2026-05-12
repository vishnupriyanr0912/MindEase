package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mentalhealthapp.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Button btnAddEntry, btnRecommendations, btnViewEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Make sure this exists!

        // 🌸 Drawer initialization
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        if (drawerLayout == null || navigationView == null) {
            Toast.makeText(this, "Error loading menu layout!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🌿 Set navigation listener
        navigationView.setNavigationItemSelectedListener(this);

        // 🌼 Menu icon click → open drawer
        View menuButton = findViewById(R.id.btnMenu);
        if (menuButton != null) {
            menuButton.setOnClickListener(v ->
                    drawerLayout.openDrawer(GravityCompat.START)
            );
        }

        // 🌸 HOME PAGE BUTTONS
        btnAddEntry = findViewById(R.id.btnAddEntry);
        btnRecommendations = findViewById(R.id.btnRecommendations);
        btnViewEntries = findViewById(R.id.btnViewEntries);

        btnAddEntry.setOnClickListener(v ->
                startActivity(new Intent(this, AddEntryActivity.class))
        );

        btnRecommendations.setOnClickListener(v ->
                startActivity(new Intent(this, RecommendationActivity.class))
        );

        btnViewEntries.setOnClickListener(v ->
                startActivity(new Intent(this, ViewJournalActivity.class))
        );
    }

    // 🌿 Navigation drawer item clicks
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_account) {
            startActivity(new Intent(this, AccountActivity.class));

        } else if (id == R.id.nav_mood_stats) {
            startActivity(new Intent(this, InsightsActivity.class));

        } else if (id == R.id.nav_reminder) {
            Toast.makeText(this, "Daily reminders coming soon ⏰", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about) {
            Toast.makeText(this, "MindEase — Your safe space to reflect 💖", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_logout) {

            Toast.makeText(this, "Signed out successfully 👋", Toast.LENGTH_SHORT).show();

            // Clear login session
            getSharedPreferences("user_session", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Close drawer when pressing back
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
