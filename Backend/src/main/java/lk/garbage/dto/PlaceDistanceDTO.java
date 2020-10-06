package lk.garbage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceDistanceDTO implements Comparable<PlaceDistanceDTO> {
    private PlaceDTO placeFrom;
    private PlaceDTO placeTo;
    private double distance;

    public PlaceDistanceDTO() {
    }

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

    @Override
    public int compareTo(PlaceDistanceDTO o) {
        if (this.getDistance() < o.getDistance()) {
            return -1;
        } else if (this.getDistance() > o.getDistance()) {
            return 1;
        } else {
            return 0;
        }
    }
}
