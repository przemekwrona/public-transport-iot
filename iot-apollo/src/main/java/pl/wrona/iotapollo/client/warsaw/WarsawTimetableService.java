package pl.wrona.iotapollo.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.EdgeTimetable;
import pl.wrona.iot.apollo.api.model.EdgeTimetables;
import pl.wrona.iot.apollo.api.model.Timetable;
import pl.wrona.iotapollo.services.WarsawFinalStopService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class WarsawTimetableService {

    public final ZoneId WARSAW_ZONE_ID = ZoneId.of("Europe/Warsaw");

    private final WarsawApiService warsawApiService;
    private final WarsawStopService warsawStopService;
    private WarsawFinalStopService warsawStopDirectionService;

    @Data
    @Builder
    private static class WarsawDepartureTimeRange {
        private long durationBetweenDepartureAndTimetable;
        private WarsawStopDepartures warsawDeparture;

        boolean isLowerOrEqual1h() {
            return durationBetweenDepartureAndTimetable <= 60 * 60;
        }

    }

    @Data
    @Builder
    private static class WarsawStopWithTimetable {
        private WarsawStop warsawStop;
        private List<WarsawDepartures> departures;

    }

    public List<WarsawStopDepartures> getDepartures(float lat, float lon, String line, String brigade) {
        return warsawStopService.getStopsInAreaOf35m(lat, lon, line).stream()
                .map(stop -> WarsawStopWithTimetable.builder()
                        .warsawStop(stop)
                        .departures(warsawApiService.getTimetable(stop.getGroup(), stop.getSlupek(), line))
                        .build())
                .flatMap(stop -> stop.getDepartures().stream()
                        .filter(timetable -> timetable.getBrigade().equals(brigade))
                        .peek(timetable -> warsawStopDirectionService.addDirection(line, timetable.getDirection()))
                        .map(timetable -> WarsawStopDepartures.builder()
                                .line(line)
                                .brigade(brigade)
                                .isOnStop(true)
                                .isOnFirstStop(warsawStopDirectionService.isDirection(line, stop.getWarsawStop().getName()))
                                .hasTimetable(true)
                                .route(timetable.getRoute())
                                .timetableDeparture(timetable.getTime())
                                .stopId(stop.getWarsawStop().getGroup())
                                .stopNumber(stop.getWarsawStop().getSlupek())
                                .stopName(stop.getWarsawStop().getName())
                                .stopLat(stop.getWarsawStop().getLat())
                                .stopLon(stop.getWarsawStop().getLon())
                                .stopDistance(stop.getWarsawStop().distance(lat, lon))
                                .vehicleDirection(timetable.getDirection())
                                .build()))
                .collect(Collectors.toList());
    }

    public List<WarsawStopDepartures> getDepartures(LocalTime time, float lat, float lon, String line, String brigade) {
        return getDepartures(lat, lon, line, brigade).stream()
                .map(departure -> WarsawDepartureTimeRange.builder()
                        .durationBetweenDepartureAndTimetable(Math.abs(Duration.between(time, departure.getTimetableDeparture()).getSeconds()))
                        .warsawDeparture(departure)
                        .build())
                .sorted(Comparator.comparing(WarsawDepartureTimeRange::getDurationBetweenDepartureAndTimetable))
                .filter(WarsawDepartureTimeRange::isLowerOrEqual1h)
                .map(WarsawDepartureTimeRange::getWarsawDeparture)
                .collect(Collectors.toList());
    }

    public WarsawStopDepartures getDeparture(LocalTime time, float lat, float lon, String line, String brigade) {
        return getDepartures(time, lat, lon, line, brigade).stream()
                .limit(1)
                .findFirst()
                .orElse(WarsawStopDepartures.builder()
                        .line(line)
                        .brigade(brigade)
                        .isOnStop(false)
                        .hasTimetable(false)
                        .stopId("")
                        .stopNumber("")
                        .stopName("")
                        .stopDistance(0L)
                        .build());
    }

    public ResponseEntity<Timetable> getTimetableResponse(OffsetDateTime time, float lat, float lon, String line, String brigade) {
        WarsawStopDepartures warsawDeparture = getDeparture(time.toLocalTime(), lat, lon, line, brigade);

        return ResponseEntity.ok(new Timetable()
                .line(line)
                .brigade(brigade)
                .arrivalTime(time)
                .vahicleRoute(warsawDeparture.getRoute())
                .vehicleDirection(warsawDeparture.getVehicleDirection())
                .isOnStop(warsawDeparture.isOnStop())
                .isOnFirstStop(warsawDeparture.isOnFirstStop())
                .hasTimetable(warsawDeparture.isHasTimetable())
                .stopId(warsawDeparture.getStopId())
                .stopNumber(warsawDeparture.getStopNumber())
                .stopName(warsawDeparture.getStopName())
                .timetableDepartureDate(Optional.ofNullable(warsawDeparture.getTimetableDeparture())
                        .map(timetableDate -> warsawDeparture.getTimetableDeparture()
                                .atOffset(WARSAW_ZONE_ID.getRules().getOffset(time.toInstant())))
                        .orElse(null))
                .stopLat(warsawDeparture.getStopLat())
                .stopLon(warsawDeparture.getStopLon())
                .stopDistance(BigDecimal.valueOf(warsawDeparture.getStopDistance())));
    }

    public ResponseEntity<EdgeTimetables> getEdgeTimetables() {
        return ResponseEntity.ok(new EdgeTimetables()
                .mornigDeparture(buildEdgeTimetables(getMorningEdgeTimetable()))
                .eveningDeparture(buildEdgeTimetables(getEveningEdgeTimetable())));
    }

    public EdgeTimetable buildEdgeTimetables(WarsawDepartures warsawDepartures) {
        return new EdgeTimetable()
                .line(warsawDepartures.getLine())
                .departureDate(warsawDepartures.getTime().atOffset(WARSAW_ZONE_ID.getRules().getOffset(warsawDepartures.getTime())));
    }

    public WarsawDepartures getMorningEdgeTimetable() {
        return warsawStopService.getStops().stream()
                .map(warsawStop -> warsawStopService.getLinesOnStop(warsawStop.getGroup(), warsawStop.getSlupek()).getLines().stream()
                        .map(line -> warsawApiService.getTimetable(warsawStop.getGroup(), warsawStop.getSlupek(), line))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .min(Comparator.comparing(WarsawDepartures::getTime))
                .orElse(null);
    }


    public WarsawDepartures getEveningEdgeTimetable() {
        return warsawStopService.getStops().stream()
                .map(warsawStop -> warsawStopService.getLinesOnStop(warsawStop.getGroup(), warsawStop.getSlupek()).getLines().stream()
                        .map(line -> warsawApiService.getTimetable(warsawStop.getGroup(), warsawStop.getSlupek(), line))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .max(Comparator.comparing(WarsawDepartures::getTime))
                .orElse(null);
    }

}
