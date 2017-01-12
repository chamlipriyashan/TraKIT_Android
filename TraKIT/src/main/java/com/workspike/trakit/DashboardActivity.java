package com.workspike.trakit;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.workspike.trakit.PutMeOnlineActivity.PhoneIMEI;

/////testtttt
public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    final Handler myHandler = new Handler();
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private ProgressDialog loading;
    boolean cbrealtime_update = true;
    static GoogleMap googleMap;
    private ProgressDialog pDialog;
    public String fullname, email,fb_id;
    public final ThreadLocal<ImageView> gif_banner = new ThreadLocal<>();
    private GoogleApiClient client;
    int userRankValue = 2;
    public double f = 0;
    public double k = 0;
    public double early_f = 0;
    public double early_k = 0;
    public static String start_time = "2015-12-09-10:06:20";
    public static String end_time = "2015-12-09-10:07:32";
    public static String[] longi_array;
    public static String[] lati_array;
    public String usernamee = "";
    public String mainemail = "";
    public String timeDuration = "";
    private Button btnrealtime;
    private Button btnselecttime;
    private TextView tv_dashboard_status;
    Button btntemperature;
    String PhoneIMEI;
    private static String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();


        btnrealtime = (Button) findViewById(R.id.btn_realtime);
        tv_dashboard_status=(TextView) findViewById(R.id.tv_dashboard_status);
        btnselecttime = (Button) findViewById(R.id.btn_select_time);
        btntemperature = (Button) findViewById(R.id.btn_temperature);


        FacebookSdk.sdkInitialize(getApplicationContext());


        System.out.println(" this is from Dashboard activity" + loadSavedimagePreferences());


        boolean yy = loadSavedPreferences("put_me_online");
        if (yy == true) {
            Intent x = (new Intent(DashboardActivity.this, LocationService.class));
            if (x == null) {
                startService(new Intent(DashboardActivity.this, LocationService.class));
            }
        }


        fullname = loadSavedstringPreferences("NAME", "DEFAULT");
        System.out.println(fullname);
        email = loadSavedstringPreferences("EMAIL", "DEFAULT");
        System.out.println(email);
         fb_id=loadSavedstringPreferences("ID", "DEFAULT");
        System.out.println(fb_id);
        System.out.println(loadSavedstringPreferences("FIRSTNAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("LASTNAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("NAME", "DEFAULT"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, RegisterActivity.class);




        int permissionCheck = ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE},1);
        } else {
            //TODO
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            PhoneIMEI =telephonyManager.getDeviceId();
            System.out.println(PhoneIMEI);
            System.out.println(PhoneIMEI);
            System.out.println(PhoneIMEI);
            System.out.println(PhoneIMEI);
            System.out.println(PhoneIMEI);
            System.out.println(PhoneIMEI);

            System.out.println(PhoneIMEI);
            System.out.println(PhoneIMEI);

        }

        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification n = new Notification.Builder(this)
                .setContentTitle("New mail from " + "trakit@workspike.com")
                .setContentText("You Have Registered Sucessfully")
                .setSmallIcon(R.drawable.noti_2)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_message_black_24dp, "More", pIntent)
                .addAction(R.drawable.ic_share_black_24dp, "Share", pIntent).build();
//setSmallIcon(R.drawable.noti_2)

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);

        boolean y = loadSavedPreferences("put_me_online");
        System.out.println("MY STATUSSSSSSSSS   MAIN   " + y);
        if (y == true) {
            System.out.println("TRAKIT service is running");
        } else {
            System.out.println("No TRAKIT Service is NOT Running");
        }




        btnrealtime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
              boolean  cbrealtimelocation = loadSavedPreferences("show_realtime_location");
                if(cbrealtimelocation==false) {
                    startService(new Intent(DashboardActivity.this, Realtime_Location_Service.class));
                    savePreferences("show_realtime_location", true);
                    System.out.println("Realtime ON");
                    tv_dashboard_status.setText("Realtime ON");
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Serrvice Started", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();


                }else if(cbrealtimelocation==true){
                    savePreferences("show_realtime_location", false);
                    stopService(new Intent(DashboardActivity.this, Realtime_Location_Service.class));
                    System.out.println("Realtime OFF");
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Serrvice Closed", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    tv_dashboard_status.setText("Realtime OFF");
                    //status.setTextColor(200);
                }
            }
        });



        btnselecttime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SelectTimePeriodActivity.class);
               startActivityForResult(myIntent, 0);

            }
        });



        btntemperature.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent tempIntent = new Intent(view.getContext(), TemperatureActivity.class);
                startActivityForResult(tempIntent, 0);

            }
        });



    }

//    private void UpdateGUI() {
//        myHandler.post(myRunnable);
//    }

    //    final Runnable myRunnable = new Runnable() {
//        public void run() {
//            if (cbrealtime_update==true) {
//                getDataRealtime();
//            } else {
//
//            }
//        }
//    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        ImageView profile_image = (ImageView) findViewById(R.id.profilepicture);
        TextView drawername = (TextView) findViewById(R.id.tvmain_fullname);
        TextView draweremail = (TextView) findViewById(R.id.main_email);


        if (drawername != null) {
            drawername.setText(fullname);
        }
        if (draweremail != null) {
            draweremail.setText(email);
        }
        if (profile_image != null) {
            Glide.with(DashboardActivity.this)
                    .load(loadSavedimagePreferences())
                    .into(profile_image);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(DashboardActivity.this,
                    PrefsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?").setMessage("Do you realy want to Log out?").setIcon(android.R.drawable.ic_dialog_alert).
                    setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            savebooleanPreferences("loginBox", false);
                            LoginManager.getInstance().logOut();
                            finish();
                        }
                    })

                    .setNegativeButton(android.R.string.no, null).show();

            return true;
        }else if (id == R.id.action_clear) {
            googleMap.clear();
            longi_array = null;
            lati_array=null;
//            if (longi_array == null) {
//                System.out.println("Select TimePeriod First");
//
//            } else {
//                for (int m = 0; m < longi_array.length; m++) {
//                    double f = Double.parseDouble(longi_array[m]);
//                    double k = Double.parseDouble(lati_array[m]);
//                    LatLng suwarapola = new LatLng((k / 1000000), (f / 1000000));
//                    googleMap.addMarker(new MarkerOptions().position(suwarapola).title("Hello world").icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot)));
//                }
//            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(DashboardActivity.this,
                    Brows_NotificationsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_favourits) {
            Intent intent = new Intent(DashboardActivity.this,
                    FavouritActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_brows_vehicle) {
            Intent intent = new Intent(DashboardActivity.this,
                    BrowsVehicleActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_browse_people_near) {
            Intent intent = new Intent(DashboardActivity.this,
                    BrowsPeopleNearActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_device) {
            Intent intent = new Intent(DashboardActivity.this,
                    AddDeviceActivity.class);
            startActivity(intent);
        } else if (id == R.id.put_me_online) {
            Intent intent = new Intent(DashboardActivity.this,
                    PutMeOnlineActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(DashboardActivity.this,
                    PrefsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_feedback) {

            final Dialog rankDialog = new Dialog(DashboardActivity.this, R.style.FullHeightDialog);
            rankDialog.setContentView(R.layout.rank_dialog);
            rankDialog.setCancelable(true);
            RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
            //  Drawable drawable = ratingBar.getProgressDrawable();
            //drawable.setColorFilter(Color.parseColor("#FFFDEC00"), PorterDuff.Mode.SRC_ATOP);
            ratingBar.setRating(userRankValue);
            TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
            text.setText("Rate Your Experience");

            Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rankDialog.dismiss();
                }
            });
            //now that the dialog is set up, it's time to show it
            rankDialog.show();
        } else if (id == R.id.nav_share) {
            shareIt();

        } else if (id == R.id.nav_sign_out) {

            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?").setMessage("Do you realy want to Log out?").setIcon(android.R.drawable.ic_dialog_alert).

                    setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            savebooleanPreferences("loginBox", false);
                            LoginManager.getInstance().logOut();
                            finish();
                        }
                    })

                    .setNegativeButton(android.R.string.no, null).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }


        return true;
    }


    private String loadSavedimagePreferences() {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        // boolean checkBoxValue = preferences.getBoolean("CheckBox_Value", false);
        String string = preferences.getString("PROFILEIMAGE", "http://goo.gl/gEgYUd");
        System.out.println("returened image=" + "PROFILEIMAGE" + "value=" + string);
        return string;
    }

    private String loadSavedstringPreferences(String key, String dummy) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        String string = preferences.getString(key, dummy);
        System.out.println("returened string=" + key + "value=" + string);
        return string;
    }


    private void savebooleanPreferences(String key, boolean value) {
        SharedPreferences myPreference= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = myPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


//    private void savebooleanPreferences(String key, String value) {
//        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(key, value);
//        editor.apply();
//   }

    private boolean loadSavedPreferences(String x) {

        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        boolean checkBoxValue = preferences.getBoolean(x, false);//false is the default value...Dont worry
        System.out.println("login checkbox value=" + checkBoxValue);
        return checkBoxValue;

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Dashboard Page", // TODO: Define a title for the content shown.
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
    protected void onResume() {
        super.onResume();

        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo"); System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo"); System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
        System.out.println("aneeeeeyakoooooooooooooooooooooooo");
       // SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        //Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
        SharedPreferences myPreference= PreferenceManager.getDefaultSharedPreferences(this);
        String myListPreference = myPreference.getString("map_type_pref", "default choice");
        System.out.println(myListPreference);
        if("MAP_TYPE_SATELLITE".equals(myListPreference) ) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            System.out.println("sateliteeeeeeeeee");

        }else if("MAP_TYPE_NORMAL".equals(myListPreference)){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            System.out.println("Normallllllllllllllllll");

        }else if("MAP_TYPE_TERRAIN".equals(myListPreference)){
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        }else if("MAP_TYPE_HYBRID".equals(myListPreference)){
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        }else{
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            System.out.println("Default map is loaded");
        }
        //myListPref.setText(myListPreference);
        LatLng Colombo = new LatLng(6.8018, 79.9227);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Colombo, 12));

        if (longi_array == null) {
            System.out.println("Select TimePeriod First");
        } else {
            for (int m = 0; m < longi_array.length; m++) {
                double f = Double.parseDouble(longi_array[m]);
                double k = Double.parseDouble(lati_array[m]);
                System.out.println(f);
                System.out.println(k);
                LatLng suwarapola = new LatLng((k / 1000000), (f / 1000000));
                googleMap.addMarker(new MarkerOptions().position(suwarapola).title("Hello world").icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot)));
            }
        }





    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Dashboard Page", // TODO: Define a title for the content shown.
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
    public void onMapReady(GoogleMap googleMap) {

        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        LatLng Colombo = new LatLng(6.9344, 86.85);
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(suwarapola), 12.0f);
        //googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Colombo, 12));
        //LatLng sydney = new LatLng(-34, 151);
        //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));





        SharedPreferences myPreference= PreferenceManager.getDefaultSharedPreferences(this);
        String myListPreference = myPreference.getString("map_type_pref", "default choice");
        System.out.println(myListPreference);
        if("MAP_TYPE_SATELLITE".equals(myListPreference) ) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            System.out.println("sateliteeeeeeeeee");

        }else if("MAP_TYPE_NORMAL".equals(myListPreference)){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            System.out.println("Normallllllllllllllllll");

        }else if("MAP_TYPE_TERRAIN".equals(myListPreference)){
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        }else if("MAP_TYPE_HYBRID".equals(myListPreference)){
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        }else{
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            System.out.println("Default map is loaded");
        }
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);






    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "http://www.workspike.com/trakit";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TraKIT1 Mobile Vehicle Tracker");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    private void accesURL(String stringg) {
        String url = (stringg);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // loading.dismiss();
               // showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();///dont put this because it will crach the app
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }






    public void showJSON(String response) {
        final String[] temp = {""};
        final String[] date = {""};
        final String[] fuel = {""};
        final String[] lati = {""};
        final String[] longi = {""};
        final String[] alti = {""};
        final String[] satellite = {""};
        final String[] speed = {""};
        final String[] other = {""};
        final String[] vorp= {""};

        String url = ("http://www.workspike.com/trakit/APIs/get_data_api/realtime_location.php?tb_name=d865904028323530");
        ProgressDialog pDialog = new ProgressDialog(DashboardActivity.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
       // pDialog.show();



        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                       // hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                //Vehicle vehicle = new Vehicle();
                               // vehicle.setVehicle_no(obj.getString("vehi_no"));
                                ////vehicle.setThumbnailUrl(obj.getString("image"));
                               // vehicle.setRating(((Number) obj.get("rating")).doubleValue());
                               // vehicle.setYear(obj.getInt("releaseYear"));
                              //  JSONArray genreArry = obj.getJSONArray("cities");
                                System.out.println(obj);
                                vorp[0] =obj.getString("vorp");
                                temp[0] =obj.getString("temp");

                               date[0] =obj.getString("date");
                                fuel[0] =obj.getString("fuel");
                                 lati[0] =obj.getString("lati");
                               longi[0] =obj.getString("longi");
                                alti[0] =obj.getString("alti");
                                 satellite[0] =obj.getString("satellite");
                                speed[0] =obj.getString("speed");
                               other[0] =obj.getString("other");
                                System.out.println(date[0]);
                                System.out.println(date[0]);
                                System.out.println(date[0]);
                                System.out.println(date[0]);

//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }
                             //   vehicle.setCities_include(genre);

                                // adding vehicle to movies array
                              //  vehicleList.add(vehicle);

                            } catch (JSONException e) {

                                Toast toast = Toast.makeText(DashboardActivity.this,
                                        "No Network Detected",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                                e.printStackTrace();

                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


            }
        });



        AppController.getInstance().addToRequestQueue(movieReq);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();




    }

    private void  getDataRealtime() {
        String url = ("http://www.workspike.com/trakit/APIs/get_data_api/realtime_location.php?tb_name=d865904028323530");
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                System.out.println(response);
                showJSON2(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DashboardActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();///dont put this because it will crach the app
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void showJSON2(String response) {
        Double early_f = null;
                Double early_k=null;
        String vorp = "";
        String temp = "";
        String date = "";
        String fuel = "";
        String lati = "";
        String longi = "";
        String alti = "";
        String satellite = "";
        String speed = "";
        String other = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            lati = collegeData.getString(Config.KEY_LATI);
            longi = collegeData.getString(Config.KEY_LONGI);
            alti = collegeData.getString(Config.KEY_ALTI);
            System.out.println("Temperature:\t" + temp + "\n  Date:\t" + date + "\n Fuel:\t" + fuel +
                    "\n  Latitude:\t" + lati + "\n  Longitude:\t" + longi +
                    "\n  Altitude:\t" + alti + "\n  Satellite Count:\t" + satellite + "\n  Speed:\t" + speed + "\n  Other:\t" + other);
          Double  f = Double.parseDouble(lati);
            Double  k = Double.parseDouble(longi);
            if((f==early_f)&&(k==early_k)){
                System.out.println("same logitude and latitude again");
            }else {
                early_f = f;
                early_k = k;
                LatLng suwarapola = new LatLng((f / 1000000), (k / 1000000));
                googleMap.addMarker(new MarkerOptions().position(suwarapola).title("Hello world").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_dot)));
                boolean markers = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void savePreferences(String key, boolean value) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
