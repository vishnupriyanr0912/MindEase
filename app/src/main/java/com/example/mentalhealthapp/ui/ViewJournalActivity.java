package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.JournalEntry;

import java.util.List;

public class ViewJournalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private Button btnBack;
    private JournalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal);

        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerView);
        emptyMessage = findViewById(R.id.emptyMessage);
        btnBack = findViewById(R.id.btnBack);

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(false);            // Allow dynamic card sizes
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setNestedScrollingEnabled(false);  // Prevent scroll conflicts

        // Load entries from Room database
        List<JournalEntry> entries = AppDatabase.getInstance(this)
                .journalDao()
                .getAllEntries();

        Log.d("ViewJournalActivity", "Entries count = " + entries.size());

        // Show entries or empty message
        if (entries == null || entries.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyMessage.setVisibility(View.GONE);

            adapter = new JournalAdapter(ViewJournalActivity.this, entries);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            // Automatically scroll to the latest entry
            recyclerView.post(() -> recyclerView.scrollToPosition(entries.size() - 1));
        }

        // Back button action
        btnBack.setOnClickListener(v -> finish());
    }
}