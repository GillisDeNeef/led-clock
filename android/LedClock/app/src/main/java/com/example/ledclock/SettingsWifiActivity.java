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

public class SettingsWifiActivity extends AppCompatActivity {
    /* Constants */
    private static final int REQUEST_SSID = 1;
    private static final int REQUEST_PASSWORD = 2;

    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mSsid;
    private TextView mSsidValue;
    private LinearLayout mPassword;
    private TextView mPasswordValue;

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
                Intent intent = new Intent(SettingsWifiActivity.this, EditStringActivity.class);
                intent.putExtra(EditStringActivity.EXTRA_TITLE, "SSID");
                intent.putExtra(EditStringActivity.EXTRA_DESCRIPTION, "Enter name of wifi network.");
                intent.putExtra(EditStringActivity.EXTRA_VALUE, Settings.getInstance().getmWifiSsid());
                startActivityForResult(intent, REQUEST_SSID);
            }
        });
        mSsidValue = (TextView) findViewById(R.id.WifiSsidDescriptionText);
        mSsidValue.setText(Settings.getInstance().getmWifiSsid());

        // Set password upon clicking linearlayout
        mPassword = (LinearLayout) findViewById(R.id.WifiPasswordLayout);
        mPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsWifiActivity.this, EditPasswordActivity.class);
                intent.putExtra(EditPasswordActivity.EXTRA_TITLE, "Password");
                intent.putExtra(EditPasswordActivity.EXTRA_DESCRIPTION, "Enter password of wifi network.");
                intent.putExtra(EditPasswordActivity.EXTRA_VALUE, Settings.getInstance().getmWifiPassword());
                startActivityForResult(intent, REQUEST_PASSWORD);
            }
        });
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < Settings.getInstance().getmWifiPassword().length(); i++) temp.append("*");
        mPasswordValue = (TextView) findViewById(R.id.WifiPasswordDescriptionText);
        mPasswordValue.setText(temp.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
        }
        else if (requestCode == REQUEST_SSID) {
            String result = data.getStringExtra(EditStringActivity.EXTRA_RESULT);
            mSsidValue.setText(result);
            Settings.getInstance().setmWifiSsid(result);
        }
        else if (requestCode == REQUEST_PASSWORD) {
            String result = data.getStringExtra(EditPasswordActivity.EXTRA_RESULT);
            Settings.getInstance().setmWifiPassword(result);
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < Settings.getInstance().getmWifiPassword().length(); i++) temp.append("*");
            mPasswordValue.setText(temp.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}