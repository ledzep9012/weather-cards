package com.mindfire.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<WeatherObject> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView curTemp;
        public TextView minTemp;
        public TextView maxTemp;
        public TextView date;
        public ImageView icon;

        public ViewHolder(View v) {
            super(v);
            curTemp = (TextView) v.findViewById(R.id.textView3);
            minTemp = (TextView) v.findViewById(R.id.textView2);
            maxTemp = (TextView) v.findViewById(R.id.textView4);
            date = (TextView) v.findViewById(R.id.textView7);
            icon = (ImageView) v.findViewById(R.id.icon_weather);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, ArrayList<WeatherObject> weatherObject) {
        this.context = context;
        mDataset = weatherObject;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v=null;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(position == 0) {
            holder.curTemp.setText("Current " + mDataset.get(position).getCurrentTemp() + "°C");
            holder.minTemp.setText("Min " + mDataset.get(position).getMintemp() + "°C");
            holder.maxTemp.setText("Max " + mDataset.get(position).getMaxtemp() + "°C");
            holder.date.setText(mDataset.get(position).getDate());
            holder.icon.setImageBitmap(mDataset.get(position).getIcon());
        }
        else{
            holder.curTemp.setText("Forecasted " + mDataset.get(position).getCurrentTemp() + "°C");
            holder.minTemp.setText("Min " + mDataset.get(position).getMintemp() + "°C");
            holder.maxTemp.setText("Max " + mDataset.get(position).getMaxtemp() + "°C");
            holder.date.setText(mDataset.get(position).getDate());
            holder.icon.setImageBitmap(mDataset.get(position).getIcon());

        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}