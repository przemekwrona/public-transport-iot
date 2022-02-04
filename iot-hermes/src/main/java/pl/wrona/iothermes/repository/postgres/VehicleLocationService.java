package pl.wrona.iothermes.repository.postgres;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.iothermes.model.postgres.VehicleLocation;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleLocationService {

    private final VehiclesLocationRepository vehiclesLocationRepository;

    public void updateVehicles(List<pl.wrona.iothermes.model.VehicleLocation> vehicles) {
        List<VehicleLocation> vehicleLocations = vehicles.stream()
                .map(this::build)
                .collect(Collectors.toList());

        vehiclesLocationRepository.saveAll(vehicleLocations);
    }

    private VehicleLocation build(pl.wrona.iothermes.model.VehicleLocation vehicleLocation) {
        return VehicleLocation.builder()
                .cityCode(vehicleLocation.getCityCode())
                .vehicleType(vehicleLocation.getVehicleType())
                .vehicleNumber(vehicleLocation.getVehicleNumber())
                .line(vehicleLocation.getLine())
                .lon(vehicleLocation.getLon())
                .lat(vehicleLocation.getLat())
                .brigade(vehicleLocation.getBrigade())
                .time(vehicleLocation.getTime())
                .build();
    }

    public void deleteVehicleLocationsByTimeBefore() {
        LocalDateTime dayAgoDateTime = LocalDateTime.now().minusDays(1L);
        vehiclesLocationRepository.deleteVehicleLocationsByTimeBefore(dayAgoDateTime.toInstant(ZoneOffset.UTC));
    }

}
