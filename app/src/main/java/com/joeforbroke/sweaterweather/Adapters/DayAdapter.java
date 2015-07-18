package com.joeforbroke.sweaterweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joeforbroke.sweaterweather.R;
import com.joeforbroke.sweaterweather.Weather.Day;

import org.w3c.dom.Text;

/**
 * Created by Joseph on 7/16/2015.
 */

//Custom adapter class
public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Day[] mDays;

    //When the adapter is created you pass in the context and the array of day objects you want to add
    public DayAdapter(Context context, Day[] days) {
        mContext = context;
        mDays = days;
    }

    //Implemented methods
    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; //Used to tag items for easy reference. Not using this.
    }

    //Used to create a layout for each item being adapted
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Holds the views...custom class made below
        ViewHolder holder;

        //If convertView is null it has no IDs attached to it
        if (convertView == null) {
            //Inflaters turn xml layouts into views we can use
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            //Adds all the views associated with list item to the holder so they can be reused
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconIV);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            //This is the template basically
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Tells android which list item was selected
        Day day = mDays[position];

        //Sets all appropriate values given from Day Object
        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");
        holder.dayLabel.setText(day.getDayOfTheWeek());
        if (position == 0) {
            holder.dayLabel.setText("Today");
        } else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }
        return convertView;
    }

    //Holds all views as variables
    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
