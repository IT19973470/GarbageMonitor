package lk.garbage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BestPathDTO {

    private List<PlaceDistanceDTO> placeDistances;
    private double distance;

    public List<PlaceDistanceDTO> getPlaceDistances() {
        return placeDistances;
    }

    public void setPlaceDistances(List<PlaceDistanceDTO> placeDistances) {
        this.placeDistances = placeDistances;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
