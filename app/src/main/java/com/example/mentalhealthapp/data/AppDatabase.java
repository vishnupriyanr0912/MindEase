package com.example.mentalhealthapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {JournalEntry.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // DAOs
    public abstract JournalDao journalDao();
    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "mental_health_db")
                    .fallbackToDestructiveMigration() // resets DB if version changes
                    .allowMainThreadQueries() // (simplifies for small apps)
                    .build();
        }
        return instance;
    }
}
