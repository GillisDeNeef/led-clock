package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsColorsActivity extends AppCompatActivity {
    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mHours;
    private LinearLayout mMinutes;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_colors);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.ColorsBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set hours color upon clicking linearlayout
        mHours = (LinearLayout) findViewById(R.id.ColorsHoursLayout);
        mHours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        // Set minutes color upon clicking linearlayout
        mMinutes = (LinearLayout) findViewById(R.id.ColorsMinutesLayout);
        mMinutes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
    }
}