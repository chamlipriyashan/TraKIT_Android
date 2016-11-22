package com.workspike.trakit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SelectedVehicleActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_vehicle);
        Bundle b = getIntent().getExtras();

        SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment2.getMapAsync(this);
        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2)).getMap();


        String vehicle_number = b.getString("vehicle_number"); // will return "FirstKeyValue"
        String vehicle_get_thumbnail_url = b.getString("vehicle_get_thumbnail_url");
        Double vehicle_rating= b.getDouble("vehicle_rating"); // will return "SecondKeyValue"
        ArrayList vehicle_cities_include=b.getStringArrayList("vehicle_cities_include");
        int vehicle_year=b.getInt("vehicle_get_year");
        System.out.println(vehicle_number+ "    "+vehicle_cities_include+"    "+vehicle_get_thumbnail_url+"    "+vehicle_year+"    "+vehicle_rating );
        final ImageView image_of_vehicle = (ImageView) findViewById(R.id.iv_vehicle_image);
        final TextView tv_vehiclename = (TextView) findViewById(R.id.tv_vehicle_name);
        final TextView tv_vehicle_rating = (TextView) findViewById(R.id.tv_vehicle_rating);
        final TextView tv_vehicleyear = (TextView) findViewById(R.id.tv_vehicle_year);
        final TextView tv_vehiclecities = (TextView) findViewById(R.id.tv_cities_include);
      //  final TextView tv_vehicle_url = (TextView) findViewById(R.id.tv_vehicle_url);
        String x= String.valueOf(vehicle_rating);
        String y= String.valueOf(vehicle_cities_include);
        String w= String.valueOf(vehicle_year);

       // tv_vehicle_url.setText(vehicle_get_thumbnail_url);
        tv_vehiclename.setText(vehicle_number);
        tv_vehiclecities.setText(y);
        tv_vehicleyear.setText(w);
        tv_vehicle_rating.setText(x);

        Glide.with(this)
                .load(vehicle_get_thumbnail_url)
                .into(image_of_vehicle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng Colombo = new LatLng(6.9344, 79.85);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(suwarapola), 12.0f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Colombo, 11));


    }
}
