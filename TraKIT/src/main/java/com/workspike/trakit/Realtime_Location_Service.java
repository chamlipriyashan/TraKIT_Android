package com.workspike.trakit;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Realtime_Location_Service extends Service {

    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
   // public LocationService.MyLocationListener listener;
    public Location previousBestLocation = null;
    Intent intent;
    int counter = 0;

    //TimerTask scanTask;
   // final Handler handler = new Handler();
    //Timer t = new Timer();
    Timer timer=new Timer();

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);

    }

    public Realtime_Location_Service() {
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        cancelimer();
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TimerTask tt=new TimerTask(){
            @Override
            public void run() {
               // Location loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              //  sendCoords(String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
               // Toast.LENGTH_SHORT).show();
               // Log.i("EOH",String.valueOf(loc.getLatitude()));
                System.out.println("realtime location is working");
                System.out.println("realtime location is working");
                System.out.println("realtime location is working");
                System.out.println("realtime location is working");
                System.out.println("realtime location is working");
                System.out.println("realtime location is working");
                System.out.println("realtime location is working");
                getDataRealtime();
            }
        };
        timer.schedule(tt,0,5000);
        return START_STICKY;
    }

public void cancelimer(){
    System.out.println("you've closed the timer ");
    if(timer!=null){
        timer.cancel();
        System.out.println("you've closed the timer sucessfully ");

    }
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
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println( error.getMessage().toString());
//                        System.out.println( error.getMessage().toString());
//                        System.out.println( error.getMessage().toString());
//                        System.out.println( error.getMessage().toString());
//                        Toast.makeText(Realtime_Location_Service.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();///dont put this because it will crach the app
//                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(Realtime_Location_Service.this, message, Toast.LENGTH_LONG).show();
                            if(timer!=null){
                                timer.cancel();
                                System.out.println("you've closed the timer sucessfully ");

                            }

                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(Realtime_Location_Service.this, message, Toast.LENGTH_LONG).show();
                            if(timer!=null){
                                timer.cancel();
                                System.out.println("you've closed the timer sucessfully ");

                            }
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(Realtime_Location_Service.this, message, Toast.LENGTH_LONG).show();
                            if(timer!=null){
                                timer.cancel();
                                System.out.println("you've closed the timer sucessfully ");

                            }
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(Realtime_Location_Service.this, message, Toast.LENGTH_LONG).show();
                            if(timer!=null){
                                timer.cancel();
                                System.out.println("you've closed the timer sucessfully ");

                            }
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(Realtime_Location_Service.this, message, Toast.LENGTH_LONG).show();
                            if(timer!=null){
                                timer.cancel();
                                System.out.println("you've closed the timer sucessfully ");

                            }
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(Realtime_Location_Service.this, message, Toast.LENGTH_LONG).show();
                        }


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
                DashboardActivity.googleMap.addMarker(new MarkerOptions().position(suwarapola).title("Hello world").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_dot)));
                boolean markers = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
