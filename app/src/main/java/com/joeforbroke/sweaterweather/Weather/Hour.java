package com.joeforbroke.sweaterweather.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joseph on 7/15/2015.
 */
public class Hour implements Parcelable{
    private long mTime;
    private String mSummary;
    private String mIcon;
    private String mTimeZone;
    private double mTemperature;

    public Hour() {

    }
    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    //Gets icon resources int
    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    //Convert time into seconds and format
    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(mTime * 1000);
        return formatter.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeString(mIcon);
        dest.writeString(mTimeZone);
        dest.writeDouble(mTemperature);
    }

    private Hour(Parcel in) {
        mTime = in.readLong();
        mSummary = in.readString();
        mIcon = in.readString();
        mTimeZone = in.readString();
        mTemperature = in.readDouble();
    }



    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        //Pass in parcel into the private constructor we made
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}
