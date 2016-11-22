package com.workspike.trakit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.workspike.trakit.adapter.FavouritListAdaptor;
import com.workspike.trakit.adapter.NotificationListAdaptor;
import com.workspike.trakit.model.Favourit;
import com.workspike.trakit.model.Notification;

public class FavouritActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = Brows_NotificationsActivity.class.getSimpleName();

    private static final String url = "http://workspike.com/trakit/APIs/get_data_api/favourits.json";
    private ProgressDialog pDialog;
    private List<Favourit> favouritList = new ArrayList<Favourit>();
    private ListView listView;
    private FavouritListAdaptor adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourit);




        listView = (ListView) findViewById(R.id.list);
        adapter = new FavouritListAdaptor(this, favouritList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
       // getActionBar().setBackgroundDrawable(
                //new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Favourit favourit = new Favourit();
                                favourit.setTitle(obj.getString("title"));
                                favourit.setThumbnailUrl(obj.getString("image"));
                                favourit.setRating(((Number) obj.get("rating"))
                                        .doubleValue());
                                favourit.setYear(obj.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                favourit.setGenre(genre);

                                // adding movie to movies array
                                favouritList.add(favourit);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);









    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent vehicle_intent = new Intent(FavouritActivity.this,
                SelectedVehicleActivity.class);

//        System.out.println(vehicleList.get(position).getCities_include());
//        System.out.println(vehicleList.get(position).getVehicle_no());
//        System.out.println(vehicleList.get(position).getRating());
//        System.out.println(vehicleList.get(position).getThumbnailUrl());
//        System.out.println(vehicleList.get(position).getYear());
//
//        vehicle_intent.putExtra("vehicle_number",vehicleList.get(position).getVehicle_no());
//        vehicle_intent.putExtra("vehicle_cities_include",vehicleList.get(position).getCities_include());
//        vehicle_intent.putExtra("vehicle_rating",vehicleList.get(position).getRating());
//        vehicle_intent.putExtra("vehicle_get_thumbnail_url",vehicleList.get(position).getThumbnailUrl());
//        vehicle_intent.putExtra("vehicle_get_year",vehicleList.get(position).getYear());
//        startActivity(vehicle_intent);
//
//
//
//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Item " + (position + 1) + ": " + vehicleList.get(position),
//                Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();
    }




}
