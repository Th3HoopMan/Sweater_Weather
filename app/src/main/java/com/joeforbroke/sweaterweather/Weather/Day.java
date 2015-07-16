package com.joeforbroke.sweaterweather.Weather;

/**
 * Created by Joseph on 7/15/2015.
 */
public class Day {
    private long mTime;
    private String mSummary;
    private double mTemperatureMex;
    private String mIcon;
    private String mTimeZone;

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

    public double getTemperatureMex() {
        return mTemperatureMex;
    }

    public void setTemperatureMex(double temperatureMex) {
        mTemperatureMex = temperatureMex;
    }

    public String getIcon() {
        return mIcon;
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
}
