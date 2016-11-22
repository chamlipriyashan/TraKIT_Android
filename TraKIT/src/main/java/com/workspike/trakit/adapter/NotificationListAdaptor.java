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
import com.workspike.trakit.model.Notification;

import java.util.List;

/**
 * Created by Chamli Priyashan on 9/25/2016.
 */
public class NotificationListAdaptor extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Notification> notificationItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public NotificationListAdaptor(Activity activity, List<Notification> notificationItems) {
        this.activity = activity;
        this.notificationItems = notificationItems;
    }

    @Override
    public int getCount() {
        return notificationItems.size();
    }

    @Override
    public Object getItem(int location) {
        return notificationItems.get(location);
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
            convertView = inflater.inflate(R.layout.notification_list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.notification_thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.notification_no);
        TextView rating = (TextView) convertView.findViewById(R.id.notification_rating);
        TextView genre = (TextView) convertView.findViewById(R.id.catagories_include);
        TextView year = (TextView) convertView.findViewById(R.id.notification_releaseYear);

        // getting movie data for the row
       Notification m = notificationItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getNotification_thumbnailUrl(), imageLoader);

        // title
        title.setText(m.getNotification_no());

        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre
        String genreStr = "";
        for (String str : m.getCatagories_include()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);

        // release year
        year.setText(String.valueOf(m.getNotificationYear()));

        return convertView;
    }




}
