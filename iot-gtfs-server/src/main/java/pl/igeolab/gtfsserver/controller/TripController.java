package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.TripsApi;
import org.igeolab.iot.gtfs.server.api.model.Trips;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.igeolab.gtfsserver.service.TripService;

@RestController
@AllArgsConstructor
public class TripController implements TripsApi {
    private final TripService tripService;

    @Override
    public ResponseEntity<Trips> getTrips(String line) {
        return ResponseEntity.ok(tripService.getTrips(line));
    }
}
