package lk.garbage.service.impl;

import lk.garbage.dto.PlaceDTO;
import lk.garbage.dto.PlaceDistanceDTO;
import lk.garbage.dto.SensorDTO;
import lk.garbage.entity.PlaceDistance;
import lk.garbage.repository.PlaceDistanceRepository;
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
        sensors.add(new SensorDTO("A5", 60));

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
            placesInArea.add(enter);

            distanceDTOS.removeAll(Collections.singletonList(placesInArea));//Get available places

            sensorLabels.removeAll(Collections.singletonList(enter));

            int factorial = getFactorial(sensorLabels.size());
//            int interval = factorial / sensorLabels.size();
            String[][] possiblePaths = new String[factorial][];
            possiblePaths[0] = sensorLabels.toArray(new String[sensorLabels.size()]);
            int count = 1;
            for (int i = 0; i < sensorLabels.size(); i++) {
                int len = count;
                for (int k = 0; k < len; k++) {
                    for (int j = i + 1; j < sensorLabels.size(); j++) {
                        possiblePaths[count++] = swapValues(possiblePaths[k], i, j);
                    }
                }
            }

//            List<String[]> possiblePaths = new ArrayList<>();
//            String[] sensorLabelsArr = new String[sensorLabels.size()];
//            possiblePaths.add(sensorLabels.toArray(sensorLabelsArr));
//
//            for (int i = 0; i < sensorLabels.size() - 1; i++) {
//                possiblePaths.add(swapValues(possiblePaths.get(0), 0, i + 1));
//            }

            System.out.println(possiblePaths);

//            int sensorsSize = sensors.size() - 1;
//
//            while (sensorsSize > 0) {
//
//                ArrayList<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>(distanceDTOS);
//                placeDistanceDTOS.retainAll(Collections.singletonList(enter));
//
//                Collections.sort(placeDistanceDTOS);
//
//                shortestPath.add(placeDistanceDTOS.get(0));
//
//                distanceDTOS.removeAll(Collections.singletonList(enter));
//
//                if (placeDistanceDTOS.get(0).getPlaceFrom().getLabel().equals(enter)) {
//                    enter = placeDistanceDTOS.get(0).getPlaceTo().getLabel();
//                } else if (placeDistanceDTOS.get(0).getPlaceTo().getLabel().equals(enter)) {
//                    enter = placeDistanceDTOS.get(0).getPlaceFrom().getLabel();
//                }
//
//                sensorsSize--;
//                System.out.println(shortestPath);
//            }
        }

        return shortestPath;
    }

    private String[] swapValues(String[] arr, int v1, int v2) {
        String[] newArr = arr.clone();
        String temp = newArr[v1];
        newArr[v1] = newArr[v2];
        newArr[v2] = temp;
        return newArr;
    }

    private int getFactorial(int val) {
        int factorial = 1;
        for (int i = val; i > 1; i--) {
            factorial *= i;
        }
        return factorial;
    }
}
