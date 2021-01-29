package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsNtpActivity extends AppCompatActivity {
    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mNtp;
    private LinearLayout mGmt;
    private LinearLayout mDaylight;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_ntp);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.NtpBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set NTP upon clicking linearlayout
        mNtp = (LinearLayout) findViewById(R.id.NtpServerLayout);
        mNtp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        // Set GMT upon clicking linearlayout
        mGmt = (LinearLayout) findViewById(R.id.NtpGmtLayout);
        mGmt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        // Set daylight upon clicking linearlayout
        mDaylight = (LinearLayout) findViewById(R.id.NtpDaylightLayout);
        mDaylight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
    }
}