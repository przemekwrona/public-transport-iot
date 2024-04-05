package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.TimetableApi;
import org.igeolab.iot.gtfs.server.api.model.Timetables;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.igeolab.gtfsserver.service.TimetableService;

@RestController
@AllArgsConstructor
public class TimetableController implements TimetableApi {

    private final TimetableService timetableService;

    @Override
    public ResponseEntity<Timetables> getTimetableByStopId(String stopId) {
        return ResponseEntity.ok(timetableService.getTimetableByStopId(stopId));
    }
}
