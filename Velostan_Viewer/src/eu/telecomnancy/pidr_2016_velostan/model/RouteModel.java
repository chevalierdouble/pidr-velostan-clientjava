package eu.telecomnancy.pidr_2016_velostan.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasgauthey on 19/05/2016.
 */
public class RouteModel {

    private int id_route;
    private String device;
    private LocationModel location_begin;
    private LocationModel location_end;
    private double averageSpeed;
    private List<LocationModel> listLocation = null;

    public RouteModel() {
        this.listLocation = new ArrayList<>();
    }

    public RouteModel(int id_route, String device, LocationModel location_begin, LocationModel location_end) {
        this.id_route = id_route;
        this.device = device;
        this.location_begin=location_begin;
        this.location_end=location_end;
        this.listLocation = new ArrayList<>();
    }

    public int getId_route() {
        return id_route;
    }

    public LocationModel getLocation_begin() {
        return location_begin;
    }

    public LocationModel getLocation_end() {
        return location_end;
    }

    public List<LocationModel> getListLocation() {
        return listLocation;
    }

    public void setLocation_begin(LocationModel location_begin) {
        this.location_begin = location_begin;
    }

    public void setLocation_end(LocationModel location_end) {
        this.location_end = location_end;
    }

    public void setListLocation(List<LocationModel> listLocation) {
        this.listLocation = listLocation;
    }

    public void setId_route(int id_route) {
        this.id_route = id_route;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String toString() {
        return "(id_route="+this.id_route+", device="+this.device+", lat="+this.location_begin+", lng="+this.location_end+")";
    }
}
