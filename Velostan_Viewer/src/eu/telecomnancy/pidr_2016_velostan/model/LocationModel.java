package eu.telecomnancy.pidr_2016_velostan.model;

import java.util.Date;

/**
 * Created by Yoann on 17/05/2016.
 */
public class LocationModel {

    private int id;
    private double latitude;
    private double longitude;
    private double speed;
    private Date date;
    private String device;
    private int idTrajet;

    public LocationModel() {}

    public LocationModel(String device, double latitude, double longitude, double speed, Date date, int idTrajet) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.device = device;
        this.date = date;
        this.idTrajet = idTrajet;
    }

    public LocationModel(int id, String device, double latitude, double longitude, double speed, Date date, int idTrajet) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.device = device;
        this.date = date;
        this.idTrajet = idTrajet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public Date getDate() {
        return date;
    }

    public String getDevice() { return device; }

    public int getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }

    @Override
    public String toString() {
        return "(device="+this.device+", lat="+this.latitude+", lng="+this.longitude
                +", speed="+this.speed+", date="+this.date+", id_trajet="+this.idTrajet+")";
    }
}
