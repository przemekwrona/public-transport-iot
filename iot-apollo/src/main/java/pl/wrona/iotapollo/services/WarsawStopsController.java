package pl.wrona.iotapollo.services;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iotapollo.client.warsaw.WarsawStop;
import pl.wrona.iotapollo.client.warsaw.WarsawStopService;

import java.util.List;

@RestController
@AllArgsConstructor
public class WarsawStopsController {

    private final WarsawStopService warsawStopService;

    @GetMapping("/warsaw/stops")
    public List<WarsawStop> getWarsawStops() {
        return warsawStopService.getStops();
    }

}
