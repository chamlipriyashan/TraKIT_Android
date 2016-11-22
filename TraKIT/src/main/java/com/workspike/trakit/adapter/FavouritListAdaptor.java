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
import com.workspike.trakit.model.Favourit;

import java.util.List;

/**
 * Created by Chamli Priyashan on 9/25/2016.
 */
public class FavouritListAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Favourit> favouritItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FavouritListAdaptor(Activity activity, List<Favourit> movieItems) {
        this.activity = activity;
        this.favouritItems = movieItems;
    }

    @Override
    public int getCount() {
        return favouritItems.size();
    }

    @Override
    public Object getItem(int location) {
        return favouritItems.get(location);
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
            convertView = inflater.inflate(R.layout.favourit_list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.favourit_thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.favourit_title);
        TextView rating = (TextView) convertView.findViewById(R.id.favourit_rating);
        TextView genre = (TextView) convertView.findViewById(R.id.favourit_genre);
        TextView year = (TextView) convertView.findViewById(R.id.favourit_releaseYear);

        // getting movie data for the row
        Favourit m = favouritItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre
        String genreStr = "";
        for (String str : m.getGenre()) {
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
