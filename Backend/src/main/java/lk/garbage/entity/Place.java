package lk.garbage.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Place {

    @Id
    private String label;
    private String location;
    private double latitude;
    private double longitude;
    private String mainLocation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeFrom")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PlaceDistance> placeDistancesFrom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeTo")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<PlaceDistance> placeDistancesTo;

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

    public Set<PlaceDistance> getPlaceDistancesFrom() {
        return placeDistancesFrom;
    }

    public void setPlaceDistancesFrom(Set<PlaceDistance> placeDistancesFrom) {
        this.placeDistancesFrom = placeDistancesFrom;
    }

    public Set<PlaceDistance> getPlaceDistancesTo() {
        return placeDistancesTo;
    }

    public void setPlaceDistancesTo(Set<PlaceDistance> placeDistancesTo) {
        this.placeDistancesTo = placeDistancesTo;
    }

    public String getMainLocation() {
        return mainLocation;
    }

    public void setMainLocation(String mainLocation) {
        this.mainLocation = mainLocation;
    }

    @Override
    public String toString() {
        return "Place{" +
                "label='" + label + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", mainLocation='" + mainLocation + '\'' +
                '}';
    }
}
