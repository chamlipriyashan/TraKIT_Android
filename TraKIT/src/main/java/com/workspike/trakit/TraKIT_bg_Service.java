package com.workspike.trakit;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.facebook.GraphRequest.TAG;

public class TraKIT_bg_Service extends Service {
    android.os.Handler customHandler = new android.os.Handler();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        customHandler.postDelayed(updateTimerThread, 0);
        System.out.println("im the background trakit");
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        customHandler.removeCallbacks(updateTimerThread);
         Toast.makeText(this,"Service Stopped",Toast.LENGTH_LONG).show();
     }
     private Runnable updateTimerThread = new Runnable()
     {
         public void run()
         {
             boolean y = loadSavedPreferences("put_me_online");
             System.out.println("MY STATUSSSSS   MAIN   "+y);
             if(y==true){

                 double latitude=1 ;
                 double longitude=2 ;
                     Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                 // http://workspike.com/trakit/APIs/put_data_api/put_gps_data.php?temp=44&date=1608251111&fuel=1544456&lati=713656&longi=12565436&alti=102453&satellite=10&speed=131&other=12254&tb_name=chamlioo
                 // startService(new Intent(DashboardActivity.this, LocationService.class));
                 TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                 String IMEI =telephonyManager.getDeviceId();
               //  double lati=googleMap.getMyLocation().getLatitude();
                // double longi=googleMap.getMyLocation().getLongitude();
               //  double alti=googleMap.getMyLocation().getAltitude();
               //  double speed=googleMap.getMyLocation().getSpeed();
               String url="http://workspike.com/trakit/APIs/put_data_api/put_gps_data.php?temp=44&date=1608251111&fuel=1544456&lati=713656&longi=12565436&alti=102453&satellite=10&speed=131&vorp=p&other=12254&tb_name=p"+IMEI  ;
                 //String url = ("http://workspike.com/trakit/APIs/put_data_api/put_gps_data.php?id=100&temp="+100+"&date="+currentDateandTime+"&fuel="+10+"&lati="+lattt+"&longi="+longii+"&alti="+myalt+"&satellite="+7+"&speed="+myspeed+"&other=000");
               //  String url ="http://workspike.com/trakit/APIs/put_data_api/put_gps_data.php?temp="+100+"&date="+1+"&fuel="+10+"&lati="+lati+"&longi="+longi+"&alti="+alti+"&satellite="+7+"&speed="+speed+"&other="+000+"&tb_name=p"+IMEI;//p is used to identyfy its a phone
                 System.out.println(url);
                 accesURL(url);
                 System.out.println(telephonyManager.getDeviceId());
             }else{
                 customHandler.removeCallbacks(updateTimerThread);
             }

             customHandler.postDelayed(this, 2000);
         }
     };



     private boolean loadSavedPreferences(String x) {

         SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
         boolean checkBoxValue = preferences.getBoolean(x, false);//false is the default value...Dont worry
         System.out.println("login checkbox value=" + checkBoxValue);
         return checkBoxValue;

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




 }
