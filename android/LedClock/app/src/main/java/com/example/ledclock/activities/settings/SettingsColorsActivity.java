package com.example.ledclock.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ledclock.activities.edit.EditColorActivity;
import com.example.ledclock.R;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.settings.Settings;

public class SettingsColorsActivity extends AppCompatActivity {
    /* Constants */
    private static final int REQUEST_HOURS = 1;
    private static final int REQUEST_MINUTES = 2;

    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mHours;
    private TextView mHoursValue;
    private LinearLayout mMinutes;
    private TextView mMinutesValue;

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
                Intent intent = new Intent(SettingsColorsActivity.this, EditColorActivity.class);
                intent.putExtra(EditColorActivity.EXTRA_TITLE, getString(R.string.colors_settings_hours_title));
                intent.putExtra(EditColorActivity.EXTRA_DESCRIPTION, getString(R.string.colors_settings_hours_description));
                intent.putExtra(EditColorActivity.EXTRA_VALUE, Settings.getInstance().getmColorHours());
                startActivityForResult(intent, REQUEST_HOURS);
            }
        });
        mHoursValue = (TextView) findViewById(R.id.ColorsHoursValueText);
        mHoursValue.setText(String.format("0x%06X", Settings.getInstance().getmColorHours()));

        // Set minutes color upon clicking linearlayout
        mMinutes = (LinearLayout) findViewById(R.id.ColorsMinutesLayout);
        mMinutes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsColorsActivity.this, EditColorActivity.class);
                intent.putExtra(EditColorActivity.EXTRA_TITLE, getString(R.string.colors_settings_minutes_title));
                intent.putExtra(EditColorActivity.EXTRA_DESCRIPTION, getString(R.string.colors_settings_minutes_description));
                intent.putExtra(EditColorActivity.EXTRA_VALUE, Settings.getInstance().getmColorMinutes());
                startActivityForResult(intent, REQUEST_MINUTES);
            }
        });
        mMinutesValue = (TextView) findViewById(R.id.ColorsMinutesValueText);
        mMinutesValue.setText(String.format("0x%06X", Settings.getInstance().getmColorMinutes()));

        // Broadcast receiver for progress
        IntentFilter filter = new IntentFilter(EditColorActivity.ACTION_PROGRESS);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            Commands.setColorHours(Settings.getInstance().getmColorHours());
            Commands.setColorMinutes(Settings.getInstance().getmColorMinutes());
        }
        else if (requestCode == REQUEST_HOURS) {
            int result = data.getIntExtra(EditColorActivity.EXTRA_RESULT, 0);
            mHoursValue.setText(String.format("0x%06X", result));
            Settings.getInstance().setmColorHours(result);
            Commands.setColorHours(result);
        }
        else if (requestCode == REQUEST_MINUTES) {
            int result = data.getIntExtra(EditColorActivity.EXTRA_RESULT, 0);
            mMinutesValue.setText(String.format("0x%06X", result));
            Settings.getInstance().setmColorMinutes(result);
            Commands.setColorMinutes(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra(EditColorActivity.EXTRA_TITLE);
            int value = intent.getIntExtra(EditColorActivity.EXTRA_VALUE, 0);
            if (title.equals(getString(R.string.colors_settings_hours_title))) {
                Commands.setColorHours(value);
            } else if (title.equals(getString(R.string.colors_settings_minutes_title))) {
                Commands.setColorMinutes(value);
            }
        }
    };
}