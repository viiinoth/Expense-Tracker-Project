package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Delay and start the HomeActivity after the splash screen
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(mainIntent);
            finish();  // Finish MainActivity so the user cannot go back to it
        }, SPLASH_DISPLAY_LENGTH);
    }
}
