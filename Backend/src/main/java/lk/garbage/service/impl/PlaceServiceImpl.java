package lk.garbage.service.impl;

import lk.garbage.dto.PlaceDTO;
import lk.garbage.entity.Place;
import lk.garbage.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Override
    public List<PlaceDTO> getPlaces() {
        List<PlaceDTO> places = new ArrayList<>();
        places.add(new PlaceDTO("Galle", 'A', 50, 6.053518500000001, 80.22097729999996));
        places.add(new PlaceDTO("Colombo", 'B', 40, 7.180272, 79.88408));
        places.add(new PlaceDTO("Colombo", 'C', 40, 7.180272, 79.88408));
        places.add(new PlaceDTO("Colombo", 'D', 40, 7.180272, 79.88408));

        for (PlaceDTO place : places) {
            ArrayList<Place> distancesForPlace = new ArrayList<>();
            Place place1 = new Place();
        }


        return places;
    }
}
