package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsRefreshActivity extends AppCompatActivity {
    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mRate;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_refresh);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.RefreshBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set refresh rate upon clicking linearlayout
        mRate = (LinearLayout) findViewById(R.id.RefreshRateLayout);
        mRate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
    }
}