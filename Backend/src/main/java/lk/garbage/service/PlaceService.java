package lk.garbage.service;

import lk.garbage.dto.PlaceDTO;
import lk.garbage.dto.PlaceDistanceDTO;

import java.util.List;

public interface PlaceService {

    List<PlaceDistanceDTO> getShortestPath(String enter);
}
