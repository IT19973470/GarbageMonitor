package lk.garbage.service.impl;

import lk.garbage.dto.BestPathDTO;
import lk.garbage.dto.PlaceDTO;
import lk.garbage.dto.PlaceDistanceDTO;
import lk.garbage.dto.SensorDTO;
import lk.garbage.entity.PlaceDistance;
import lk.garbage.repository.PlaceDistanceRepository;
import lk.garbage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDistanceRepository placeDistanceRepository;
    @Autowired
    private SimpMessagingTemplate webSocket;

    private static double MAX_WEIGHT = 70;
    private List<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>();
    private List<SensorDTO> sensors = new ArrayList<>();
    private static int BINS_COUNT = 5;
    private int ongoingBinsCount = 0;
    private static double MIN_WEIGHT = 10;

    @Override
    public void getShortestPath(String enter) {
        sensors = new ArrayList<>();
        ongoingBinsCount = 0;
        System.out.println("00");

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://192.168.1.8/get_weight");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
            connection.getResponseCode();
//            connection.setRequestProperty("Content-Length",
//                    Integer.toString(urlParameters.getBytes().length));
//            connection.setRequestProperty("Content-Language", "en-US");

//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            //Send request
//            DataOutputStream wr = new DataOutputStream(
//                    connection.getOutputStream());
//            wr.writeBytes(urlParameters);
//
//            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        calcShortestPath(enter);
        //Notify nodes
    }

    private void calcShortestPath(String enter) {

        //==============================================================================================================

        //Get sensors
//        sensors = new ArrayList<>();
//        sensors.add(new SensorDTO("A1", 50.5));
//        sensors.add(new SensorDTO("A2", 68));
//        sensors.add(new SensorDTO("A3", 41));
//        sensors.add(new SensorDTO("A4", 32));
//        sensors.add(new SensorDTO("A5", 46));

        //==============================================================================================================

        //Filter only labels of sensors
        List<String> sensorLabels = new ArrayList<>();
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

        //==============================================================================================================

        //Get the shortest distance
        PlaceServiceImpl.PathDTO lowestPath = pathDTOS.get(0);
        for (int i = 1; i < pathDTOS.size(); i++) {
            if (pathDTOS.get(i).distance < lowestPath.distance) {
                lowestPath = pathDTOS.get(i);
            }
        }

        BestPathDTO bestPathDTO = new BestPathDTO();
        List<PlaceDistanceDTO> placeDistanceDTOS = new ArrayList<>();
        for (int i = 0; i < lowestPath.path.length - 1; i++) {
            placeDistanceDTOS.add(getPlaceDistanceDTO(lowestPath.path[i], lowestPath.path[i + 1]));
        }
        bestPathDTO.setPlaceDistances(placeDistanceDTOS);
        bestPathDTO.setDistance(lowestPath.distance);
//        this.placeDistanceDTOS = new ArrayList<>();
        this.placeDistanceDTOS.addAll(placeDistanceDTOS);

        PlaceDistanceDTO placeDistanceDTO = new PlaceDistanceDTO();
        PlaceDTO placeFromDTO = placeDistanceDTOS.get(placeDistanceDTOS.size() - 1).getPlaceTo();
        placeDistanceDTO.setPlaceFrom(placeFromDTO);
        this.placeDistanceDTOS.add(placeDistanceDTO);

        webSocket.convertAndSend("/topic/greetings2", bestPathDTO);
//        return bestPathDTO;
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
        double distance = 0;
        for (int i = 0; i < placeDistanceDTOS.size(); i++) {
            if ((placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v1) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v2)) || (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v2) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v1))) {
                distance = placeDistanceDTOS.get(i).getDistance();
            }
        }
        return distance;
    }

    private PlaceDistanceDTO getPlaceDistanceDTO(String v1, String v2) {
        for (int i = 0; i < placeDistanceDTOS.size(); i++) {
            if (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v1) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v2)) {
                PlaceDistanceDTO placeDistanceDTO = placeDistanceDTOS.get(i);
                placeDistanceDTO.getPlaceFrom().setWeight(getWeight(placeDistanceDTO.getPlaceFrom().getLabel()) / MAX_WEIGHT * 100);
                placeDistanceDTO.getPlaceTo().setWeight(getWeight(placeDistanceDTO.getPlaceTo().getLabel()) / MAX_WEIGHT * 100);
                return placeDistanceDTO;
            }

            if (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(v2) && placeDistanceDTOS.get(i).getPlaceTo().getLabel().equals(v1)) {
                PlaceDTO placeDTOTemp = placeDistanceDTOS.get(i).getPlaceFrom();
                placeDistanceDTOS.get(i).setPlaceFrom(placeDistanceDTOS.get(i).getPlaceTo());
                placeDistanceDTOS.get(i).setPlaceTo(placeDTOTemp);
                PlaceDistanceDTO placeDistanceDTO = placeDistanceDTOS.get(i);
                placeDistanceDTO.getPlaceFrom().setWeight(getWeight(placeDistanceDTO.getPlaceFrom().getLabel()) / MAX_WEIGHT * 100);
                placeDistanceDTO.getPlaceTo().setWeight(getWeight(placeDistanceDTO.getPlaceTo().getLabel()) / MAX_WEIGHT * 100);
                return placeDistanceDTO;
            }
        }
        return null;
    }

    private double getWeight(String label) {
        for (SensorDTO sensor : sensors) {
            if (sensor.getLabel().equals(label)) {
                return sensor.getWeight();
            }
        }
        return 0;
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

    //-----------------------------------Notify----------------------------------

    private void setBinToEmpty(int pos) {//0 - full, 1 - current, 2 - empty
        PlaceDistanceDTO placeDistanceDTO = new PlaceDistanceDTO();
        PlaceDTO placeFromDTO = new PlaceDTO();
        PlaceDTO placeToDTO = new PlaceDTO();
        placeDistanceDTO.setPlaceFrom(placeFromDTO);
        placeDistanceDTO.setPlaceTo(placeToDTO);
        placeFromDTO.setBinEmpty(2);
        placeToDTO.setBinEmpty(1);
        placeFromDTO.setLabel(placeDistanceDTOS.get(pos).getPlaceFrom().getLabel());

        if (pos < placeDistanceDTOS.size() - 1) {
            placeToDTO.setLabel(placeDistanceDTOS.get(pos + 1).getPlaceFrom().getLabel());
        }

        webSocket.convertAndSend("/topic/greetings1", placeDistanceDTO);
    }

    @Override
    public void binStatus(String label, double weight) {
        System.out.println(label);
        if (weight == 0) {
            for (int i = 0; i < placeDistanceDTOS.size(); i++) {
                if (placeDistanceDTOS.get(i).getPlaceFrom().getLabel().equals(label)) {
                    setBinToEmpty(i);
                }
            }
        } else if (weight > 0) {
            if (weight > MIN_WEIGHT) {
                ongoingBinsCount++;
                sensors.add(new SensorDTO(label, weight));
            } else {
                ongoingBinsCount++;
            }
            if (ongoingBinsCount == BINS_COUNT) {
                calcShortestPath("No");
            }
        }

//        System.out.println(placeDistanceDTOS);
//        webSocket.convertAndSend("/topic/greetings1", placeDistanceDTO);
    }

}