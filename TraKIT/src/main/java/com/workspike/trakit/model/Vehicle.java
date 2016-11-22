package com.workspike.trakit.model;

/**
 * Created by Chamli Priyashan on 8/7/2016.
 */
import java.util.ArrayList;

public class Vehicle {
    private String vehicle_no, thumbnailUrl,route;
    private int year;
    private double rating;
    private ArrayList<String> cities_include;

    public Vehicle() {
    }

    public Vehicle(String vehicle_number, String thumbnailUrl, int year, double rating,
                   ArrayList<String> cities_include) {
        this.vehicle_no = vehicle_number;
        this.thumbnailUrl = thumbnailUrl;
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
