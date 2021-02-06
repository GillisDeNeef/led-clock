package com.example.ledclock.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import androidx.core.content.res.TypedArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class BluetoothSerial {
    // Constants
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Bluetooth
    final int mHandlerState = 0;
    Handler mReceiveHandler;

    // Threads
    private ConnectionThread mConnectionThread;
    private CommunicationThread mCommunicationThread;
    private boolean mStopThread;

    // Thread complete listeren
    private ThreadCompleteListener mListener = null;

    // Open connection
    public void openConnection(String address, Handler handler, ThreadCompleteListener connected)
    {
        mStopThread = false;
        mReceiveHandler = handler;
        mListener = connected;
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
    public void write(String data)
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
                //mCommunicationThread.write("x"); // Check if communication is working
                mListener.notifyOfThreadComplete();
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
        private static final byte START = 0x2A;
        private static final byte STOP = 0x0A;

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
            int bytes = 0;
            int start = -1;
            int stop = -1;

            while (!mStopThread) {
                try {
                    bytes += mmInStream.read(buffer, bytes, 256 - bytes);
                    if (bytes > 0) {
                        start = (new String(buffer, StandardCharsets.UTF_8)).indexOf(START);
                        if (start >= 0) {
                            stop = (new String(buffer, StandardCharsets.UTF_8)).indexOf(STOP);
                            while (stop < 0) {
                                bytes += mmInStream.read(buffer, bytes, 256 - bytes);
                                stop = (new String(buffer, StandardCharsets.UTF_8)).indexOf(STOP);
                            }
                            String readMessage = new String(buffer, start + 1, stop - start - 1);
                            mReceiveHandler.obtainMessage(mHandlerState, -1, -1, readMessage).sendToTarget();

                            bytes -= (stop - start + 1);
                            if (bytes > 0) {
                                byte[] temp = Arrays.copyOfRange(buffer, stop + 1, stop + 1 + bytes);
                                Arrays.fill(buffer, (byte)0);
                                System.arraycopy(temp, 0, buffer, 0, bytes);
                            }
                            else {
                                Arrays.fill(buffer, (byte)0);
                                bytes = 0;
                            }
                        }
                        else {
                            bytes = 0;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(String input) {
            byte[] msgBuffer = new byte[input.length() + 2];
            msgBuffer[0] = START;
            System.arraycopy(input.getBytes(), 0, msgBuffer, 1, input.length());
            msgBuffer[msgBuffer.length-1] = STOP;
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