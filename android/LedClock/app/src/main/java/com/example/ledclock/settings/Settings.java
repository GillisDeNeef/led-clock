package com.example.ledclock.settings;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {
    // Wifi settings
    private String mWifiSsid;
    private String mWifiPassword;

    // Ntp settings
    private String mNtpServer;
    private int mGmtOffset;
    private int mDaylight;

    // Location settings
    private float mLongitude;
    private float mLatitude;

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
    public float getmLongitude() {
        return mLongitude;
    }
    public void setmLongitude(float mLongitude) {
        this.mLongitude = mLongitude;
    }
    public float getmLatitude() {
        return mLatitude;
    }
    public void setmLatitude(float mLatitude) {
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

    // Parcelable part
    public Settings(Parcel in){
        this.mWifiSsid = in.readString();
        this.mWifiPassword = in.readString();
        this.mNtpServer = in.readString();
        this.mGmtOffset = in.readInt();
        this.mDaylight = in.readInt();
        this.mLongitude = in.readFloat();
        this.mLatitude = in.readFloat();
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
        dest.writeFloat(this.mLongitude);
        dest.writeFloat(this.mLatitude);
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
}