package lk.garbage.dto;

import java.util.Set;

public class PlaceDTO {

    private String location;
    private char label;
    private double weight;
    private double latitude;
    private double longitude;
    private PlaceDTO placeNext;

    public PlaceDTO(String location, char label, double weight, double latitude, double longitude) {
        this.location = location;
        this.label = label;
        this.weight = weight;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public PlaceDTO getPlaceNext() {
        return placeNext;
    }

    public void setPlaceNext(PlaceDTO placeNext) {
        this.placeNext = placeNext;
    }
}
