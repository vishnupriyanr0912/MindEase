package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.data.AppDatabase;
import com.example.mentalhealthapp.data.JournalEntry;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;


import java.text.SimpleDateFormat;
import java.util.*;

public class InsightsActivity extends AppCompatActivity {

    private TextView tvMostCommonMood, tvTotalEntries, tvMoodBreakdown, tvStreak;
    private BarChart moodBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

        tvMostCommonMood = findViewById(R.id.tvMostCommonMood);
        tvTotalEntries = findViewById(R.id.tvTotalEntries);
        tvMoodBreakdown = findViewById(R.id.tvMoodBreakdown);
        tvStreak = findViewById(R.id.tvStreak);
        moodBarChart = findViewById(R.id.moodBarChart);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        loadWeeklyMoodStats();
    }

    private void loadWeeklyMoodStats() {

        AppDatabase db = AppDatabase.getInstance(this);
        List<JournalEntry> entries = db.journalDao().getAllEntries();

        if (entries == null || entries.isEmpty()) {
            tvMostCommonMood.setText("No entries this week 😢");
            tvTotalEntries.setText("0 entries");
            tvStreak.setText("Streak: 0 days");
            tvMoodBreakdown.setText("Start journaling to see insights! 🌸");
            return;
        }

        // 🌼 Last 7 days
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeekDate = calendar.getTime();

        HashMap<String, Integer> moodCount = new HashMap<>();
        int streak = calculateStreak(entries);

        for (JournalEntry entry : entries) {
            try {
                Date entryDate = sdf.parse(entry.getDate());
                if (entryDate != null && entryDate.after(lastWeekDate)) {
                    String mood = entry.getMood();
                    moodCount.put(mood, moodCount.getOrDefault(mood, 0) + 1);
                }
            } catch (Exception ignored) {}
        }

        // 🌸 Total entries
        int totalEntries = 0;
        for (int c : moodCount.values()) totalEntries += c;

        // 🌼 Most common mood
        String mostCommonMood = "None";
        if (!moodCount.isEmpty()) {
            mostCommonMood = Collections.max(moodCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        }

        // 🌿 Breakdown
        StringBuilder breakdown = new StringBuilder();
        List<String> moods = Arrays.asList("Happy 😊", "Sad 😢", "Angry 😠", "Anxious 😟", "Calm 😌");

        for (String mood : moods) {
            breakdown.append(mood)
                    .append(": ")
                    .append(moodCount.getOrDefault(mood, 0))
                    .append(" days\n");
        }

        // 🌸 Update text
        tvMostCommonMood.setText("Most common mood: " + mostCommonMood);
        tvTotalEntries.setText("Total entries this week: " + totalEntries);
        tvMoodBreakdown.setText(breakdown.toString());
        tvStreak.setText("Current journaling streak: " + streak + " days");

        setupBarChart(moodCount);
    }

    private int calculateStreak(List<JournalEntry> entries) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        HashSet<String> uniqueDays = new HashSet<>();

        for (JournalEntry entry : entries) {
            uniqueDays.add(entry.getDate());
        }

        int streak = 0;
        Calendar today = Calendar.getInstance();

        while (true) {
            String day = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(today.getTime());

            if (uniqueDays.contains(day)) {
                streak++;
                today.add(Calendar.DAY_OF_YEAR, -1);
            } else {
                break;
            }
        }

        return streak;
    }

    private void setupBarChart(HashMap<String, Integer> moodCount) {

        List<BarEntry> barEntries = new ArrayList<>();
        List<String> moodLabels = Arrays.asList("Happy", "Sad", "Angry", "Anxious", "Calm");

        for (int i = 0; i < moodLabels.size(); i++) {
            String mood = moodLabels.get(i);
            barEntries.add(new BarEntry(i, moodCount.getOrDefault(mood, 0)));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, "Mood Frequency This Week");
        dataSet.setColors(new int[]{
                R.color.light_blue,
                R.color.light_yellow,
                R.color.light_red,
                R.color.light_purple,
                R.color.light_green
        }, this);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        moodBarChart.setData(data);
        moodBarChart.setFitBars(true);

        XAxis xAxis = moodBarChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // 💡 Proper ValueFormatter (no lambda)
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                if (index >= 0 && index < moodLabels.size()) {
                    return moodLabels.get(index);
                } else {
                    return "";
                }
            }
        });

        moodBarChart.getAxisRight().setEnabled(false); // optional: hide right axis for cleaner look
        moodBarChart.getDescription().setEnabled(false); // remove default "Description"
        moodBarChart.invalidate();
    }
}
