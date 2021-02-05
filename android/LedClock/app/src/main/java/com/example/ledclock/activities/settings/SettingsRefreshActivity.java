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
import com.example.ledclock.R;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.settings.Settings;

public class SettingsRefreshActivity extends AppCompatActivity {
    /* Constants */
    private static final int REQUEST_REFRESH = 1;

    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mRate;
    private TextView mRateValue;

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
                Intent intent = new Intent(SettingsRefreshActivity.this, EditIntActivity.class);
                intent.putExtra(EditIntActivity.EXTRA_TITLE, "Refresh rate");
                intent.putExtra(EditIntActivity.EXTRA_DESCRIPTION, "Enter refresh rate for time display (in ms).");
                intent.putExtra(EditIntActivity.EXTRA_VALUE, Settings.getInstance().getmRefresh());
                startActivityForResult(intent, REQUEST_REFRESH);
            }
        });
        mRateValue = (TextView) findViewById(R.id.RefreshRateDescriptionText);
        mRateValue.setText(String.valueOf(Settings.getInstance().getmRefresh()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
        }
        else if (requestCode == REQUEST_REFRESH) {
            int result = data.getIntExtra(EditIntActivity.EXTRA_RESULT, 0);
            mRateValue.setText(String.valueOf(result));
            Settings.getInstance().setmRefresh(result);
            Commands.setRefreshRate(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}