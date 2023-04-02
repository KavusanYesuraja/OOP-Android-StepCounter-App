package com.example.androidstepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView createNewAccount;
    private EditText inputUsername, inputPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        createNewAccount = findViewById(R.id.createNewAccount);
        btnLogin = findViewById(R.id.btnLogin);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        Database db = new Database(this,"StepCounterApp", null,1);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (username.isEmpty()) {
            showError(inputUsername,"Your username is not valid!");
        }
        else if (password.isEmpty()) {
            showError(inputPassword,"Password is not valid");
        }
        else {
            Database db = new Database(this,"StepCounterApp", null,1);
            if(db.login(username,password)==1){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", db.getEmail(username));
                editor.apply();
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            }else {
                Toast.makeText(this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}