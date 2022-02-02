package pl.wrona.iotdemeter.warsaw;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.demeter.api.model.Location;
import pl.wrona.iotdemeter.repositories.locations.LocationsRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarsawTimetableService {

    private final LocationsRepository locationsRepository;

    public ResponseEntity<List<Location>> getLocations() {

        List<Location> vehicleLocations = locationsRepository.findByTimeAfter(LocalDateTime.now().minusSeconds(40L)).stream()
                .map(location -> new Location()
                        .key(location.getVehicleNumber())
                        .latitude(new BigDecimal(location.getLat()))
                        .longitude(new BigDecimal(location.getLon()))
                        .name(location.getLine()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(vehicleLocations);
    }

}
