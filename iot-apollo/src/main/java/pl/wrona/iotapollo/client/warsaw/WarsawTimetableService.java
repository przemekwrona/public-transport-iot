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
        private WarsawDepartures warsawDeparture;

    }

    public List<WarsawDepartures> getTimetable(float lat, float lon, String line, String brigade) {
        WarsawStop closestStop = warsawStopService.getClosestStop(lat, lon, line);

        return warsawApiService.getTimetable(closestStop.getGroup(), closestStop.getSlupek(), line).stream()
                .filter(departure -> brigade.equals(departure.getBrigade()))
                .collect(Collectors.toList());
    }

    public WarsawDepartures getTimetable(LocalTime time, float lat, float lon, String line, String brigade) {
        return getTimetable(lat, lon, line, brigade).stream()
                .map(departure -> WarsawDepartureTimeRange.builder()
                        .durationBetweenDepartureAndTimetable(Math.abs(Duration.between(time, departure.getTime()).getSeconds()))
                        .warsawDeparture(departure)
                        .build())
                .min(Comparator.comparing(WarsawDepartureTimeRange::getDurationBetweenDepartureAndTimetable))
                .map(WarsawDepartureTimeRange::getWarsawDeparture)
                .orElse(null);
    }

    public ResponseEntity<Timetable> getTimetableResponse(OffsetDateTime time, float lat, float lon, String line, String brigade) {
        WarsawDepartures warsawDeparture = getTimetable(time.toLocalTime(), lat, lon, line, brigade);

        return ResponseEntity.ok(new Timetable()
                .line(line)
                .brigade(brigade)
                .timetableDateTime(LocalDateTime.of(LocalDate.now(), warsawDeparture.getTime()).atOffset(ZoneOffset.UTC))
                .arrivalDateTime(time)
                .direction(warsawDeparture.getDirection())
                .route(warsawDeparture.getRoute()));
    }


}
