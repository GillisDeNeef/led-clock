package com.example.ledclock.settings;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings {
    // Wifi settings
    private String mWifiSsid;
    private String mWifiPassword;

    // Ntp settings
    private String mNtpServer;
    private int mGmtOffset;
    private int mDaylight;

    // Location settings
    private double mLongitude;
    private double mLatitude;

    // Refresh rate setting
    private int mRefresh;

    // Color settings
    private int mColorHours;
    private int mColorMinutes;

    // Brightness settings
    private int mBrightnessDay;
    private int mBrightnessNight;

    // Constructor
    public Settings(){
        mWifiSsid = "Telenet-ssid";
        mWifiPassword = "Telenet-pwd";
        mNtpServer = "pool.ntp.com";
        mGmtOffset = 60;
        mDaylight = 60;
        mLongitude = 50.456;
        mLatitude = 3.6546;
        mRefresh = 1000;
        mColorHours = 0xFFFFFF;
        mColorMinutes = 0xFFFFFF;
        mBrightnessDay = 20;
        mBrightnessNight = 10;
    }

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

    /*
    // Parcelable part
    public Settings(Parcel in){
        this.mWifiSsid = in.readString();
        this.mWifiPassword = in.readString();
        this.mNtpServer = in.readString();
        this.mGmtOffset = in.readInt();
        this.mDaylight = in.readInt();
        this.mLongitude = in.readDouble();
        this.mLatitude = in.readDouble();
        this.mRefresh = in.readInt();
        this.mColorHours = in.readInt();
        this.mColorMinutes = in.readInt();
        this.mBrightnessDay = in.readInt();
        this.mBrightnessNight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mWifiSsid);
        dest.writeString(this.mWifiPassword);
        dest.writeString(this.mNtpServer);
        dest.writeInt(this.mGmtOffset);
        dest.writeInt(this.mDaylight);
        dest.writeDouble(this.mLongitude);
        dest.writeDouble(this.mLatitude);
        dest.writeInt(this.mRefresh);
        dest.writeInt(this.mColorHours);
        dest.writeInt(this.mColorMinutes);
        dest.writeInt(this.mBrightnessDay);
        dest.writeInt(this.mBrightnessNight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
    */
}
