package com.example.testtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        setContentView(R.layout.activity_splash);

        Intent iintent = new Intent(this, MainActivity.class);
        startActivity(iintent);

        finish();
    }
}
