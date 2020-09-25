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
    public List<PlaceDistanceDTO> getShortestPath(String enter) {

        List<PlaceDistanceDTO> shortestPath = new ArrayList<>();//The shortest path

        //Get sensors
        List<SensorDTO> sensors = new ArrayList<>();
        sensors.add(new SensorDTO("A1", 50));
        sensors.add(new SensorDTO("A2", 40));
        sensors.add(new SensorDTO("A3", 40));
        sensors.add(new SensorDTO("A4", 40));

        //Filter only labels
        List<String> sensorLabels = new ArrayList<>();
        for (SensorDTO sensor : sensors) {
            sensorLabels.add(sensor.getLabel());
        }

        if (sensorLabels.contains(enter)) {

            //Get all places for the location
            List<PlaceDistance> distances = placeDistanceRepository.getDistances("Colombo");
            List<PlaceDistanceDTO> distanceDTOS = new ArrayList<>();
            for (PlaceDistance distance : distances) {
                PlaceDTO placeFromDTO = new PlaceDTO(distance.getPlaceFrom());
                PlaceDTO placeToDTO = new PlaceDTO(distance.getPlaceTo());
                distanceDTOS.add(new PlaceDistanceDTO(placeFromDTO, placeToDTO, distance.getDistance()));
            }

            //Remove duplicate labels of places
            Set<String> placesInArea = new HashSet<>();
            for (PlaceDistance distance : distances) {
                placesInArea.add(distance.getPlaceFrom().getLabel());
                placesInArea.add(distance.getPlaceTo().getLabel());
            }

            placesInArea.removeAll(sensorLabels);//Get only not available sensors

            distanceDTOS.removeAll(new ArrayList<>(placesInArea));//Get available places

            int sensorsSize = sensors.size() - 1;

            while (sensorsSize > 0) {

                ArrayList<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>(distanceDTOS);
                placeDistanceDTOS.retainAll(Collections.singletonList(enter));

                Collections.sort(placeDistanceDTOS);

                shortestPath.add(placeDistanceDTOS.get(0));

                distanceDTOS.removeAll(Collections.singletonList(enter));

                if (placeDistanceDTOS.get(0).getPlaceFrom().getLabel().equals(enter)) {
                    enter = placeDistanceDTOS.get(0).getPlaceTo().getLabel();
                } else if (placeDistanceDTOS.get(0).getPlaceTo().getLabel().equals(enter)) {
                    enter = placeDistanceDTOS.get(0).getPlaceFrom().getLabel();
                }

                sensorsSize--;
                System.out.println(shortestPath);
            }
        }

        return shortestPath;
    }
}
