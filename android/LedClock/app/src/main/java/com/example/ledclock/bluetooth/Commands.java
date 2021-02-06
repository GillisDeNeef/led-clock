package com.example.ledclock.bluetooth;

import android.os.Handler;

public class Commands{
    // Identifiers
    public static final String FETCH_SETTINGS     = "S0";
    public static final String WIFI_SSID          = "W0";
    public static final String WIFI_PWD           = "W1";
    public static final String NTP_SERVER         = "N0";
    public static final String NTP_GMT            = "N1";
    public static final String NTP_DAYLIGHT       = "N2";
    public static final String LOCATION_LONGITUDE = "L0";
    public static final String LOCATION_LATITUDE  = "L1";
    public static final String REFRESH_RATE       = "R0";
    public static final String COLOR_HOURS        = "C0";
    public static final String COLOR_MINUTES      = "C1";
    public static final String BRIGHTNESS_DAY     = "B0";
    public static final String BRIGHTNESS_NIGHT   = "B1";

    // Variables
    private static BluetoothSerial mSerial = null;

    // Initialization
    public static void start(String address, Handler handler)
    {
        mSerial = new BluetoothSerial();
        mSerial.openConnection(address, handler, new ThreadCompleteListener() {
            @Override
            public void notifyOfThreadComplete() {
                fetchSettings();
            }
        });
    }
    public static void stop()
    {
        if (mSerial != null)
            mSerial.closeConnection();
    }

    // Commands
    public static void setWifiSsid(String ssid) { mSerial.write(WIFI_SSID + ssid); }
    public static void setWifiPassword(String pwd) { mSerial.write(WIFI_PWD + pwd); }
    public static void setNtpServer(String server) { mSerial.write( NTP_SERVER + server); }
    public static void setGmtOffset(int gmt) { mSerial.write( NTP_GMT + String.valueOf(gmt)); }
    public static void setDaylightTime(int daylight) { mSerial.write( NTP_DAYLIGHT + String.valueOf(daylight)); }
    public static void setRefreshRate(int rate) { mSerial.write( REFRESH_RATE + String.valueOf(rate)); }
    public static void setLocationLongitude(double longitude) { mSerial.write( LOCATION_LONGITUDE + String.valueOf(longitude)); }
    public static void setLocationLatitude(double latitude) { mSerial.write( LOCATION_LATITUDE + String.valueOf(latitude)); }
    public static void setColorHours(int color) { mSerial.write( COLOR_HOURS + String.format("%06X", color)); }
    public static void setColorMinutes(int color) { mSerial.write( COLOR_MINUTES + String.format("%06X", color)); }
    public static void setBrightnessDay(int brightness) { mSerial.write( BRIGHTNESS_DAY + String.valueOf(brightness)); }
    public static void setBrightnessNight(int brightness) { mSerial.write( BRIGHTNESS_NIGHT + String.valueOf(brightness)); }
    public static void fetchSettings()
    {
        mSerial.write( FETCH_SETTINGS);
    }

    // Replies
    public static String getIdentifier(String message) { return message.substring(0, 2); }
    public static String getValue(String message) { return (message.length() <= 2) ? null : message.substring(2, message.length()); }
}
