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

    @GetMapping(value = "/shortestPath/{enter}")
    public void getShortestPathWithEntrance(@PathVariable String enter) {
        placeService.getShortestPath(enter);
    }

    @GetMapping(value = "/shortestPath")
    public void getShortestPath() {
        placeService.getShortestPath("No");
    }

    @GetMapping(value = "/binSignal/{label}/{weight}")
    public void binStatus(@PathVariable String label, @PathVariable double weight) {
        placeService.binStatus(label, weight);
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
