package lk.garbage.controller;

import lk.garbage.dto.BestPathDTO;
import lk.garbage.dto.PlaceDistanceDTO;
import lk.garbage.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping(value = "/getShortestPath/{enter}")
    public BestPathDTO getShortestPathWithEntrance(@PathVariable String enter) {
        return placeService.getShortestPath(enter);
    }

    @GetMapping(value = "/getShortestPath")
    public BestPathDTO getShortestPath() {
        placeService.notifyBinStatus();
        return placeService.getShortestPath("No");
    }

//    @MessageMapping("/hello1")
//    @SendTo("/topic/greetings1")
//    public PlaceDistanceDTO binUpdate() throws Exception {
////        Thread.sleep(1000); // simulated delay
//        System.out.println(123);
//        PlaceDistanceDTO placeDistanceDTO = new PlaceDistanceDTO();
//        placeDistanceDTO.setDistance(45);
//        return placeDistanceDTO;
//    }
}
