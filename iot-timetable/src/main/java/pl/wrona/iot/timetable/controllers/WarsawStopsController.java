package pl.wrona.iot.timetable.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.apollo.api.StopsApi;
import pl.wrona.iot.apollo.api.model.FinalStop;
import pl.wrona.iot.timetable.client.warsaw.WarsawStopService;
import pl.wrona.iot.timetable.services.WarsawFinalStopService;
import pl.wrona.iot.timetable.client.warsaw.WarsawStop;

import java.util.List;

@RestController
@AllArgsConstructor
public class WarsawStopsController implements StopsApi {

    private final WarsawStopService warsawStopService;
    private final WarsawFinalStopService warsawFinalStopService;

    @GetMapping("/stops")
    public List<WarsawStop> getWarsawStops() {
        return warsawStopService.getStops();
    }

    @Override
    public ResponseEntity<List<FinalStop>> getFinalStops() {
        return warsawFinalStopService.findAll();
    }

}
