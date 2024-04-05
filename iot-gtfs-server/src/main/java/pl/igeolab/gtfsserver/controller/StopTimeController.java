package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.StopTimeApi;
import org.igeolab.iot.gtfs.server.api.model.Trips;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StopTimeController implements StopTimeApi {

    @Override
    public ResponseEntity<Trips> getStopTrips(String stop) {
        return null;
    }
}
