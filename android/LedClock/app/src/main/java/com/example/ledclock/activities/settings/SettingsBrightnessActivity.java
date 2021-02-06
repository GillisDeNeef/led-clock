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

import com.example.ledclock.activities.edit.EditPercentageActivity;
import com.example.ledclock.R;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.settings.Settings;

public class SettingsBrightnessActivity extends AppCompatActivity {
    /* Constants */
    private static final int REQUEST_DAY = 1;
    private static final int REQUEST_NIGHT = 2;

    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mDay;
    private TextView mDayValue;
    private LinearLayout mNight;
    private TextView mNightValue;

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
                Intent intent = new Intent(SettingsBrightnessActivity.this, EditPercentageActivity.class);
                intent.putExtra(EditPercentageActivity.EXTRA_TITLE, getString(R.string.brightness_settings_day_title));
                intent.putExtra(EditPercentageActivity.EXTRA_DESCRIPTION, getString(R.string.brightness_settings_day_description));
                intent.putExtra(EditPercentageActivity.EXTRA_VALUE, Settings.getInstance().getmBrightnessDay());
                startActivityForResult(intent, REQUEST_DAY);
            }
        });
        mDayValue = (TextView) findViewById(R.id.BrightnessDayValueText);
        mDayValue.setText(String.valueOf(Settings.getInstance().getmBrightnessDay()));

        // Set brightness night upon clicking linearlayout
        mNight = (LinearLayout) findViewById(R.id.BrightnessNightLayout);
        mNight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsBrightnessActivity.this, EditPercentageActivity.class);
                intent.putExtra(EditPercentageActivity.EXTRA_TITLE, getString(R.string.brightness_settings_night_title));
                intent.putExtra(EditPercentageActivity.EXTRA_DESCRIPTION, getString(R.string.brightness_settings_night_description));
                intent.putExtra(EditPercentageActivity.EXTRA_VALUE, Settings.getInstance().getmBrightnessNight());
                startActivityForResult(intent, REQUEST_NIGHT);
            }
        });
        mNightValue = (TextView) findViewById(R.id.BrightnessNightValueText);
        mNightValue.setText(String.valueOf(Settings.getInstance().getmBrightnessNight()));

        // Broadcast receiver for progress
        IntentFilter filter = new IntentFilter(EditPercentageActivity.ACTION_PROGRESS);
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
            Commands.setBrightnessDay(Settings.getInstance().getmBrightnessDay());
            Commands.setBrightnessNight(Settings.getInstance().getmBrightnessNight());
        }
        else if (requestCode == REQUEST_DAY) {
            int result = data.getIntExtra(EditPercentageActivity.EXTRA_RESULT, 0);
            mDayValue.setText(String.valueOf(result));
            Settings.getInstance().setmBrightnessDay(result);
            Commands.setBrightnessDay(result);
        }
        else if (requestCode == REQUEST_NIGHT) {
            int result = data.getIntExtra(EditPercentageActivity.EXTRA_RESULT, 0);
            mNightValue.setText(String.valueOf(result));
            Settings.getInstance().setmBrightnessNight(result);
            Commands.setBrightnessNight(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra(EditPercentageActivity.EXTRA_TITLE);
            int value = intent.getIntExtra(EditPercentageActivity.EXTRA_VALUE, 0);
            if (title.equals(getString(R.string.brightness_settings_day_title))) {
                Commands.setBrightnessDay(value);
            } else if (title.equals(getString(R.string.brightness_settings_night_title))) {
                Commands.setBrightnessNight(value);
            }
        }
    };
}