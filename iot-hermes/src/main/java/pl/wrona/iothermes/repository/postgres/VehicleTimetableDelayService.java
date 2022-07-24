package pl.wrona.iothermes.repository.postgres;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.Timetable;
import pl.wrona.iothermes.client.apollo.ApolloTimetableService;
import pl.wrona.iothermes.model.VehicleLocation;
import pl.wrona.iothermes.model.postgres.VehicleTimetableDelay;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleTimetableDelayService {

    private final ZoneId WARSAW_ZONE = ZoneId.of("Europe/Warsaw");

    private final VehicleTimetableDelayRepository vehicleDelayRepository;
    private final ApolloTimetableService apolloTimetableService;

    public void updateVehiclesWithDelay(List<VehicleLocation> vehicles) {
        List<VehicleTimetableDelay> vehicleDelays = vehicles.stream().parallel()
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
        Optional<Timetable> warsawTimetable = Optional.ofNullable(timetable);

        return VehicleTimetableDelay.builder()
                .cityCode(vehicleLocation.getCityCode())
                .vehicleType(vehicleLocation.getVehicleType())
                .vehicleNumber(vehicleLocation.getVehicleNumber())
                .line(vehicleLocation.getLine())
                .lon(vehicleLocation.getLon())
                .lat(vehicleLocation.getLat())
                .brigade(vehicleLocation.getBrigade())
                .time(vehicleLocation.getTime())
                .isOnStop(warsawTimetable.map(Timetable::getIsOnStop).orElse(false))
                .hasTimetable(warsawTimetable.map(Timetable::getHasTimetable).orElse(false))
                .stopId(warsawTimetable.map(Timetable::getStopId).orElse(""))
                .stopNumber(warsawTimetable.map(Timetable::getStopNumber).orElse(""))
                .stopName(warsawTimetable.map(Timetable::getStopName).orElse(""))
                .timetableDepartureDate(warsawTimetable
                        .map(Timetable::getTimetableDepartureDate)
                        .map(offset -> offset.atZoneSameInstant(WARSAW_ZONE))
                        .map(ZonedDateTime::toLocalDateTime)
                        .orElse(null))
                .build();
    }

    @Transactional
    public void deleteVehicleLocationsByTimeBefore() {
        LocalDateTime dayAgoDateTime = LocalDateTime.now().minusDays(1L);
        vehicleDelayRepository.deleteVehicleTimetableDelaysByTimeBefore(dayAgoDateTime);
    }

}
