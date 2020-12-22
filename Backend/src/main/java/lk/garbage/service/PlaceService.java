package lk.garbage.service;

import lk.garbage.dto.BestPathDTO;

import java.util.List;

public interface PlaceService {

    void getShortestPath(String enter);

//    void notifyBinStatus();

    void binStatus(String label, double weight);

}
