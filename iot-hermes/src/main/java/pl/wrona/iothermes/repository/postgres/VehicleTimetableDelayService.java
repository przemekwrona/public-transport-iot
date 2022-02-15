package pl.wrona.iothermes.repository.postgres;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.Timetable;
import pl.wrona.iothermes.client.apollo.ApolloTimetableService;
import pl.wrona.iothermes.model.VehicleLocation;
import pl.wrona.iothermes.model.postgres.VehicleTimetableDelay;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleTimetableDelayService {

    private final VehicleTimetableDelayRepository vehicleDelayRepository;
    private final ApolloTimetableService apolloTimetableService;

    public void updateVehiclesWithDelay(List<VehicleLocation> vehicles) {
        List<VehicleTimetableDelay> vehicleDelays = vehicles.stream()
                .map(vehicle -> {
                    Timetable timetable = apolloTimetableService.getTimetable(vehicle.getTime(),
                            vehicle.getLat(),
                            vehicle.getLon(),
                            vehicle.getLine(),
                            vehicle.getBrigade());

                    return build(vehicle, timetable);
                }).collect(Collectors.toList());

        vehicleDelayRepository.saveAll(vehicleDelays);
    }

    private VehicleTimetableDelay build(VehicleLocation vehicleLocation, Timetable timetable) {
        return VehicleTimetableDelay.builder()
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
        vehicleDelayRepository.deleteVehicleLocationsByTimeBefore(dayAgoDateTime.toInstant(ZoneOffset.UTC));
    }

}
