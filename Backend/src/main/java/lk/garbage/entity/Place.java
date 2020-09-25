package lk.garbage.entity;

import java.util.Set;

public class Place {
    private String location;
    private char label;
    private double latitude;
    private double longitude;
    private Set<PlaceDistance> placeDistances;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Set<PlaceDistance> getPlaceDistances() {
        return placeDistances;
    }

    public void setPlaceDistances(Set<PlaceDistance> placeDistances) {
        this.placeDistances = placeDistances;
    }
}
