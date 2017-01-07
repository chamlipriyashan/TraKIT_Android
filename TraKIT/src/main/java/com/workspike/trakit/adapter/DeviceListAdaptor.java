package com.workspike.trakit.adapter;

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
import com.workspike.trakit.model.Device;

import java.util.List;

/**
 * Created by Chamli Priyashan on 12/30/2016.
 */

public class DeviceListAdaptor extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Device> deviceItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public DeviceListAdaptor(Activity activity, List<Device> vehicleItems) {
        this.activity = activity;
        this.deviceItems = vehicleItems;
    }

    @Override
    public int getCount() {
        return deviceItems.size();
    }

    @Override
    public Object getItem(int location) {
        return deviceItems.get(location);
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
       Device m = deviceItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getDeviceId());

        // rating
        rating.setText("IMEI: " + String.valueOf(m.getImei()));

        // genre
        String genreStr = "";
        for (String str : m.getBounds_include()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);

        // release year
        year.setText(String.valueOf(m.getFbid()));

        return convertView;
    }

}