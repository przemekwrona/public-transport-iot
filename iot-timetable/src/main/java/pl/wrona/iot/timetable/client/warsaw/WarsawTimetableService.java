package pl.wrona.iot.timetable.client.warsaw;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.SloppyMath;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.EdgeTimetable;
import pl.wrona.iot.apollo.api.model.EdgeTimetables;
import pl.wrona.iot.apollo.api.model.Timetable;
import pl.wrona.iot.timetable.entity.TimetableRepository;
import pl.wrona.iot.timetable.entity.Timetables;
import pl.wrona.iot.timetable.services.WarsawFinalStopService;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class WarsawTimetableService {

    public final ZoneId WARSAW_ZONE_ID = ZoneId.of("Europe/Warsaw");
    public final long MINUTES_5 = 5;

    private final WarsawApiService warsawApiService;
    private final WarsawStopService warsawStopService;
    private WarsawFinalStopService warsawStopDirectionService;

    private final TimetableRepository timetableRepository;

    @Data
    @Builder
    private static class WarsawDepartureTimeRange {
        private long durationBetweenDepartureAndTimetable;
        private Timetables warsawDeparture;

        boolean isLowerOrEqual1h() {
            return durationBetweenDepartureAndTimetable <= 60 * 60;
        }

        boolean isLowerOrEqual5m() {
            return durationBetweenDepartureAndTimetable <= 5 * 60;
        }

    }

    public WarsawStopDepartures getDeparture(LocalDateTime date, float lat, float lon, String line, String brigade) {
        List<WarsawStop> stopsInAreaOf35m = warsawStopService.getStopsInAreaOf35m(lat, lon, line);

        if (stopsInAreaOf35m.isEmpty()) {
            return WarsawStopDepartures.builder().build();
        }

        Timetables timetables = stopsInAreaOf35m.stream()
                .map(stop -> warsawStopService.findTimetables(line, brigade, stop.getGroup(), stop.getSlupek(), date))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()).stream()
                .map(departure -> WarsawDepartureTimeRange.builder()
                        .durationBetweenDepartureAndTimetable(Math.abs(Duration.between(date, departure.getTimetableDepartureDate()).getSeconds()))
                        .warsawDeparture(departure)
                        .build())
                .filter(WarsawDepartureTimeRange::isLowerOrEqual1h)
                .min(Comparator.comparing(WarsawDepartureTimeRange::getDurationBetweenDepartureAndTimetable))
                .map(WarsawDepartureTimeRange::getWarsawDeparture)
                .orElse(null);

        if (Objects.isNull(timetables)) {
            return WarsawStopDepartures.builder().build();
        }

        long delay = ChronoUnit.MINUTES.between(timetables.getDepartureDate(), date);

        if (Math.abs(delay) <= MINUTES_5) {
            if (isNull(timetables.getDepartureDate())) {
                timetables.setArrivalDate(date);
                timetables.setDepartureDate(date);
            } else {
                timetables.setDepartureDate(date);
            }
            timetableRepository.save(timetables);

            return WarsawStopDepartures.builder()
                    .line(line)
                    .brigade(brigade)
                    .isOnStop(true)
//                        .isOnFirstStop(warsawStopDirectionService.isDirection(line, stop.getWarsawStop().getName()))
                    .hasTimetable(true)
//                    .route(timetable.getRoute())
                    .timetableDeparture(timetables.getTimetableDepartureDate())
                    .stopId(timetables.getStopId())
                    .stopNumber(timetables.getStopNumber())
                    .stopName(timetables.getStopName())
                    .stopLat(timetables.getLat())
                    .stopLon(timetables.getLon())
                    .stopDistance(0)
                    .vehicleDirection(timetables.getDirection())
                    .stopDistance((long) SloppyMath.haversinMeters(timetables.getLat(), timetables.getLon(), lat, lon))
                    .build();
        }

        return WarsawStopDepartures.builder().build();
    }

    @Timed(value = "iot_apollo_api_get_timetables")
    public ResponseEntity<Timetable> getTimetableResponse(OffsetDateTime time, float lat, float lon, String line, String brigade) {
        return Optional.of(getDeparture(time.toLocalDateTime(), lat, lon, line, brigade))
                .map(warsawDeparture -> new Timetable()
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
                                .map(timetableDate -> timetableDate.atOffset(WARSAW_ZONE_ID.getRules().getOffset(timetableDate)))
                                .orElse(null))
                        .stopLat(warsawDeparture.getStopLat())
                        .stopLon(warsawDeparture.getStopLon())
                        .stopDistance(BigDecimal.valueOf(warsawDeparture.getStopDistance())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.of(Optional.empty()));
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

    public boolean hasTimetable(LocalDate localDate) {
        int numberOfTimetables = timetableRepository.countTimetablesByTimetableDepartureDateBetweenAndLineNotLike(LocalDateTime.of(localDate, LocalTime.of(3, 0)), LocalDateTime.of(localDate, LocalTime.MAX), "N%");
        return numberOfTimetables > 0;
    }

}
