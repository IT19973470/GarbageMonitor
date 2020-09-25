package lk.garbage.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Place {

    @Id
    private String label;
    private String location;
    private double latitude;
    private double longitude;
    private Set<PlaceDistance> placeDistances;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
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

    @Override
    public String toString() {
        return "Place{" +
                "label='" + label + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
