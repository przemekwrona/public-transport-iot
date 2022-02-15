package pl.wrona.iotapollo.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.Timetable;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WarsawTimetableService {

    private final WarsawApiService warsawApiService;
    private final WarsawStopService warsawStopService;

    @Data
    @Builder
    private static class WarsawDepartureTimeRange {
        private long durationBetweenDepartureAndTimetable;
        private WarsawStopDepartures warsawDeparture;

    }

    public List<WarsawStopDepartures> getTimetable(float lon, float lat, String line, String brigade) {
        WarsawStop closestStop = warsawStopService.getClosestStop(lon, lat, line);

        List<WarsawStopDepartures> warsawStopDepartures = warsawApiService.getTimetable(closestStop.getGroup(), closestStop.getSlupek(), line).stream()
                .filter(departure -> brigade.equals(departure.getBrigade()))
                .map(departure -> WarsawStopDepartures.builder()
                        .line(line)
                        .brigade(departure.getBrigade())
                        .direction(departure.getDirection())
                        .route(departure.getRoute())
                        .time(departure.getTime())
                        .stopId(closestStop.getGroup())
                        .stopNumber(closestStop.getSlupek())
                        .stopName(closestStop.getName())
                        .stopLon(closestStop.getLon())
                        .stopLat(closestStop.getLat())
                        .stopDirection(closestStop.getDirection())
                        .build())
                .collect(Collectors.toList());

        if (warsawStopDepartures.isEmpty()) {
            log.error("The closes stop is NULL. Line {} and brigade {}", line, brigade);
        }

        return warsawStopDepartures;
    }

    public WarsawStopDepartures getTimetable(LocalTime time, float lon, float lat, String line, String brigade) {
        return getTimetable(lon, lat, line, brigade).stream()
                .map(departure -> WarsawDepartureTimeRange.builder()
                        .durationBetweenDepartureAndTimetable(Math.abs(Duration.between(time, departure.getTime()).getSeconds()))
                        .warsawDeparture(departure)
                        .build())
                .min(Comparator.comparing(WarsawDepartureTimeRange::getDurationBetweenDepartureAndTimetable))
                .map(WarsawDepartureTimeRange::getWarsawDeparture)
                .orElse(null);
    }

    public ResponseEntity<Timetable> getTimetableResponse(OffsetDateTime time, float lon, float lat, String line, String brigade) {
        WarsawStopDepartures warsawDeparture = getTimetable(time.toLocalTime(), lon, lat, line, brigade);

//        if (warsawDeparture == null) {
        log.info("Warsaw departure. Time {}, lon {}, lat {}, line {}, brigade {}. {}", time, lon, lat, line, brigade, warsawDeparture);
//        }
        return ResponseEntity.ok(new Timetable()
                .line(line)
                .brigade(brigade)
                .timetableTime(LocalDateTime.of(LocalDate.now(), warsawDeparture.getTime()).atOffset(ZoneOffset.UTC))
                .arrivalTime(time)
                .vehicleDirection(warsawDeparture.getDirection())
                .vahicleRoute(warsawDeparture.getRoute())
                .stopId(warsawDeparture.getStopId())
                .stopNumber(warsawDeparture.getStopNumber())
                .stopName(warsawDeparture.getStopName())
                .stopLat(warsawDeparture.getStopLat())
                .stopLon(warsawDeparture.getStopLon())
                .stopDirection(warsawDeparture.getStopDirection()));
    }


}
