package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.StopsApi;
import org.igeolab.iot.gtfs.server.api.model.Stops;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.igeolab.gtfsserver.service.StopService;

@RestController
@AllArgsConstructor
public class StopController implements StopsApi {
    private final StopService stopService;

    @Override
    public ResponseEntity<Stops> getStops(String agencyCode) {
        return ResponseEntity.ok(stopService.getStops());
    }
}
