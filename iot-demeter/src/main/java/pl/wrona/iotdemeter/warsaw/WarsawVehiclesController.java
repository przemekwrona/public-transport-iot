package pl.wrona.iotdemeter.warsaw;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.demeter.api.LocationsApi;
import pl.wrona.iot.demeter.api.model.Location;

import java.util.List;

@RestController
@AllArgsConstructor
public class WarsawVehiclesController implements LocationsApi {

    private final WarsawTimetableService warsawTimetableService;

    @Override
    public ResponseEntity<List<Location>> getLocations() {
        return warsawTimetableService.getLocations();
    }

}
