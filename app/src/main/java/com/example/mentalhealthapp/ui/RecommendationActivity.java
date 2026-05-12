package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.R;

import java.util.Random;

public class RecommendationActivity extends AppCompatActivity {

    private TextView recommendationText;
    private TextView backButton;

    private final String[] randomActivities = {
            "🌿 Take a 5-minute nature walk",
            "🎶 Listen to one song you love",
            "📚 Read 2 pages of a book",
            "💧 Drink a glass of water",
            "🎨 Doodle something fun",
            "📸 Take a picture of something pretty",
            "🤸 Do a quick stretch",
            "🍎 Eat a healthy snack",
            "🧘 Try a 1-minute breathing exercise",
            "🧼 Clean one small thing near you",
            "✨ Organize one app on your phone",
            "🎮 Play a mobile game for 5 minutes",
            "📺 Watch a short funny clip",
            "💭 Write one thought stuck in your mind",
            "🌈 Sit near a window and enjoy sunlight"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recommendationText = findViewById(R.id.recommendationText);
        backButton = findViewById(R.id.backButton);

        // Get mood from AddEntryActivity
        Intent intent = getIntent();
        String mood = intent.getStringExtra("mood");

        if (mood == null || mood.trim().isEmpty()) {
            // 🌟 OPENED FROM HOME → GIVE RANDOM ACTIVITY
            recommendationText.setText(getRandomActivity());
        } else {
            // OPENED FROM ADD ENTRY → SHOW MOOD-BASED ONE
            recommendationText.setText(getMoodBasedRecommendation(mood));
        }

        backButton.setOnClickListener(v -> finish());
    }

    // 🌟 Random Suggestion Generator
    private String getRandomActivity() {
        Random random = new Random();
        return randomActivities[random.nextInt(randomActivities.length)];
    }

    // 🌟 Mood-Based Recommendation
    private String getMoodBasedRecommendation(String mood) {
        Random random = new Random();

        switch (mood) {
            case "Sad 😢":
                String[] sadSuggestions = {
                        "Listen to your favorite uplifting song 🎶",
                        "Write down three things you're grateful for 💖",
                        "Take a short walk and get sunlight 🌞",
                        "Watch a feel-good video 🎬",
                        "Call someone you trust ☎️"
                };
                return sadSuggestions[random.nextInt(sadSuggestions.length)];

            case "Angry 😠":
                String[] angrySuggestions = {
                        "Take a few deep breaths — count to ten 🧘‍♀️",
                        "Do some quick exercise 🏃‍♂️",
                        "Write out your frustration then tear it 📝",
                        "Use a stress ball 🤲",
                        "Drink water and give yourself space 💧"
                };
                return angrySuggestions[random.nextInt(angrySuggestions.length)];

            case "Anxious 😟":
                String[] anxiousSuggestions = {
                        "Try the 4-7-8 breathing technique 🌬️",
                        "Listen to calming nature sounds 🎧",
                        "Do 5 minutes of mindfulness 🧘",
                        "Focus on one good thing today 🌈",
                        "Journal your worries ✍️"
                };
                return anxiousSuggestions[random.nextInt(anxiousSuggestions.length)];

            case "Calm 😌":
                String[] calmSuggestions = {
                        "Stretch gently for a moment 🧘",
                        "Read a few pages of something peaceful 📖",
                        "Enjoy nature 🌿",
                        "Journal your calm thoughts ☀️",
                        "Do something kind for yourself 💫"
                };
                return calmSuggestions[random.nextInt(calmSuggestions.length)];

            case "Happy 😊":
                String[] happySuggestions = {
                        "Share your good vibes with someone 💬",
                        "Dance to a fun song 💃",
                        "Take a happy selfie 📸",
                        "Write why you feel happy 📝",
                        "Spread joy by complimenting someone 🌻"
                };
                return happySuggestions[random.nextInt(happySuggestions.length)];

            default:
                return getRandomActivity(); // fallback to random fun activity
        }
    }
}
