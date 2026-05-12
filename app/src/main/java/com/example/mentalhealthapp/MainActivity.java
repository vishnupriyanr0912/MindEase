package com.example.mentalhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthapp.ui.HomeActivity;
import com.example.mentalhealthapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Your splash screen

        Log.d(TAG, "Splash screen started ✅");

        // Delay for 2 seconds and go to HomeActivity
        new Handler().postDelayed(() -> {
            try {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                Log.d(TAG, "Navigated to HomeActivity ✅");
            } catch (Exception e) {
                Log.e(TAG, "Error starting HomeActivity: ", e);
            }
        }, 2000);
    }
}
