package lk.garbage.controller;

import lk.garbage.dto.PlaceDistanceDTO;
import lk.garbage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping(value = "/getShortestPath/{enter}")
    public List<PlaceDistanceDTO> getShortestPathWithEntrance(@PathVariable String enter) {
        return placeService.getShortestPath(enter);
    }

    @GetMapping(value = "/getShortestPath")
    public List<PlaceDistanceDTO> getShortestPath() {
        return placeService.getShortestPath("No");
    }

}
