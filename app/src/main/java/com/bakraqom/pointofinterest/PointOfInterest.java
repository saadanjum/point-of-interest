package com.bakraqom.pointofinterest;

/**
 * Created by saad on 10/24/15.
 */
public class PointOfInterest {

    int id;
    double lat;
    double lon;
    String nameOfPlace;
    String category;



    public PointOfInterest() {
        this.id = -1;
        this.lat = 0.0;
        this.lon = 0.0;
        this.nameOfPlace = "NA";
        this.category = "NA";
    }


    public int getId() {

        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getNameOfPlace() {
        return nameOfPlace;
    }

    public String getCategory() {
        return category;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setNameOfPlace(String nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PointOfInterest(double lat, double lon, String nameOfPlace, String category) {
        this.lat = lat;
        this.lon = lon;
        this.nameOfPlace = nameOfPlace;
        this.category = category;
    }

    public PointOfInterest(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }


}
