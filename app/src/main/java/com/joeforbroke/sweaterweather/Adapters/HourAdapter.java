package com.joeforbroke.sweaterweather.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joeforbroke.sweaterweather.R;
import com.joeforbroke.sweaterweather.Weather.Hour;

/**
 * Created by Joseph on 7/17/2015.
 */


public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder>{

    private Hour[] mHours;
    private Context  mContext;

    public HourAdapter(Context context, Hour[] hours) {
        mHours = hours;
        mContext = context;

    }

    @Override
    //Inflates layout file, passes it through view holder
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hourly_list_item, viewGroup, false);
        return new HourViewHolder(view);
    }

    @Override
    //Uses the custom method to bind data to views
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {
        hourViewHolder.bindHour(mHours[i]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    //Holder class to hold and bind views
    public class HourViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        //Initialize all variables in item xml
        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        //Binds all views
        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconIV);

            itemView.setOnClickListener(this);
        }

        //Sets all appropriate values for views
        public void bindHour(Hour hour) {
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText().toString();
            String temperature = mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();
            String message = String.format("At %s it will be %s and %s",
                    time, temperature, summary);
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }
}
