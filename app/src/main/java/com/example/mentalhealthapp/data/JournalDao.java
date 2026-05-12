package com.example.mentalhealthapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JournalDao {

    @Insert
    void insertEntry(JournalEntry entry);

    @Query("SELECT * FROM journal_entries ORDER BY id DESC")
    List<JournalEntry> getAllEntries();

    @Update
    void updateEntry(JournalEntry entry);

    @Delete
    void delete(JournalEntry entry);

    // ✔ REQUIRED FOR EDIT MODE
    @Query("SELECT * FROM journal_entries WHERE id = :id LIMIT 1")
    JournalEntry getEntryById(int id);
}
