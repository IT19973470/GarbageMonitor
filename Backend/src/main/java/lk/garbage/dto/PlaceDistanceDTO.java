package lk.garbage.dto;

import java.util.Objects;

public class PlaceDistanceDTO {
    private PlaceDTO placeFrom;
    private PlaceDTO placeTo;
    private double distance;

    public PlaceDistanceDTO(PlaceDTO placeFrom, PlaceDTO placeTo, double distance) {
        this.placeFrom = placeFrom;
        this.placeTo = placeTo;
        this.distance = distance;
    }

    public PlaceDTO getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(PlaceDTO placeFrom) {
        this.placeFrom = placeFrom;
    }

    public PlaceDTO getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(PlaceDTO placeTo) {
        this.placeTo = placeTo;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "PlaceDistanceDTO{" +
                "placeFrom=" + placeFrom +
                ", placeTo=" + placeTo +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) {
            return o.equals(getPlaceFrom().getLabel()) || o.equals(getPlaceTo().getLabel());
        }
        return false;
    }

}
