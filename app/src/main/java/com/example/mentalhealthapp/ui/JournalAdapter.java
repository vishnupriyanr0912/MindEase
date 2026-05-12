package com.example.mentalhealthapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.JournalEntry;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private List<JournalEntry> entries;
    private Context context;

    public JournalAdapter(Context context, List<JournalEntry> entries) {
        this.context = context;
        this.entries = entries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);

        holder.moodText.setText(entry.getMood());
        holder.dateText.setText(entry.getDate());
        holder.noteText.setText(entry.getNote());

        // 🌈 APPLY PASTEL COLORS BASED ON MOOD
        setMoodColor(holder.cardView, entry.getMood());

        // Popup Menu for Edit/Delete
        holder.btnOptions.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(context, holder.btnOptions);
            popup.inflate(R.menu.journal_menu);

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    editEntry(entry);
                    return true;
                }
                else if (item.getItemId() == R.id.menu_delete) {
                    deleteEntry(entry, holder.getAdapterPosition());
                    return true;
                }
                return false;
            });

            popup.show();
        });
    }

    // 🌈 Mood → Pastel Color Mapper
    private void setMoodColor(CardView cardView, String mood) {

        if (mood.contains("Happy")) {
            cardView.setCardBackgroundColor(0xFFFFF7C2); // yellow pastel

        } else if (mood.contains("Sad")) {
            cardView.setCardBackgroundColor(0xFFCDE7FF); // soft blue

        } else if (mood.contains("Angry")) {
            cardView.setCardBackgroundColor(0xFFFFDBDB); // soft red/pink

        } else if (mood.contains("Anxious")) {
            cardView.setCardBackgroundColor(0xFFE5D7FF); // lavender

        } else if (mood.contains("Calm")) {
            cardView.setCardBackgroundColor(0xFFDFFFE2); // mint green

        } else {
            cardView.setCardBackgroundColor(0xFFFFFFFF); // fallback white
        }
    }

    private void editEntry(JournalEntry entry) {
        Intent intent = new Intent(context, AddEntryActivity.class);

        // IMPORTANT: use entry_id key (fixes your update issue)
        intent.putExtra("entry_id", entry.getId());

        context.startActivity(intent);
    }

    private void deleteEntry(JournalEntry entry, int position) {
        AppDatabase db = AppDatabase.getInstance(context);
        db.journalDao().delete(entry);

        entries.remove(position);
        notifyItemRemoved(position);

        Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView moodText, dateText, noteText;
        ImageButton btnOptions;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewJournal);
            moodText = itemView.findViewById(R.id.moodTextView);
            dateText = itemView.findViewById(R.id.dateTextView);
            noteText = itemView.findViewById(R.id.noteTextView);
            btnOptions = itemView.findViewById(R.id.btnOptions);
        }
    }
}
