package com.workspike.trakit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.workspike.trakit.adapter.DeviceListAdaptor;
import com.workspike.trakit.model.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrowseDeviceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // Log tag
    private static final String TAG = BrowsVehicleActivity.class.getSimpleName();

    // Movies json url
    // private static final String url = "http://api.androidhive.info/json/movies.json";
    private static final String url = "http://workspike.com/trakit/APIs/get_data_api/brows_vehicles.json";
    private static final String phpurl = "http://workspike.com/trakit/APIs/get_data_api/brows_vehicles.php?fbid=1275166865850228";
    private ProgressDialog pDialog;
   // private List<Vehicle> vehicleList = new ArrayList<Vehicle>();
    private List<Device> mydevicesList = new ArrayList<Device>();
    private ListView listView;
    private DeviceListAdaptor adapter;
    // private DeviceListAdapter adapter2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_device);
        listView = (ListView) findViewById(R.id.list);
        adapter = new DeviceListAdaptor(this,mydevicesList);
        // adapter2 = new VehicleListAdapter(this, mydevicesList);
        listView.setAdapter(adapter);

        //  listView.setAdapter(mydevicesList);
        listView.setOnItemClickListener(this);
        pDialog = new ProgressDialog(BrowseDeviceActivity.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

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
                                Device device = new Device();
                                device.setDeviceId(obj.getString("vehi_no"));
                                device.setThumbnailUrl(obj.getString("image"));
                                device.setImei(String.valueOf(((Number) obj.get("IMEI")).doubleValue()));
                                device.setFbid(String.valueOf(obj.getInt("FBID")));
                                JSONArray genreArry = obj.getJSONArray("bOUNDS");

                                ArrayList<String> bounds= new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    bounds.add((String) genreArry.get(j));
                                }
                                device.setBounds_include(bounds);

                                // adding vehicle to movies array
                                mydevicesList.add(device);

                            } catch (JSONException e) {

                                Toast toast = Toast.makeText(BrowseDeviceActivity.this,
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






        JsonArrayRequest browsvehicleReq = new JsonArrayRequest(phpurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Device mydevices = new Device();
                                mydevices.setDeviceId(obj.getString("device_id"));
                                mydevices.setThumbnailUrl(obj.getString("image"));
                                mydevices.setImei((obj.getString("imei")));
                                mydevices.setFbid(obj.getString("fbid"));
                                mydevices.setSerialNo(obj.getString("serialnumber"));
                                mydevices.setSimNo(obj.getString("simnumber"));
                                mydevices.setDeviceName((obj.getString("devicename")));
                                mydevices.setDevicePassword(obj.getString("devicepassword"));
                                mydevices.setDeviceColour(obj.getString("devicecolor"));

                                System.out.println(obj.getString("device_id"));
                                System.out.println(obj.getString("image"));
                                System.out.println((obj.getString("imei")));
                                System.out.println(obj.getString("fbid"));
                                System.out.println(obj.getString("serialnumber"));
                                System.out.println((obj.getString("devicename")));
                                System.out.println(obj.getString("devicecolor"));




                                JSONArray boundArry = obj.getJSONArray("bounds");

                                ArrayList<String> bounds = new ArrayList<String>();
                                for (int j = 0; j < boundArry.length(); j++) {
                                    bounds.add((String) boundArry.get(j));
                                }
                                mydevices.setBounds_include(bounds);




                                mydevicesList.add(mydevices);

                            } catch (JSONException e) {

                                Toast toast = Toast.makeText(BrowseDeviceActivity.this,
                                        "No Network Detected..Mine",
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
        // AppController.getInstance().addToRequestQueue(movieReq);
        AppController.getInstance().addToRequestQueue(browsvehicleReq);


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

        Intent vehicle_intent = new Intent(BrowseDeviceActivity.this,
                SelectedVehicleActivity.class);

        System.out.println(mydevicesList.get(position).getBounds_include());
        System.out.println(mydevicesList.get(position).getDeviceId());
        System.out.println(mydevicesList.get(position).getFbid());
        System.out.println(mydevicesList.get(position).getThumbnailUrl());
        System.out.println(mydevicesList.get(position).getImei());

        vehicle_intent.putExtra("device_id",mydevicesList.get(position).getDeviceId());
        vehicle_intent.putExtra("device_cities_include",mydevicesList.get(position).getBounds_include());
        vehicle_intent.putExtra("device_rating",mydevicesList.get(position).getFbid());
        vehicle_intent.putExtra("device_get_thumbnail_url",mydevicesList.get(position).getThumbnailUrl());
        vehicle_intent.putExtra("device_get_year",mydevicesList.get(position).getImei());
        startActivity(vehicle_intent);



        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + mydevicesList.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}


