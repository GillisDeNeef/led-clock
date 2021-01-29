package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsWifiActivity extends AppCompatActivity {
    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mSsid;
    private LinearLayout mPassword;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_wifi);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.WifiBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set SSID upon clicking linearlayout
        mSsid = (LinearLayout) findViewById(R.id.WifiSsidLayout);
        mSsid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        // Set password upon clicking linearlayout
        mPassword = (LinearLayout) findViewById(R.id.WifiPasswordLayout);
        mPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
    }
}