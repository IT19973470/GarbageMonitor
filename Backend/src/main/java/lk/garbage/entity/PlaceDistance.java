package lk.garbage.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class PlaceDistance {

    @EmbeddedId
    private PlaceDistancePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Place placeFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Place placeTo;

    public PlaceDistancePK getId() {
        return id;
    }

    public void setId(PlaceDistancePK id) {
        this.id = id;
    }

    public Place getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(Place placeFrom) {
        this.placeFrom = placeFrom;
    }

    public Place getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(Place placeTo) {
        this.placeTo = placeTo;
    }

    @Override
    public String toString() {
        return "PlaceDistance{" +
                "id=" + id +
                ", placeFrom=" + placeFrom +
                ", placeTo=" + placeTo +
                '}';
    }
}
