package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                Intent intent = new Intent(SettingsNtpActivity.this, ResultStringActivity.class);
                intent.putExtra(ResultStringActivity.EXTRA_TITLE, "NTP");
                intent.putExtra(ResultStringActivity.EXTRA_DESCRIPTION, "Enter url of ntp server.");
                intent.putExtra(ResultStringActivity.EXTRA_VALUE, Settings.getInstance().getmNtpServer());
                startActivityForResult(intent, REQUEST_NTP);
            }
        });
        mNtpValue = (TextView) findViewById(R.id.NtpServerDescriptionText);
        mNtpValue.setText(Settings.getInstance().getmNtpServer());

        // Set GMT upon clicking linearlayout
        mGmt = (LinearLayout) findViewById(R.id.NtpGmtLayout);
        mGmt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsNtpActivity.this, ResultIntActivity.class);
                intent.putExtra(ResultIntActivity.EXTRA_TITLE, "GMT");
                intent.putExtra(ResultIntActivity.EXTRA_DESCRIPTION, "Enter gmt offset for your location (minutes).");
                intent.putExtra(ResultIntActivity.EXTRA_VALUE, Settings.getInstance().getmGmtOffset());
                startActivityForResult(intent, REQUEST_GMT);
            }
        });
        mGmtValue = (TextView) findViewById(R.id.NtpGmtDescriptionText);
        mGmtValue.setText(String.valueOf(Settings.getInstance().getmGmtOffset()));

        // Set daylight upon clicking linearlayout
        mDaylight = (LinearLayout) findViewById(R.id.NtpDaylightLayout);
        mDaylight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsNtpActivity.this, ResultIntActivity.class);
                intent.putExtra(ResultIntActivity.EXTRA_TITLE, "Daylight");
                intent.putExtra(ResultIntActivity.EXTRA_DESCRIPTION, "Enter daylight saving time for your location (minutes).");
                intent.putExtra(ResultIntActivity.EXTRA_VALUE, Settings.getInstance().getmDaylight());
                startActivityForResult(intent, REQUEST_DAYLIGHT);
            }
        });
        mDaylightValue = (TextView) findViewById(R.id.NtpDaylightDescriptionText);
        mDaylightValue.setText(String.valueOf(Settings.getInstance().getmDaylight()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
        }
        else if (requestCode == REQUEST_NTP) {
            String result = data.getStringExtra(ResultStringActivity.EXTRA_RESULT);
            mNtpValue.setText(result);
            Settings.getInstance().setmNtpServer(result);
        }
        else if (requestCode == REQUEST_GMT) {
            int result = data.getIntExtra(ResultIntActivity.EXTRA_RESULT, 0);
            mGmtValue.setText(String.valueOf(result));
            Settings.getInstance().setmGmtOffset(result);
        }
        else if (requestCode == REQUEST_DAYLIGHT) {
            int result = data.getIntExtra(ResultIntActivity.EXTRA_RESULT, 0);
            mDaylightValue.setText(String.valueOf(result));
            Settings.getInstance().setmDaylight(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}