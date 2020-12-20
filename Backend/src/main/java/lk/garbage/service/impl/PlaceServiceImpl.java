package lk.garbage.service.impl;

import lk.garbage.dto.BestPathDTO;
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

    private List<PlaceDistanceDTO> placeDistanceDTOS;
    private List<String> sensorLabels;
    private List<PlaceDistanceDTO> shortestPath;

    @Override
    public BestPathDTO getShortestPath(String enter) {

        //==============================================================================================================

        //Get sensors
        List<SensorDTO> sensors = new ArrayList<>();
        sensors.add(new SensorDTO("A1", 50));
        sensors.add(new SensorDTO("A2", 40));
        sensors.add(new SensorDTO("A3", 40));
        sensors.add(new SensorDTO("A4", 40));
        sensors.add(new SensorDTO("A5", 60));

        //==============================================================================================================

        //Filter only labels of sensors
        sensorLabels = new ArrayList<>();
        for (SensorDTO sensor : sensors) {
            sensorLabels.add(sensor.getLabel());
        }

        //==============================================================================================================

        //Get all places for the location(Hibernate)
        List<PlaceDistance> distances = placeDistanceRepository.getDistances("Colombo");
        placeDistanceDTOS = new ArrayList<>();
        for (PlaceDistance distance : distances) {
            PlaceDTO placeFromDTO = new PlaceDTO(distance.getPlaceFrom());
            PlaceDTO placeToDTO = new PlaceDTO(distance.getPlaceTo());
            placeDistanceDTOS.add(new PlaceDistanceDTO(placeFromDTO, placeToDTO, distance.getDistance()));
        }

        //==============================================================================================================

        //Remove duplicate labels of labels(Hibernate)
        Set<String> placesInArea = new HashSet<>();
        for (PlaceDistance distance : distances) {
            placesInArea.add(distance.getPlaceFrom().getLabel());
            placesInArea.add(distance.getPlaceTo().getLabel());
        }

        placesInArea.removeAll(sensorLabels);//Get only not available sensors(Hibernate)
        placeDistanceDTOS.removeAll(Collections.singletonList(placesInArea));//Get available places(Hibernate)(Clean)

        //==============================================================================================================

        calculateThePath(enter);

        //==============================================================================================================

        BestPathDTO bestPathDTO = new BestPathDTO();
//        List<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>();
//        for (int i = 0; i < lowestPath.path.length - 1; i++) {
//            placeDistanceDTOS.add(getPlaceDistanceDTO(lowestPath.path[i], lowestPath.path[i + 1]));
//        }
//        bestPathDTO.setPlaceDistances(placeDistanceDTOS);
//        bestPathDTO.setDistance(lowestPath.distance);

        return bestPathDTO;
    }

    //------------------------------------------------------------------------------------------------------------------

    private String[] swapValues(String[] arr, int v1, int v2) {
        String[] newArr = arr.clone();
        String temp = newArr[v1];
        newArr[v1] = newArr[v2];
        newArr[v2] = temp;
        return newArr;
    }

    //------------------------------------------------------------------------------------------------------------------

    private int getFactorial(int val) {
        int factorial = 1;
        for (int i = val; i > 1; i--) {
            factorial *= i;
        }
        return factorial;
    }

    //------------------------------------------------------------------------------------------------------------------

    private double getDistance(String v1, String v2) {
        boolean found=false;
        double distance = 0;
//        if (v1.equals(v2)) {
//            return distance;
//        }
        for (int i = 0; i < placeDistanceDTOS.size(); i++) {
            if ((placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v1) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v2)) || (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v2) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v1))) {
                distance = placeDistanceDTOS.get(i).getDistance();
                found=true;
                break;
            }
        }
        if(!found){
            System.out.println(v1+" "+v2);
        }
        return distance;
    }

    private PlaceDistanceDTO getPlaceDistanceDTO(String v1, String v2) {
        for (int i = 0; i < placeDistanceDTOS.size(); i++) {
            if (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v1) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v2)) {
                return placeDistanceDTOS.get(i);
            }

            if (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v2) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v1)) {
                PlaceDTO placeDTOTemp = placeDistanceDTOS.get(i).getPlaceFrom();
                placeDistanceDTOS.get(i).setPlaceFrom(placeDistanceDTOS.get(i).getPlaceTo());
                placeDistanceDTOS.get(i).setPlaceTo(placeDTOTemp);
                return placeDistanceDTOS.get(i);
            }
        }
        return null;
    }

    private void swapPlaceFromTo(PlaceDistanceDTO placeDistanceDTO) {
        PlaceDTO placeDTOTemp = placeDistanceDTO.getPlaceFrom();
        placeDistanceDTO.setPlaceFrom(placeDistanceDTO.getPlaceTo());
        placeDistanceDTO.setPlaceTo(placeDTOTemp);
    }

    //------------------------------------------------------------------------------------------------------------------

    private class PathDTO {
        String[] path;
        double distance;

        private PathDTO(String[] path, double distance) {
            this.path = path;
            this.distance = distance;
        }
    }

    private void calculateThePath(String enter) {
//        int sensorsSize = sensorLabels.size() - 1;
        shortestPath = new ArrayList<>();

//        while (sensorsSize > 0) {
//
//            List<PlaceDistanceDTO> distanceDTOS = new ArrayList<>(placeDistanceDTOS);
//            distanceDTOS.retainAll(Collections.singletonList(enter));
////
//            Collections.sort(distanceDTOS);
//
//            shortestPath.add(distanceDTOS.get(0));
//
////            if (distanceDTOS.get(0).getPlaceTo().equals(enter)) {
////                swapPlaceFromTo(distanceDTOS.get(0));
////            }
//
//            placeDistanceDTOS.removeAll(Collections.singletonList(enter));
//
//            if (distanceDTOS.get(0).getPlaceFrom().getLabel().equals(enter)) {
//                enter = distanceDTOS.get(0).getPlaceTo().getLabel();
//            } else if (distanceDTOS.get(0).getPlaceTo().getLabel().equals(enter)) {
//                swapPlaceFromTo(distanceDTOS.get(0));
//                enter = distanceDTOS.get(0).getPlaceTo().getLabel();
//            }
//
//            sensorsSize--;
//            System.out.println(shortestPath);
//        }
//        System.out.println(shortestPath);
//        String[][] possiblePaths = new String[sensorLabels.size()][sensorLabels.size()];
//        String start;
//        for (int i = 0; i < possiblePaths.length; i++) {
//            start = sensorLabels.get(i);
//            for (int j = i; j < possiblePaths[i].length; j++) {
//                double distance = getDistance(start, sensorLabels.get(j));
//                possiblePaths[i][j] = distance != 0 ? (i + 1) + "-" + (j + 1) + "-" + distance : null;
////                for (int k = 0; k <= i; k++) {
////                    if (possiblePaths[k][j] == null) {
////                        possiblePaths[i][j] = distance != 0 ? (i + 1) + "-" + (j + 1) + "-" + distance : null;
////                    } else if (distance != 0) {
////                        if (Double.parseDouble(possiblePaths[k][j].split("-")[2]) > distance) {
////                            possiblePaths[k][j] = null;
////                            possiblePaths[i][j] = distance != 0 ? (i + 1) + "-" + (j + 1) + "-" + distance : null;
////                        }
////                    }
////                }
//
//            }
//        }
//
//        System.out.println(possiblePaths);

        //Temporary remove entrance
        boolean sensorRemovedTemp = false;
        if (sensorLabels.contains(enter)) {
            sensorRemovedTemp = sensorLabels.removeAll(Collections.singletonList(enter));
        }

        //Get all possible paths
        int factorial = getFactorial(sensorLabels.size());

        String[][] possiblePaths = new String[factorial][];
        possiblePaths[0] = sensorLabels.toArray(new String[sensorLabels.size()]);
        int count = 1;
        int len;
        for (int i = 0; i < sensorLabels.size(); i++) {
            len = count;
            for (int k = 0; k < len; k++) {
                for (int j = i + 1; j < sensorLabels.size(); j++) {
                    possiblePaths[count++] = swapValues(possiblePaths[k], i, j);
                }
            }
        }

        //Re add the entrance for paths
        if (sensorRemovedTemp) {
            String tempArr[];
            for (int i = 0; i < possiblePaths.length; i++) {
                tempArr = possiblePaths[i].clone();
                possiblePaths[i] = new String[sensorLabels.size() + 1];
                possiblePaths[i][0] = enter;
                for (int j = 0; j < tempArr.length; j++) {
                    possiblePaths[i][j + 1] = tempArr[j];
                }
            }
        }

        System.out.println(possiblePaths);

        //==============================================================================================================

        //Get distance for all possible paths
        List<PlaceServiceImpl.PathDTO> pathDTOS = new ArrayList<>();
        for (int i = 0; i < possiblePaths.length; i++) {
            double totalDistance = 0;
            for (int j = 0; j < possiblePaths[i].length - 1; j++) {
                totalDistance += getDistance(possiblePaths[i][j], possiblePaths[i][j + 1]);
            }
            pathDTOS.add(new PathDTO(possiblePaths[i], totalDistance));
        }

        System.out.println(pathDTOS);
//
//        //==============================================================================================================
//
        //Get the shortest distance
        PlaceServiceImpl.PathDTO lowestPath = pathDTOS.get(0);
        for (int i = 1; i < pathDTOS.size(); i++) {
            if (pathDTOS.get(i).distance < lowestPath.distance) {
                lowestPath = pathDTOS.get(i);
            }
        }

        System.out.println(pathDTOS);
        System.out.println(lowestPath);
    }
}


//            int sensorsSize = sensors.size() - 1;
//
//            while (sensorsSize > 0) {
//
//                ArrayList<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>(placeDistanceDTOS);
//                placeDistanceDTOS.retainAll(Collections.singletonList(enter));
//
//                Collections.sort(placeDistanceDTOS);
//
//                shortestPath.add(placeDistanceDTOS.get(0));
//
//                placeDistanceDTOS.removeAll(Collections.singletonList(enter));
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