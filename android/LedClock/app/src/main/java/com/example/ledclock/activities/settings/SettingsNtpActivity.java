package com.example.ledclock.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ledclock.activities.edit.EditIntActivity;
import com.example.ledclock.activities.edit.EditStringActivity;
import com.example.ledclock.R;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.settings.Settings;

public class SettingsNtpActivity extends AppCompatActivity {
    /* Constants */
    private static final int REQUEST_NTP = 1;
    private static final int REQUEST_GMT = 2;
    private static final int REQUEST_DAYLIGHT = 3;

    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mNtp;
    private TextView mNtpValue;
    private LinearLayout mGmt;
    private TextView mGmtValue;
    private LinearLayout mDaylight;
    private TextView mDaylightValue;

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
                Intent intent = new Intent(SettingsNtpActivity.this, EditStringActivity.class);
                intent.putExtra(EditStringActivity.EXTRA_TITLE, getString(R.string.ntp_settings_server_title));
                intent.putExtra(EditStringActivity.EXTRA_DESCRIPTION, getString(R.string.ntp_settings_server_description));
                intent.putExtra(EditStringActivity.EXTRA_VALUE, Settings.getInstance().getmNtpServer());
                startActivityForResult(intent, REQUEST_NTP);
            }
        });
        mNtpValue = (TextView) findViewById(R.id.NtpServerValueText);
        mNtpValue.setText(Settings.getInstance().getmNtpServer());

        // Set GMT upon clicking linearlayout
        mGmt = (LinearLayout) findViewById(R.id.NtpGmtLayout);
        mGmt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsNtpActivity.this, EditIntActivity.class);
                intent.putExtra(EditIntActivity.EXTRA_TITLE, getString(R.string.ntp_settings_gmt_title));
                intent.putExtra(EditIntActivity.EXTRA_DESCRIPTION, getString(R.string.ntp_settings_gmt_description));
                intent.putExtra(EditIntActivity.EXTRA_VALUE, Settings.getInstance().getmGmtOffset());
                startActivityForResult(intent, REQUEST_GMT);
            }
        });
        mGmtValue = (TextView) findViewById(R.id.NtpGmtValueText);
        mGmtValue.setText(String.valueOf(Settings.getInstance().getmGmtOffset()));

        // Set daylight upon clicking linearlayout
        mDaylight = (LinearLayout) findViewById(R.id.NtpDaylightLayout);
        mDaylight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsNtpActivity.this, EditIntActivity.class);
                intent.putExtra(EditIntActivity.EXTRA_TITLE, getString(R.string.ntp_settings_daylight_title));
                intent.putExtra(EditIntActivity.EXTRA_DESCRIPTION, getString(R.string.ntp_settings_daylight_description));
                intent.putExtra(EditIntActivity.EXTRA_VALUE, Settings.getInstance().getmDaylight());
                startActivityForResult(intent, REQUEST_DAYLIGHT);
            }
        });
        mDaylightValue = (TextView) findViewById(R.id.NtpDaylightValueText);
        mDaylightValue.setText(String.valueOf(Settings.getInstance().getmDaylight()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
        }
        else if (requestCode == REQUEST_NTP) {
            String result = data.getStringExtra(EditStringActivity.EXTRA_RESULT);
            mNtpValue.setText(result);
            Settings.getInstance().setmNtpServer(result);
            Commands.setNtpServer(result);
        }
        else if (requestCode == REQUEST_GMT) {
            int result = data.getIntExtra(EditIntActivity.EXTRA_RESULT, 0);
            mGmtValue.setText(String.valueOf(result));
            Settings.getInstance().setmGmtOffset(result);
            Commands.setGmtOffset(result);
        }
        else if (requestCode == REQUEST_DAYLIGHT) {
            int result = data.getIntExtra(EditIntActivity.EXTRA_RESULT, 0);
            mDaylightValue.setText(String.valueOf(result));
            Settings.getInstance().setmDaylight(result);
            Commands.setDaylightTime(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}