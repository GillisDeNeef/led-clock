package com.example.ledclock.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ledclock.activities.edit.EditDoubleActivity;
import com.example.ledclock.R;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.settings.Settings;

public class SettingsLocationActivity extends AppCompatActivity {
    /* Constants */
    private static final int REQUEST_LONGITUDE = 1;
    private static final int REQUEST_LATITUDE = 2;

    /* Activity components */
    private ImageButton mBackBtn;
    private LinearLayout mLongitude;
    private TextView mLongitudeValue;
    private LinearLayout mLatitude;
    private TextView mLatitudeValue;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_location);

        // Close activity upon clicking back button
        mBackBtn = (ImageButton) findViewById(R.id.LocationBackButton);
        mBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Set longitude upon clicking linearlayout
        mLongitude = (LinearLayout) findViewById(R.id.LocationLongitudeLayout);
        mLongitude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsLocationActivity.this, EditDoubleActivity.class);
                intent.putExtra(EditDoubleActivity.EXTRA_TITLE, getString(R.string.location_settings_longitude_title));
                intent.putExtra(EditDoubleActivity.EXTRA_DESCRIPTION, getString(R.string.location_settings_longitude_description));
                intent.putExtra(EditDoubleActivity.EXTRA_VALUE, Settings.getInstance().getmLongitude());
                startActivityForResult(intent, REQUEST_LONGITUDE);
            }
        });
        mLongitudeValue = (TextView) findViewById(R.id.LocationLongitudeValueText);
        mLongitudeValue.setText(String.valueOf(Settings.getInstance().getmLongitude()));

        // Set latitude upon clicking linearlayout
        mLatitude = (LinearLayout) findViewById(R.id.LocationLatitudeLayout);
        mLatitude.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsLocationActivity.this, EditDoubleActivity.class);
                intent.putExtra(EditDoubleActivity.EXTRA_TITLE, getString(R.string.location_settings_latitude_title));
                intent.putExtra(EditDoubleActivity.EXTRA_DESCRIPTION, getString(R.string.location_settings_latitude_description));
                intent.putExtra(EditDoubleActivity.EXTRA_VALUE, Settings.getInstance().getmLatitude());
                startActivityForResult(intent, REQUEST_LATITUDE);
            }
        });
        mLatitudeValue = (TextView) findViewById(R.id.LocationLatitudeValueText);
        mLatitudeValue.setText(String.valueOf(Settings.getInstance().getmLatitude()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            // code to handle cancelled state
        }
        else if (requestCode == REQUEST_LONGITUDE) {
            double result = data.getDoubleExtra(EditDoubleActivity.EXTRA_RESULT, 0);
            mLongitudeValue.setText(String.valueOf(result));
            Settings.getInstance().setmLongitude(result);
            Commands.setLocationLongitude(result);
        }
        else if (requestCode == REQUEST_LATITUDE) {
            double result = data.getDoubleExtra(EditDoubleActivity.EXTRA_RESULT, 0);
            mLatitudeValue.setText(String.valueOf(result));
            Settings.getInstance().setmLatitude(result);
            Commands.setLocationLatitude(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}