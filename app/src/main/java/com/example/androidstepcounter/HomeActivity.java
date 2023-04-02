package com.example.androidstepcounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private Sensor accelerometerSensor;
    private TextView stepCountTextView;
    private ProgressBar stepProgressBar;
    private ImageView pedometerImageView;
    private Button resetButton, checkBmiButton, checkProfileButton;
    private int stepCount = 0;
    private int stepGoal = 10000;
    private float[] accelerometerValues = new float[3];
    private float[] magnetometerValues = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        stepCountTextView = findViewById(R.id.step_counter_text);
        stepProgressBar = findViewById(R.id.step_progress_bar);
        pedometerImageView = findViewById(R.id.pedometer_image);

        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepCount = 0;
                stepCountTextView.setText("0");
                stepProgressBar.setProgress(0);
            }
        });

        checkBmiButton = findViewById(R.id.checkBmiButton);
        checkBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BmiActivity.class));
            }
        });

        checkProfileButton = findViewById(R.id.checkProfileButton);
        checkProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int steps = (int) sensorEvent.values[0];
            if (stepCount == 0) {
                stepCount = steps;
            }
            int delta = steps - stepCount;
            stepCount = steps;
            updateStepCount(delta);
        }else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerValues, 0, 3);
            float totalAcceleration = calculateTotalAcceleration(accelerometerValues);
            if (totalAcceleration > 20) {
                stepCount++;
                updateStepCount(1);
            }
        }
    }

    private float calculateTotalAcceleration(float[] values) {
        float x = values[0];
        float y = values[1];
        float z = values[2];
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    private void updateStepCount(int delta) {
        stepCount += delta;
        stepCountTextView.setText(Integer.toString(stepCount));
        int progress = (int) (((float) stepCount / stepGoal) * 100);
        stepProgressBar.setProgress(progress);
        if (progress >= 100) {
            pedometerImageView.setImageResource(R.drawable.ic_pedometer_goal_achieved);
            pedometerImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pedometerImageView.setAdjustViewBounds(true);
            pedometerImageView.setMaxHeight(100);
            pedometerImageView.setMaxWidth(100);
        } else {
            pedometerImageView.setImageResource(R.drawable.ic_pedometer);
            pedometerImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pedometerImageView.setAdjustViewBounds(true);
            pedometerImageView.setMaxHeight(100);
            pedometerImageView.setMaxWidth(100);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}