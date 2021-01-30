package com.example.ledclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DiscoveryActivity extends AppCompatActivity {
    /* Constants */
    private static final String DEVICE_NAME = "LED Clock";
    public static final String EXTRA_ADDRESS = "device_address";

    /* Activity components */
    private TextView mStatus;
    private ProgressBar mProgress;
    private ImageButton mButton;

    /* Variables */
    private BluetoothAdapter mAdapter = null;
    private String mAddress = null;

    // Called upon starting application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

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
                startDiscovery();
            }
        });

        Intent i = new Intent(DiscoveryActivity.this, SettingsActivity.class);
        startActivity(i);
    }

    // Called right after starting next activity
    @Override
    protected void onStop()
    {
        stopDiscovery();
        super.onStop();
    }

    // Called upon closing application
    @Override
    protected void onDestroy()
    {
        stopDiscovery();
        super.onDestroy();
    }

    // Called upon discovering new bluetooth device
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("BTDEVICE", "Device found " + device.getName() + " " + device.getAddress());
                if (device.getName() != null && device.getName().equals(DEVICE_NAME)) {
                    if (mAddress == null) {
                        mAddress = device.getAddress();
                        Intent i = new Intent(DiscoveryActivity.this, SettingsActivity.class);
                        i.putExtra(EXTRA_ADDRESS, mAddress);
                        startActivity(i);
                    }
                }
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                stopDiscovery();
            }
        }
    };

    // Start bluetooth device discovery
    private void startDiscovery()
    {
        mAddress = null;
        mStatus.setText(getString(R.string.main_start_discovery));
        mButton.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

        if (mAdapter.isDiscovering()) mAdapter.cancelDiscovery();
        mAdapter.startDiscovery();
    }

    // Stop bluetooth device discovery
    private void stopDiscovery()
    {
        mAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);

        mStatus.setText(getString(R.string.main_stop_discover));
        mProgress.setVisibility(View.INVISIBLE);
        mButton.setVisibility(View.VISIBLE);
    }
}