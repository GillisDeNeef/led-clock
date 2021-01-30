package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.ledclock.settings.Settings;

public class SettingsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    /* Constants */
    public static final String EXTRA_DEVICE = "bluetooth_device";

    /* Activity components */
    private ImageButton mBackBtn;
    private ImageButton mDotsBtn;
    private Button mWifiBtn;
    private Button mNtpBtn;
    private Button mLocationBtn;
    private Button mRefreshBtn;
    private Button mColorBtn;
    private Button mBrightnessBtn;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.BackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Open popup menu upon clicking dots button
        mDotsBtn = (ImageButton) findViewById(R.id.DotsButton);
        mDotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(SettingsActivity.this, v);
                popup.setOnMenuItemClickListener(SettingsActivity.this);
                popup.inflate(R.menu.dots_menu);
                popup.show();
            }
        });

        // Open wifi settings activity
        mWifiBtn = (Button) findViewById(R.id.WifiButton);
        mWifiBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SettingsActivity.this, SettingsWifiActivity.class);
                i.putExtra(EXTRA_DEVICE, "");
                startActivity(i);
            }
        });

        // Open ntp settings activity
        mNtpBtn = (Button) findViewById(R.id.NtpButton);
        mNtpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SettingsActivity.this, SettingsNtpActivity.class);
                i.putExtra(EXTRA_DEVICE, "");
                startActivity(i);
            }
        });

        // Open location settings activity
        mLocationBtn = (Button) findViewById(R.id.LocationButton);
        mLocationBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SettingsActivity.this, SettingsLocationActivity.class);
                i.putExtra(EXTRA_DEVICE, "");
                startActivity(i);
            }
        });

        // Open refresh rate settings activity
        mRefreshBtn = (Button) findViewById(R.id.RefreshButton);
        mRefreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SettingsActivity.this, SettingsRefreshActivity.class);
                i.putExtra(EXTRA_DEVICE, "");
                startActivity(i);
            }
        });

        // Open colors settings activity
        mColorBtn = (Button) findViewById(R.id.ColorsButton);
        mColorBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SettingsActivity.this, SettingsColorsActivity.class);
                i.putExtra(EXTRA_DEVICE, "");
                startActivity(i);
            }
        });

        // Open brightness settings activity
        mBrightnessBtn = (Button) findViewById(R.id.BrightnessButton);
        mBrightnessBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(SettingsActivity.this, SettingsBrightnessActivity.class);
                i.putExtra(EXTRA_DEVICE, "");
                startActivity(i);
            }
        });
    }

    // Dots button popup menu handler
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.DisconnectItem:
                finish();
                return true;
            default:
                return false;
        }
    }
}