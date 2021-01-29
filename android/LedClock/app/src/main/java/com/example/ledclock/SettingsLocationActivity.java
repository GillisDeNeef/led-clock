package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsLocationActivity extends AppCompatActivity {
    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mLongitude;
    private LinearLayout mLatitude;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_location);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.LocationBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set longitude upon clicking linearlayout
        mLongitude = (LinearLayout) findViewById(R.id.LocationLongitudeLayout);
        mLongitude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        // Set latitude upon clicking linearlayout
        mLatitude = (LinearLayout) findViewById(R.id.LocationLatitudeLayout);
        mLatitude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
    }
}