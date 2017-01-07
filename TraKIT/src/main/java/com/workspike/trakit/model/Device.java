package com.workspike.trakit.model;

import java.util.ArrayList;

/**
 * Created by Chamli Priyashan on 12/30/2016.
 */


public class Device {
    private String DeviceId, thumbnailUrl,route;
    private String fbid;
    private String imei;
    private ArrayList<String> bounds_include;
    private String serialnumber;
    private String simnumber;
    private String devicename;
    private String devicepassword;
    private String devicecolor;

    public Device() {
    }

    public Device(String vehicle_number, String thumbnailUrl, int year, String imei,
                  ArrayList<String> bounds_include) {
        this.DeviceId = vehicle_number;
        this.thumbnailUrl = thumbnailUrl;
        this.fbid = fbid;
        this.imei = imei;
        this.bounds_include = bounds_include;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String name) {
        this.DeviceId = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public ArrayList<String> getBounds_include() {
        return bounds_include;
    }

    public void setBounds_include(ArrayList<String> bounds_include) {
        this.bounds_include = bounds_include;
    }




    public void setSerialNo(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public void setSimNo(String simnumber) {
        this.simnumber = simnumber;
    }

    public void setDeviceName(String devicename) {
        this.devicename = devicename;
    }

    public void setDevicePassword(String devicepassword) {
        this.devicepassword = devicepassword;
    }

    public void setDeviceColour(String devicecolor) {
        this.devicecolor = devicecolor;
    }
}
