package com.example.ledclock.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.ledclock.R;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.settings.Settings;

public class SettingsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    /* Activity components */
    private ImageButton mBackBtn;
    private ImageButton mDotsBtn;
    private Button mWifiBtn;
    private Button mNtpBtn;
    private Button mLocationBtn;
    private Button mRefreshBtn;
    private Button mColorBtn;
    private Button mBrightnessBtn;
    private Button mSaveBtn;

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
                if (Settings.getInstance().hasChanges()) {
                    showSaveRebootDialog();
                }
                else {
                    finish();
                }
            }
        });

        // Open popup menu upon clicking dots button
        mDotsBtn = (ImageButton) findViewById(R.id.DotsButton);
        mDotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(SettingsActivity.this, v);
                popup.setOnMenuItemClickListener(SettingsActivity.this);
                popup.inflate(R.menu.settings_menu);
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
                startActivity(i);
            }
        });

        // Close activity upon clicking save button
        mSaveBtn = (Button) findViewById(R.id.SaveButton);
        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (Settings.getInstance().hasChanges()) {
                    showSaveRebootDialog();
                }
                else {
                    finish();
                }
            }
        });
    }

    // Called upon returning to the activity
    @Override
    protected void onStart() {
        super.onStart();
        if (Settings.getInstance().hasChanges()) {
            mSaveBtn.setVisibility(View.VISIBLE);
        } else {
            mSaveBtn.setVisibility(View.INVISIBLE);
        }
    }

    // Dots button popup menu handler
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.DisconnectItem:
                if (Settings.getInstance().hasChanges()) {
                    showSaveRebootDialog();
                }
                else {
                    finish();
                }
                return true;
            default:
                return false;
        }
    }

    // Show save settings and reboot dialog
    private void showSaveRebootDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Save and reboot device?");
        builder.setMessage("Changes have been made to the device settings. Press yes to save the settings to the device. The device will also needs to reboot for the changes to become active.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Commands.saveSettings();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}