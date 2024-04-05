package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.AgencyApi;
import org.igeolab.iot.gtfs.server.api.model.Agencies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.igeolab.gtfsserver.service.AgencyService;

@RestController
@AllArgsConstructor
public class AgencyController implements AgencyApi {

    private final AgencyService agencyService;

    @Override
    public ResponseEntity<Agencies> getAgencies() {
        return ResponseEntity.ok(agencyService.getAgencies());
    }
}
