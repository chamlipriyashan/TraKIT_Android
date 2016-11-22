package com.workspike.trakit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.workspike.trakit.adapter.NotificationListAdaptor;
import com.workspike.trakit.adapter.VehicleListAdapter;
import com.workspike.trakit.model.Notification;
import com.workspike.trakit.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Brows_NotificationsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = Brows_NotificationsActivity.class.getSimpleName();

    // Movies json url
    // private static final String url = "http://api.androidhive.info/json/movies.json";
    private static final String url = "http://workspike.com/trakit/APIs/get_data_api/brows_notifications.json";
    private ProgressDialog pDialog;
    private List<Notification> notificationList = new ArrayList<Notification>();
    private ListView listView;
    private NotificationListAdaptor adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brows__notifications);


        listView = (ListView) findViewById(R.id.list);
        adapter = new NotificationListAdaptor(this, notificationList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        pDialog = new ProgressDialog(Brows_NotificationsActivity.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        //getActionBar().setBackgroundDrawable(
        //         new ColorDrawable(Color.parseColor("#1b1b1b")));

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
                                Notification notification = new Notification();
                                notification.setNotification_no(obj.getString("title"));
                                notification.setNotification_thumbnailUrl(obj.getString("image"));
                                notification.setRating(((Number) obj.get("rating")).doubleValue());
                                notification.setNotificationYear(obj.getInt("releaseYear"));
                                JSONArray genreArry = obj.getJSONArray("genre");

                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                notification.setCatagories_include(genre);

                                // adding vehicle to movies array
                                notificationList.add(notification);

                            } catch (JSONException e) {

                                Toast toast = Toast.makeText(Brows_NotificationsActivity.this,
                                        "No Network Detected",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Brows Vehicles Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.workspike.trakit/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Brows Vehicles Page", // TODO: Define a title for the content shown.//"Browsprojects Page"
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.workspike.trakit/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent vehicle_intent = new Intent(Brows_NotificationsActivity.this,
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
