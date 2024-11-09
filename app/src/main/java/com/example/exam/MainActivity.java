package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);  // Make sure your splash_screen.xml is set

        // Delaying the transition to HomeActivity by 2 seconds
        new Handler().postDelayed(() -> {
            // Start HomeActivity and finish the current SplashActivity
            Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(mainIntent);
            finish();  // Finish the current activity so the user can't go back to the splash screen
        }, SPLASH_DISPLAY_LENGTH);
    }
}

