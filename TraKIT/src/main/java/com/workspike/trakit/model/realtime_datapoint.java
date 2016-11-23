package com.workspike.trakit.model;

import java.util.ArrayList;

/**
 * Created by Chamli Priyashan on 11/22/2016.
 */

public class realtime_datapoint {
    private String vehicle_no, thumbnailUrl,route;
    private String latitude,longitude,fuel,prov,temp,other,date,speed,satellite,altitude;
    private int year;
    private double rating;
    private ArrayList<String> cities_include;
  //  private ArrayList<String> cities_includev;

    public realtime_datapoint() {
    }

    public realtime_datapoint(String latitude,String longitude,String fuel,String prov,String temp,String other,String date,String speed,String satellite,String altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;
        this.rating = rating;
        this.cities_include = cities_include;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String name) {
        this.vehicle_no = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCities_include() {
        return cities_include;
    }

    public void setCities_include(ArrayList<String> cities_include) {
        this.cities_include = cities_include;
    }

}
