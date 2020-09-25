package lk.garbage.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlaceDistancePK implements Serializable {

    private String placeFrom;
    private String placeTo;

    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        this.placeFrom = placeFrom;
    }

    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }

    @Override
    public String toString() {
        return "PlaceDistancePK{" +
                "placeFrom='" + placeFrom + '\'' +
                ", placeTo='" + placeTo + '\'' +
                '}';
    }
}
