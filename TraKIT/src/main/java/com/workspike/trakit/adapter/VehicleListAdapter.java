package com.workspike.trakit.adapter;

/**
 * Created by Chamli Priyashan on 8/7/2016.
 */


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.workspike.trakit.AppController;
import com.workspike.trakit.R;
import com.workspike.trakit.model.Vehicle;

public class VehicleListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Vehicle> vehicleItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public VehicleListAdapter(Activity activity, List<Vehicle> vehicleItems) {
        this.activity = activity;
        this.vehicleItems = vehicleItems;
    }

    @Override
    public int getCount() {
        return vehicleItems.size();
    }

    @Override
    public Object getItem(int location) {
        return vehicleItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.brows_vehicle_list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.vehi_no);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.cities_include);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Vehicle m = vehicleItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getVehicle_no());

        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre
        String genreStr = "";
        for (String str : m.getCities_include()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);

        // release year
        year.setText(String.valueOf(m.getYear()));

        return convertView;
    }

}