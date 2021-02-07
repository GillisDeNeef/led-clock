package com.example.ledclock.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ledclock.R;
import com.example.ledclock.activities.settings.SettingsActivity;
import com.example.ledclock.bluetooth.BluetoothSerial;
import com.example.ledclock.bluetooth.Commands;
import com.example.ledclock.bluetooth.ThreadCompleteListener;
import com.example.ledclock.settings.Settings;

// TODO: add leading zero setting for hours

public class DiscoveryActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    /* Constants */
    private static final String DEVICE_NAME = "LED Clock";
    private static final String SHARED_PREFERENCES = "sharedPrefs";
    private static final String SHARED_PREFERENCES_DARK_MODE = "mDarkMode";

    /* Activity components */
    private ImageButton mDotsBtn;
    private TextView mStatus;
    private ProgressBar mProgress;
    private ImageButton mButton;

    /* Variables */
    private BluetoothAdapter mAdapter = null;
    private String mAddress = null;

    private SharedPreferences mPreferences = null;
    private boolean mDarkMode = false;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        // Initialize dark mode
        mPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        mDarkMode = mPreferences.getBoolean(SHARED_PREFERENCES_DARK_MODE, false);
        if (mDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Open popup menu upon clicking dots button
        mDotsBtn = (ImageButton) findViewById(R.id.MainDotsButton);
        mDotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DiscoveryActivity.this, v);
                popup.setOnMenuItemClickListener(DiscoveryActivity.this);
                popup.inflate(R.menu.launch_menu);
                popup.getMenu().findItem(R.id.DarkModeItem).setChecked(mDarkMode);
                popup.show();
            }
        });

        // Initialize components
        mStatus = (TextView) findViewById(R.id.ConnectionStatus);
        mProgress = (ProgressBar) findViewById(R.id.ConnectionProgress);
        mButton = (ImageButton) findViewById(R.id.ConnectionButton);

        // Check permissions
        String[] permissionsToRequest = { Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };
        for(String permission : permissionsToRequest)
        {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
            }
        }

        // Start bluetooth adapter
        mStatus.setText(getString(R.string.main_starting));
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) finish();

        // Ask to the user turn the bluetooth on
        if (!mAdapter.isEnabled()) {
            Intent activateBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(activateBluetooth, 1);
        }

        // Register BroadcastReceiver for discovery
        if (mAdapter.isEnabled()) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(mReceiver, filter);
        }

        // Start discovery
        mStatus.setText(getString(R.string.main_start_discovery));
        if (mAdapter.isDiscovering()) mAdapter.cancelDiscovery();
        mAdapter.startDiscovery();

        // Scan for bluetooth devices
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (mAdapter.isDiscovering()) mAdapter.cancelDiscovery();
                mAdapter.startDiscovery();
            }
        });

        // Register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    // Called upon closing application
    @Override
    protected void onDestroy()
    {
        mAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    // Called upon discovering new bluetooth device
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Commands.stop();
                mAddress = null;
                mStatus.setText(getString(R.string.main_start_discovery));
                mButton.setVisibility(View.INVISIBLE);
                mProgress.setVisibility(View.VISIBLE);
            }
            else if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName() != null && device.getName().equals(DEVICE_NAME)) {
                    if (mAddress == null) {
                        mAddress = device.getAddress();
                        mStatus.setText(getString(R.string.main_connecting));
                        mAdapter.cancelDiscovery();
                        Commands.start(mAddress, mHandler);
                    }
                }
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mAddress == null) {
                    mStatus.setText(getString(R.string.main_stop_discover));
                    mProgress.setVisibility(View.INVISIBLE);
                    mButton.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    // Called upon receiving bluetooth message
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String identifier = Commands.getIdentifier(msg.obj.toString());
            String value = Commands.getValue(msg.obj.toString());
            switch (identifier) {
                case Commands.WIFI_SSID:
                    Settings.getInstance().setmWifiSsid(value);
                    break;
                case Commands.WIFI_PWD:
                    Settings.getInstance().setmWifiPassword(value);
                    break;
                case Commands.NTP_SERVER:
                    Settings.getInstance().setmNtpServer(value);
                    break;
                case Commands.NTP_GMT:
                    Settings.getInstance().setmGmtOffset(Integer.parseInt(value));
                    break;
                case Commands.NTP_DAYLIGHT:
                    Settings.getInstance().setmDaylight(Integer.parseInt(value));
                    break;
                case Commands.REFRESH_RATE:
                    Settings.getInstance().setmRefresh(Integer.parseInt(value));
                    break;
                case Commands.LOCATION_LONGITUDE:
                    Settings.getInstance().setmLongitude(Double.parseDouble(value));
                    break;
                case Commands.LOCATION_LATITUDE:
                    Settings.getInstance().setmLatitude(Double.parseDouble(value));
                    break;
                case Commands.COLOR_HOURS:
                    Settings.getInstance().setmColorHours(Integer.parseInt(value, 16));
                    break;
                case Commands.COLOR_MINUTES:
                    Settings.getInstance().setmColorMinutes(Integer.parseInt(value, 16));
                    break;
                case Commands.BRIGHTNESS_DAY:
                    Settings.getInstance().setmBrightnessDay(Integer.parseInt(value));
                    break;
                case Commands.BRIGHTNESS_NIGHT:
                    Settings.getInstance().setmBrightnessNight(Integer.parseInt(value));
                    break;
                case Commands.FETCH_SETTINGS:
                    Settings.getInstance().clearChanges();

                    Intent i = new Intent(DiscoveryActivity.this, SettingsActivity.class);
                    startActivity(i);

                    mStatus.setText(getString(R.string.main_stop_discover));
                    mProgress.setVisibility(View.INVISIBLE);
                    mButton.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    // Dots button popup menu handler
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ConnectItem:
                if (mAdapter.isDiscovering()) mAdapter.cancelDiscovery();
                mAdapter.startDiscovery();
                return true;
            case R.id.DarkModeItem:
                mDarkMode = !item.isChecked();
                if (mDarkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mPreferences.edit().putBoolean(SHARED_PREFERENCES_DARK_MODE, true).apply();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mPreferences.edit().putBoolean(SHARED_PREFERENCES_DARK_MODE, false).apply();
                }
                item.setChecked(mDarkMode);
                return true;
            default:
                return false;
        }
    }
}