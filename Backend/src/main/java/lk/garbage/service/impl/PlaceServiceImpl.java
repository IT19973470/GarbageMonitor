package lk.garbage.service.impl;

import lk.garbage.dto.PlaceDTO;
import lk.garbage.dto.PlaceDistanceDTO;
import lk.garbage.dto.SensorDTO;
import lk.garbage.entity.Place;
import lk.garbage.entity.PlaceDistance;
import lk.garbage.repository.PlaceDistanceRepository;
import lk.garbage.repository.PlaceRepository;
import lk.garbage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDistanceRepository placeDistanceRepository;

    @Override
    public List<PlaceDTO> getDistances() {

        //Get sensors
        List<SensorDTO> sensors = new ArrayList<>();
        sensors.add(new SensorDTO("Galle", "A1", 50, 6.053518500000001, 80.22097729999996));
        sensors.add(new SensorDTO("Colombo", "A2", 40, 7.180272, 79.88408));
        sensors.add(new SensorDTO("Colombo", "A3", 40, 7.180272, 79.88408));
        sensors.add(new SensorDTO("Colombo", "A4", 40, 7.180272, 79.88408));

        //Get all places for the location
        List<PlaceDistance> distances = placeDistanceRepository.getDistances("Galle");
        List<PlaceDistanceDTO> distanceDTOS = new ArrayList<>();
        for (PlaceDistance distance : distances) {
            PlaceDTO placeFromDTO = new PlaceDTO(distance.getPlaceFrom());
            PlaceDTO placeToDTO = new PlaceDTO(distance.getPlaceTo());
            distanceDTOS.add(new PlaceDistanceDTO(placeFromDTO, placeToDTO, distance.getDistance()));
        }

        //Filter only labels
        List<String> sensorLabels = new ArrayList<>();
        for (SensorDTO sensor : sensors) {
            sensorLabels.add(sensor.getLabel());
        }

        //Remove duplicate labels of places
        Set<String> placesInArea = new HashSet<>();

        for (PlaceDistance distance : distances) {
            placesInArea.add(distance.getPlaceFrom().getLabel());
            placesInArea.add(distance.getPlaceTo().getLabel());
        }

        placesInArea.removeAll(sensorLabels);

        distanceDTOS.removeAll(new ArrayList<>(placesInArea));

        System.out.println(distanceDTOS);

        for (int i = 0; i < sensors.size(); i++) {

            ArrayList<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>(distanceDTOS);
            System.out.println(Collections.singletonList(sensors.get(i).getLabel()));
            placeDistanceDTOS.retainAll(Collections.singletonList(sensors.get(i).getLabel()));

            
            System.out.println(placeDistanceDTOS);
        }

        return null;
    }
}
