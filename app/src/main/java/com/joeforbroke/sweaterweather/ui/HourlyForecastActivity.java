package com.joeforbroke.sweaterweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.joeforbroke.sweaterweather.Adapters.HourAdapter;
import com.joeforbroke.sweaterweather.R;
import com.joeforbroke.sweaterweather.Weather.Hour;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HourlyForecastActivity extends Activity {

    @Bind(R.id.hourlyBackground)
    RelativeLayout mBackground;

    private Hour[] mHours;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);

        //Grabs background from main activity
        Intent intent = getIntent();
        int background = intent.getIntExtra(MainActivity.BACKGROUND_CODE, R.drawable.very_hot);
        Drawable drawable = getResources().getDrawable(background);
        mBackground.setBackground(drawable);

        //Retrieving Days array passed through
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

        //Setting adapter to view
        HourAdapter adapter= new HourAdapter(this, mHours);
        mRecyclerView.setAdapter(adapter);

        //Layout Manager helps with determining when list items are no longer visible and can be reused
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        //Helps with performance
        mRecyclerView.setHasFixedSize(true);
    }

}
