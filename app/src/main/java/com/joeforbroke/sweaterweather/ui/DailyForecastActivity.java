package com.joeforbroke.sweaterweather.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joeforbroke.sweaterweather.Adapters.DayAdapter;
import com.joeforbroke.sweaterweather.R;
import com.joeforbroke.sweaterweather.Weather.Day;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyForecastActivity extends Activity {
    private Day[] mDays;


    @Bind(R.id.dailyBackground) RelativeLayout mBackground;
    @Bind(R.id.locationLabel) TextView mLocationLabel;
    @Bind(android.R.id.list) ListView mListView;
    @Bind(android.R.id.empty) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        ButterKnife.bind(this);
        mLocationLabel.setText(MainActivity.CURRENT_LOCATION);


        //Grabs background from main activity
        Intent intent = getIntent();
        int background = intent.getIntExtra(MainActivity.BACKGROUND_CODE, R.drawable.very_hot);
        Drawable drawable = getResources().getDrawable(background);
        mBackground.setBackground(drawable);

        //How to get parcelable data
        Parcelable[]  parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, mDays);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                 String dayOfTheWeek = mDays[position].getDayOfTheWeek();
                                                 String conditions = mDays[position].getSummary();
                                                 String highTemp = mDays[position].getTemperatureMax() + "";
                                                 String message = String.format("On %s the high will be %s and it will be %s",
                                                         dayOfTheWeek, highTemp, conditions);
                                                 Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_LONG).show();
                                             }
                                         }
        );
    }


}
