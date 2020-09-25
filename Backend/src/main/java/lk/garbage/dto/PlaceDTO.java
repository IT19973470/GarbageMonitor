package lk.garbage.dto;

import lk.garbage.entity.Place;

import java.util.Set;

public class PlaceDTO {

    private String label;
    private String location;
    private double latitude;
    private double longitude;
    private String mainLocation;
    private PlaceDTO placeNext;

    public PlaceDTO(Place place) {
        if (place != null) {
            this.label = place.getLabel();
            this.location = place.getLocation();
            this.latitude = place.getLatitude();
            this.longitude = place.getLongitude();
            this.mainLocation = place.getMainLocation();
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getMainLocation() {
        return mainLocation;
    }

    public void setMainLocation(String mainLocation) {
        this.mainLocation = mainLocation;
    }

    public PlaceDTO getPlaceNext() {
        return placeNext;
    }

    public void setPlaceNext(PlaceDTO placeNext) {
        this.placeNext = placeNext;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "label='" + label + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", mainLocation='" + mainLocation + '\'' +
                ", placeNext=" + placeNext +
                '}';
    }
}
