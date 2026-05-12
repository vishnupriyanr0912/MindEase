package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AddEntryActivity extends AppCompatActivity {

    Spinner moodSpinner;
    EditText noteEditText;
    Button saveButton;

    private boolean isEditMode = false;
    private int editId = -1;
    private ArrayAdapter<String> adapter;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        // UI references
        moodSpinner = findViewById(R.id.moodSpinner);
        noteEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);

        db = AppDatabase.getInstance(this);

        // Setup spinner
        String[] moods = {"Happy 😊", "Sad 😢", "Angry 😠", "Anxious 😟", "Calm 😌"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);

        // CHECK EDIT MODE
        if (getIntent().hasExtra("entry_id")) {
            isEditMode = true;
            editId = getIntent().getIntExtra("entry_id", -1);

            loadExistingEntry(editId);
            saveButton.setText("Update Entry");
        }

        saveButton.setOnClickListener(v -> saveEntry());
    }

    private void loadExistingEntry(int id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            JournalEntry entry = db.journalDao().getEntryById(id);

            if (entry != null) {
                runOnUiThread(() -> {
                    noteEditText.setText(entry.getNote());

                    int pos = adapter.getPosition(entry.getMood());
                    if (pos >= 0) moodSpinner.setSelection(pos);
                });
            }
        });
    }

    private void saveEntry() {
        String mood = moodSpinner.getSelectedItem().toString();
        String note = noteEditText.getText().toString();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if (note.isEmpty()) {
            Toast.makeText(this, "Please write a note before saving!", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {

            if (isEditMode && editId != -1) {
                // UPDATE
                JournalEntry updated = new JournalEntry(date, mood, note);
                updated.setId(editId);

                db.journalDao().updateEntry(updated);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Entry Updated!", Toast.LENGTH_SHORT).show();
                    finish();
                });

            } else {
                // INSERT NEW
                JournalEntry entry = new JournalEntry(date, mood, note);
                db.journalDao().insertEntry(entry);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Entry Saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, RecommendationActivity.class);
                    intent.putExtra("mood", mood);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }
}
