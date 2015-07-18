package com.joeforbroke.sweaterweather.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.joeforbroke.sweaterweather.R;
import com.joeforbroke.sweaterweather.Weather.Current;
import com.joeforbroke.sweaterweather.Weather.Day;
import com.joeforbroke.sweaterweather.Weather.Forecast;
import com.joeforbroke.sweaterweather.Weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    public static final String BACKGROUND_CODE = "BACKGROUND_CODE";
    public static String CURRENT_LOCATION = "Atlanta, GA";

    private Forecast mForecast;

    @Bind(R.id.timeLabel) TextView mTimeLabel;
    @Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
    @Bind(R.id.clothingLabel) TextView mclothingLabel;
    @Bind(R.id.mainIconIV) ImageView mMainIconIV;
    @Bind(R.id.locationLabel) TextView mLocationLabel;
    @Bind(R.id.precipValue) TextView mPrecipValue;
    @Bind(R.id.humidityValue) TextView mHumidityValue;
    @Bind(R.id.summaryTV) TextView mSummaryTV;
    @Bind(R.id.background) RelativeLayout mBackground;
    @Bind(R.id.refreshIV) ImageView mRefreshIV;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private double mLatitude = 33.4545935;
    private double mLongitude = -84.45128389999999;
    private String mCity = "Atlanta";
    private String mState = "GA";
    private Drawable mDrawable;
    private int mBackgroundInt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adds all views to the activity
        ButterKnife.bind(this);

        //Creates a Google Services Client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks( this)
                .addOnConnectionFailedListener( this)
                .addApi(LocationServices.API)
                .build();

        //Hides the progress bar at start
        mProgressBar.setVisibility(View.INVISIBLE);



        //Refreshes when icon clicked
        mRefreshIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(mLatitude, mLongitude);
            }
        });


        getForecast(mLatitude, mLongitude);

        Log.d(TAG, "Main UI is Running");
    }

    private void retrieveLocation() {
        Geocoder mGeocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = mGeocoder.getFromLocation(mLatitude, mLongitude, 1);
            mCity = addresses.get(0).getLocality();
            mState = addresses.get(0).getAdminArea();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CURRENT_LOCATION = mCity + ", " + mState;
        mLocationLabel.setText(mCity + ", " + mState);
    }


    private void getForecast(double latitude, double longitude) {
        String apiKey = "570564442492eba282cdb03774873235";
        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/" + latitude +
                "," + longitude;

        if (isNetworkAvailable()) {
            toggleRefresh();

            //Sends request to get JSON data from URL
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshIV.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshIV.setVisibility(View.VISIBLE);
        }
    }


    private void updateDisplay() {
        Current current = mForecast.getCurrent();
        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText("Forecast for " + current.getFormattedTime() + " in");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mclothingLabel.setText(current.getWeatherEval());
        mSummaryTV.setText(current.getSummary());
        int start = current.getToday();

        mDrawable = getResources().getDrawable(current.getIconId());
        mMainIconIV.setImageDrawable(mDrawable);
        mDrawable = getResources().getDrawable(current.getWeekIconId(0));

        int checkTemp = current.getTemperature();
        if (checkTemp >= 90) {
            mDrawable = getResources().getDrawable(R.drawable.very_hot);
            mBackground.setBackground(mDrawable);
            mBackgroundInt = R.drawable.very_hot;
        } else if (checkTemp > 80 && checkTemp <= 90) {
            mDrawable = getResources().getDrawable(R.drawable.hot);
            mBackground.setBackground(mDrawable);
            mBackgroundInt = R.drawable.hot;
        } else if (checkTemp > 65 && checkTemp <= 80) {
            mDrawable = getResources().getDrawable(R.drawable.moderate);
            mBackground.setBackground(mDrawable);
            mBackgroundInt = R.drawable.moderate;
        } else if (checkTemp > 50 && checkTemp <= 65) {
            mDrawable = getResources().getDrawable(R.drawable.cold);
            mBackground.setBackground(mDrawable);
            mBackgroundInt = R.drawable.cold;
        } else{
            mDrawable = getResources().getDrawable(R.drawable.freezing);
            mBackground.setBackground(mDrawable);
            mBackgroundInt = R.drawable.freezing;
        }

        retrieveLocation();

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        return forecast;


    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimeZone(timezone);

            days[i] = day;

        }

        return days;

    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timezone);

            hours[i] = hour;

        }

        return hours;
    }


    //Goes through the JSON data and pulls data
    private Current getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");
        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray weekSum = daily.getJSONArray("data");
        for (int i = 0; i < 7; i++) {
            JSONObject dayObj = weekSum.getJSONObject(i);
            String icon = dayObj.getString("icon");
            current.setWeekIcons(icon, i);
        }


        Log.d(TAG, current.getFormattedTime());

        return current;

    }

    //Checks to see if user is connected the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError(){
            AlertDialogFragment dialog = new AlertDialogFragment();
            dialog.show(getFragmentManager(), "error_dialog");
        }

    //Gets user's last location
    @Override
        public void onConnected(Bundle bundle) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitude = mLastLocation.getLatitude();
                mLongitude = mLastLocation.getLongitude();
            }
        }

        @Override
        public void onConnectionSuspended ( int i){

        }

        @Override
        public void onConnectionFailed (ConnectionResult connectionResult){

        }

    //Switches to daily activity and sends over background info
    @OnClick (R.id.dailyBtn)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(BACKGROUND_CODE, mBackgroundInt);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick (R.id.hourlyBtn)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(BACKGROUND_CODE, mBackgroundInt);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
        startActivity(intent);
    }

}
