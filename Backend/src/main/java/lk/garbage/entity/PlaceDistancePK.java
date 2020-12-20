package lk.garbage.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlaceDistancePK implements Serializable {

    private String placeFromId;
    private String placeToId;

    public String getPlaceFromId() {
        return placeFromId;
    }

    public void setPlaceFromId(String placeFromId) {
        this.placeFromId = placeFromId;
    }

    public String getPlaceToId() {
        return placeToId;
    }

    public void setPlaceToId(String placeToId) {
        this.placeToId = placeToId;
    }

    @Override
    public String toString() {
        return "PlaceDistancePK{" +
                "placeFromId='" + placeFromId + '\'' +
                ", placeToId='" + placeToId + '\'' +
                '}';
    }
}
