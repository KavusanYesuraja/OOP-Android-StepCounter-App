package com.example.androidstepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView displayUsername, displayEmail, displayAge, displayWeight, displayGender, displayHeight;
    private Button editProfileButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadProfileData();

        editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
            }
        });
    }
    private void loadProfileData() {
        SharedPreferences preferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String email = preferences.getString("email", "");
        String age = preferences.getString("age", "");
        String weight = preferences.getString("weight", "");
        String gender = preferences.getString("gender", "");
        String height = preferences.getString("height", "");

        displayUsername = findViewById(R.id.displayUsername);
        displayUsername.setText(username);
        displayEmail = findViewById(R.id.displayEmail);
        displayEmail.setText(email);
        displayAge = findViewById(R.id.displayAge);
        displayAge.setText(age);
        displayWeight = findViewById(R.id.displayWeight);
        displayWeight.setText(weight);
        displayGender = findViewById(R.id.displayGender);
        displayGender.setText(gender);
        displayHeight = findViewById(R.id.displayHeight);
        displayHeight.setText(height);
    }
}