package com.example.ledclock.settings;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings {
    // Wifi settings
    private String mWifiSsid = "";
    private String mWifiPassword = "";

    // Ntp settings
    private String mNtpServer = "";
    private int mGmtOffset = 0;
    private int mDaylight = 0;

    // Location settings
    private double mLongitude = 0;
    private double mLatitude = 0;

    // Refresh rate setting
    private int mRefresh = 0;

    // Color settings
    private int mColorHours = 0;
    private int mColorMinutes = 0;

    // Brightness settings
    private int mBrightnessDay = 0;
    private int mBrightnessNight = 0;

    // Constructor
    public Settings(){ }

    // Getters and setters
    public String getmWifiSsid() {
        return mWifiSsid;
    }
    public void setmWifiSsid(String mWifiSsid) {
        this.mWifiSsid = mWifiSsid;
    }
    public String getmWifiPassword() {
        return mWifiPassword;
    }
    public void setmWifiPassword(String mWifiPassword) {
        this.mWifiPassword = mWifiPassword;
    }
    public String getmNtpServer() {
        return mNtpServer;
    }
    public void setmNtpServer(String mNtpServer) {
        this.mNtpServer = mNtpServer;
    }
    public int getmGmtOffset() {
        return mGmtOffset;
    }
    public void setmGmtOffset(int mGmtOffset) {
        this.mGmtOffset = mGmtOffset;
    }
    public int getmDaylight() {
        return mDaylight;
    }
    public void setmDaylight(int mDaylight) {
        this.mDaylight = mDaylight;
    }
    public double getmLongitude() {
        return mLongitude;
    }
    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }
    public double getmLatitude() {
        return mLatitude;
    }
    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }
    public int getmRefresh() {
        return mRefresh;
    }
    public void setmRefresh(int mRefresh) {
        this.mRefresh = mRefresh;
    }
    public int getmColorHours() {
        return mColorHours;
    }
    public void setmColorHours(int mColorHours) {
        this.mColorHours = mColorHours;
    }
    public int getmColorMinutes() {
        return mColorMinutes;
    }
    public void setmColorMinutes(int mColorMinutes) {
        this.mColorMinutes = mColorMinutes;
    }
    public int getmBrightnessDay() {
        return mBrightnessDay;
    }
    public void setmBrightnessDay(int mBrightnessDay) {
        this.mBrightnessDay = mBrightnessDay;
    }
    public int getmBrightnessNight() {
        return mBrightnessNight;
    }
    public void setmBrightnessNight(int mBrightnessNight) {
        this.mBrightnessNight = mBrightnessNight;
    }

    private static final Settings holder = new Settings();
    public static Settings getInstance() {return holder;}
}
