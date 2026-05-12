package com.example.mentalhealthapp.data;  // ✅ Make sure this matches your folder path

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This class represents a single journal entry stored in the Room database
@Entity(tableName = "journal_entries")
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String mood;
    private String note;

    // Constructor
    public JournalEntry(String date, String mood, String note) {
        this.date = date;
        this.mood = mood;
        this.note = note;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
