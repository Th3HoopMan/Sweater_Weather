package com.joeforbroke.sweaterweather.Weather;

import com.joeforbroke.sweaterweather.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Joseph on 7/13/2015.
 */
public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String[] mDayList = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
    private String[] mWeekIcons = new String[7];
    private int[] mWeekIconIds = new int[7];
    private int  mToday = Calendar.getInstance()
            .get(Calendar.DAY_OF_WEEK) - 1;
    private String mTimeZone;
    private String[] mWeatherEval = {
            "Leave Your Jacket",
            "Bring Your Jacket",
            "Wear A Sweater",
            "Bundle Up",
            "Leave Your Jacket, But Bring An Umbrella",
            "Bring Your Jacket And An Umbrella",
            "Wear A Sweater And Bring An Umbrella",
            "Bundle Up and Bring An Umbrella"
    };

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        return formatter.format(dateTime);
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int) Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }


    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }


    public String[] getdayList() {
        return mDayList;
    }

    public void setdayList(String[] mdayList) {
        this.mDayList = mdayList;
    }

    public int getToday() {
        return mToday;
    }

    public void setToday(int today) {
        int mToday = today;
    }


    public String getDayList(int pos) {
        if (pos >= 7) {
            pos -= 7;
        }
        return mDayList[pos];
    }

    public void setDayList(String[] dayList) {
        this.mDayList = dayList;
    }

    public String[] getWeekIcons() {
        return mWeekIcons;
    }

    public void setWeekIcons(String icon, int pos) {
        mWeekIcons[pos] = icon;
    }


    public int getWeekIconId(int pos) {
        int iconId = R.drawable.clear_day;

        if (mWeekIcons[pos].equals("clear-day")) {
            iconId = R.drawable.clear_day;
        } else if (mWeekIcons[pos].equals("clear-night")) {
            iconId = R.drawable.clear_night;
        } else if (mWeekIcons[pos].equals("rain")) {
            iconId = R.drawable.rain;
        } else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        } else if (mWeekIcons[pos].equals("sleet")) {
            iconId = R.drawable.sleet;
        } else if (mWeekIcons[pos].equals("wind")) {
            iconId = R.drawable.wind;
        } else if (mWeekIcons[pos].equals("fog")) {
            iconId = R.drawable.fog;
        } else if (mWeekIcons[pos].equals("cloudy")) {
            iconId = R.drawable.cloudy;
        } else if (mWeekIcons[pos].equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        } else if (mWeekIcons[pos].equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }


    public String getWeatherEval() {
        if (mTemperature >= 75 && mPrecipChance >= 60) {
            return mWeatherEval[4];
        } else if (mTemperature < 75 && mTemperature >= 65 && mPrecipChance >= 60) {
            return mWeatherEval[5];
        } else if (mTemperature < 65 && mTemperature >= 45 && mPrecipChance >= 60) {
            return mWeatherEval[6];
        } else if (mTemperature < 45 && mPrecipChance >= 60) {
            return mWeatherEval[7];
        } else if (mTemperature >= 75) {
            return mWeatherEval[0];
        } else if (mTemperature < 75 && mTemperature >= 65) {
            return mWeatherEval[1];
        } else if (mTemperature < 65 && mTemperature >= 45) {
            return mWeatherEval[2];
        } else {
            return mWeatherEval[7];
        }
    }

    public void setWeatherEval(String[] weatherEval) {
        mWeatherEval = weatherEval;
    }


}
