package pl.wrona.iotapollo.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.apollo.api.StopsApi;
import pl.wrona.iot.apollo.api.model.FinalStop;
import pl.wrona.iotapollo.client.warsaw.WarsawStop;
import pl.wrona.iotapollo.client.warsaw.WarsawStopService;

import java.util.List;

@RestController
@AllArgsConstructor
public class WarsawStopsController implements StopsApi {

    private final WarsawStopService warsawStopService;

    @GetMapping("/warsaw/stops")
    public List<WarsawStop> getWarsawStops() {
        return warsawStopService.getStops();
    }


    @Override
    public ResponseEntity<FinalStop> getFinalStops() {
        return null;
    }

}
