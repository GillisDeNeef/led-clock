package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsBrightnessActivity extends AppCompatActivity {
    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mDay;
    private LinearLayout mNight;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_brightness);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.BrightnessBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set brightness day upon clicking linearlayout
        mDay = (LinearLayout) findViewById(R.id.BrightnessDayLayout);
        mDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        // Set brightness night upon clicking linearlayout
        mNight = (LinearLayout) findViewById(R.id.BrightnessNightLayout);
        mNight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
    }
}