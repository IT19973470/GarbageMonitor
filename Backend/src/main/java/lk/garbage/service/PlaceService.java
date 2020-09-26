package lk.garbage.service;

import lk.garbage.dto.BestPathDTO;

import java.util.List;

public interface PlaceService {

    BestPathDTO getShortestPath(String enter);
}
