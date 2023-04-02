package com.example.androidstepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    private TextView displayUsername, displayEmail;
    private EditText editAge, editWeight, editGender, editHeight;
    private Button saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences preferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String email = preferences.getString("email", "");

        displayUsername = findViewById(R.id.displayUsername);
        displayUsername.setText(username);

        displayEmail = findViewById(R.id.displayEmail);
        displayEmail.setText(email);

        editAge = findViewById(R.id.editAge);
        editWeight = findViewById(R.id.editWeight);
        editGender = findViewById(R.id.editGender);
        editHeight = findViewById(R.id.editHeight);
        saveProfileButton = findViewById(R.id.saveProfileButton);

        saveProfileButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("age", editAge.getText().toString());
            editor.putString("weight", editWeight.getText().toString());
            editor.putString("gender", editGender.getText().toString());
            editor.putString("height", editHeight.getText().toString());
            editor.apply();
            finish();
            startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
        });
    }
}