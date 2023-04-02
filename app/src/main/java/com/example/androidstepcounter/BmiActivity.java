package com.example.androidstepcounter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BmiActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mHeightEditText;
    private EditText mWeightEditText;
    private TextView mResultTextView;
    private Button mCalculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mHeightEditText = findViewById(R.id.height_edit_text);
        mWeightEditText = findViewById(R.id.weight_edit_text);
        mResultTextView = findViewById(R.id.result_text_view);
        mCalculateButton = findViewById(R.id.calculate_button);
        mCalculateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mCalculateButton) {
            String heightStr = mHeightEditText.getText().toString();
            String weightStr = mWeightEditText.getText().toString();

            if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr)) {
                Toast.makeText(this, "Please enter your height and weight.", Toast.LENGTH_SHORT).show();
                return;
            }

            float heightInCm = Float.parseFloat(heightStr);
            float weight = Float.parseFloat(weightStr);

            if (heightInCm <= 0 || weight <= 0) {
                Toast.makeText(this, "Invalid height or weight value.", Toast.LENGTH_SHORT).show();
                return;
            }

            float heightInM = heightInCm / 100f;
            float bmi = weight / (heightInM * heightInM);
            String result = String.format("Your BMI is %.2f", bmi);
            mResultTextView.setText(result);
        }
    }
}