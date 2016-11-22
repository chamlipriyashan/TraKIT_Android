package com.workspike.trakit.model;

import java.util.ArrayList;

/**
 * Created by Chamli Priyashan on 9/25/2016.
 */
public class Notification {


    private String notification_no, notification_thumbnailUrl,route;
    private int NotificationYear;
    private double rating;
    private ArrayList<String> catagories_include;

    public Notification() {
    }

    public Notification(String notification_number, String thumbnailUrl, int year, double rating,
                   ArrayList<String> cities_include) {
        this.notification_no = notification_number;
        this.notification_thumbnailUrl = thumbnailUrl;
        this.NotificationYear = year;
        this.rating = rating;
        this.catagories_include = cities_include;
    }

    public String getNotification_no() {
        return notification_no;
    }

    public void setNotification_no(String name) {
        this.notification_no = name;
    }

    public String getNotification_thumbnailUrl() {
        return notification_thumbnailUrl;
    }

    public void setNotification_thumbnailUrl(String notification_thumbnailUrl) {
        this.notification_thumbnailUrl = notification_thumbnailUrl;
    }

    public int getNotificationYear() {
        return NotificationYear;
    }

    public void setNotificationYear(int notificationYear) {
        this.NotificationYear = notificationYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCatagories_include() {
        return catagories_include;
    }

    public void setCatagories_include(ArrayList<String> catagories_include) {
        this.catagories_include = catagories_include;
    }

}
