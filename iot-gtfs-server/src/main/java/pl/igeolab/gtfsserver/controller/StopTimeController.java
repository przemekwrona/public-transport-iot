package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.StopTimeApi;
import org.igeolab.iot.gtfs.server.api.model.Departures;
import org.igeolab.iot.gtfs.server.api.model.StopTimes;
import org.igeolab.iot.gtfs.server.api.model.Trips;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.igeolab.gtfsserver.service.StopTimeService;

@RestController
@AllArgsConstructor
public class StopTimeController implements StopTimeApi {

    private final StopTimeService stopTimeService;

    @Override
    public ResponseEntity<Departures> getDeparturesByStopId(String agencyCode, String stopId) {
        return ResponseEntity.ok(stopTimeService.getDeparturesByStopId(agencyCode, stopId));
    }
}
