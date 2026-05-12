package com.example.mentalhealthapp.utils;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoodUtils {

    // Returns a color to visually represent the mood
    public static int getMoodColor(String mood) {
        if (mood == null) return Color.GRAY;

        switch (mood.toLowerCase()) {
            case "happy":
                return Color.parseColor("#FFD54F"); // Yellow
            case "content":
                return Color.parseColor("#81C784"); // Green
            case "neutral":
                return Color.parseColor("#90A4AE"); // Grey-blue
            case "sad":
                return Color.parseColor("#64B5F6"); // Blue
            case "angry":
                return Color.parseColor("#E57373"); // Red
            default:
                return Color.LTGRAY;
        }
    }

    // Returns an emoji or short symbol for a given mood
    public static String getMoodEmoji(String mood) {
        if (mood == null) return "❓";

        switch (mood.toLowerCase()) {
            case "happy": return "😊";
            case "content": return "🙂";
            case "neutral": return "😐";
            case "sad": return "😔";
            case "angry": return "😡";
            default: return "❓";
        }
    }

    // Suggests calming activities based on mood
    public static List<String> getRecommendations(String mood) {
        List<String> recs = new ArrayList<>();
        if (mood == null) mood = "neutral";

        switch (mood.toLowerCase()) {
            case "happy":
                recs.add("Share your happiness — call a friend!");
                recs.add("Take a walk and enjoy the sunshine 🌞");
                recs.add("Write down what made you happy today!");
                break;
            case "content":
                recs.add("Enjoy a cup of tea ☕");
                recs.add("Do a 5-minute gratitude journaling");
                recs.add("Listen to relaxing music 🎶");
                break;
            case "neutral":
                recs.add("Try deep breathing for 2 minutes 🧘");
                recs.add("Go for a short stroll outside 🚶‍♀️");
                recs.add("Write one positive thing about today ✍️");
                break;
            case "sad":
                recs.add("Watch something that makes you laugh 😂");
                recs.add("Listen to calm, uplifting music 🎵");
                recs.add("Do gentle stretches or yoga 🧘‍♀️");
                break;
            case "angry":
                recs.add("Take 10 deep breaths 😮‍💨");
                recs.add("Go for a jog or brisk walk 🏃‍♂️");
                recs.add("Write down your feelings and let them go 📝");
                break;
            default:
                recs.add("Take a moment to breathe and relax 🌿");
        }

        return recs;
    }

    // Random recommendation if no mood is specified
    public static String getRandomRecommendation(Context context) {
        String[] generalRecs = {
                "Take a short break and stretch your body 🧘",
                "Drink a glass of water 💧",
                "Write down 3 things you're grateful for ✨",
                "Go for a short walk 🌳",
                "Listen to your favorite song 🎶"
        };
        return generalRecs[new Random().nextInt(generalRecs.length)];
    }
}
