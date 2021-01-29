package com.example.ledclock.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothDataService {
    // Constants
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Bluetooth
    final int mHandlerState = 0;
    Handler mReceiveHandler;

    // Threads
    private ConnectionThread mConnectionThread;
    private CommunicationThread mCommunicationThread;
    private boolean mStopThread;

    // Constructor
    public BluetoothDataService(Handler handler)
    {
        mStopThread = false;
        mReceiveHandler = handler;
    }

    // Open connection
    public void openConnection(String address)
    {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null && adapter.isEnabled()) {
            try {
                BluetoothDevice device = adapter.getRemoteDevice(address);
                mConnectionThread = new ConnectionThread(device);
                mConnectionThread.start();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    // Close connection
    public void closeConnection()
    {
        mReceiveHandler.removeCallbacksAndMessages(null);
        mStopThread = true;
        if (mCommunicationThread != null) {
            mCommunicationThread.closeStreams();
        }
        if (mConnectionThread != null) {
            mConnectionThread.closeSocket();
        }
    }

    // Transmit data
    public void transmitData(String data)
    {
        mCommunicationThread.write(data);
    }

    // Thread handles managing the bluetooth connection
    private class ConnectionThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectionThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket temp = null;
            try {
                temp = mmDevice.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = temp;
        }

        @Override
        public void run() {
            super.run();
            try {
                mmSocket.connect();
                mCommunicationThread = new CommunicationThread(mmSocket);
                mCommunicationThread.start();
                mCommunicationThread.write("x"); // Check if communication is working
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        public void closeSocket() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Thread handles communication with bluetooth device
    private class CommunicationThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public CommunicationThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            while (!mStopThread) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    mReceiveHandler.obtainMessage(mHandlerState, bytes, -1, readMessage).sendToTarget(); // Send the obtained bytes to the UI Activity via handler
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(String input) {
            byte[] msgBuffer = input.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void closeStreams() {
            try {
                mmInStream.close();
                mmOutStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}