package pl.wrona.iot.timetable.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.entity.TimetableRepository;
import pl.wrona.iot.timetable.entity.Timetables;
import pl.wrona.iot.timetable.services.WarsawFinalStopService;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WarsawStopService {

    private final WarsawApiService warsawApiService;
    private final WarsawFinalStopService warsawStopDirectionService;
    private final TimetableRepository timetableRepository;

    @Data
    @Builder
    private static class ClosestDistance {

        private WarsawVehicle warsawVehicle;
        private WarsawStop closestStop;

    }

    @Data
    @Builder
    private static class StopDistance {

        private WarsawStop warsawStop;
        private double distance;

        public boolean isLowerOrEqual250m() {
            return distance <= 250;
        }

        public boolean isLowerOrEqual35m() {
            return distance <= 35;
        }

    }

    public List<WarsawStop> getStops() {
        return warsawApiService.getStops();
    }

    public WarsawStop getStopsInAreaOf35m(float lat, float lon) {
        WarsawStop currentPosition = WarsawStop.builder()
                .lon(lon)
                .lat(lat)
                .build();

        return warsawApiService.getStops().stream()
                .map(stop -> StopDistance.builder()
                        .warsawStop(stop)
                        .distance(stop.distance(currentPosition))
                        .build())
                .min(Comparator.comparingDouble(StopDistance::getDistance))
                .map(StopDistance::getWarsawStop)
                .orElse(null);
    }

    public List<WarsawStop> getStopsInAreaOf35m(float lat, float lon, String line) {
        return warsawApiService.getStops().stream()
                .map(stop -> StopDistance.builder()
                        .warsawStop(stop)
                        .distance(stop.distance(lat, lon))
                        .build())
                .filter(StopDistance::isLowerOrEqual250m)
                .filter(stop -> hasLineOnStop(stop.getWarsawStop().getGroup(), stop.getWarsawStop().getSlupek(), line))
                .filter(stop -> hasTimetableOnStop(stop.getWarsawStop().getGroup(), stop.getWarsawStop().getSlupek(), line))
                .sorted(Comparator.comparingDouble(StopDistance::getDistance))
                .filter(StopDistance::isLowerOrEqual35m)
                .map(StopDistance::getWarsawStop)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "stopInWarsawCache")
    public WarsawStop getWarsawStop(String stopId, String stopNumber) {
        return getStops().stream()
                .filter(stop -> stop.getGroup().equals(stopId))
                .filter(stop -> stop.getSlupek().equals(stopNumber))
                .findFirst().orElse(null);
    }

    @Cacheable(cacheNames = "linesOnStopInWarsawCache")
    public WarsawLineOnStop getLinesOnStop(String stopId, String stopNumber) {
        List<String> linesOnStop = warsawApiService.getLinesOnStop(stopId, stopNumber);
        return WarsawLineOnStop.builder()
                .stopId(stopId)
                .stopNumber(stopNumber)
                .lines(linesOnStop)
                .build();
    }

    public boolean hasLineOnStop(String stopId, String stopNumber, String line) {
        return getLinesOnStop(stopId, stopNumber).hasLine(line);
    }

    public boolean hasTimetableOnStop(String stopId, String stopNumber, String line) {
        List<WarsawDepartures> timetables = warsawApiService.getTimetable(stopId, stopNumber, line);
        saveTimetable(stopId, stopNumber, timetables);
        return !timetables.isEmpty();
    }

    @Transactional
    public void saveTimetable(String stopId, String stopNumber, List<WarsawDepartures> timetables) {
        WarsawStop warsawStop = getWarsawStop(stopId, stopNumber);

        timetableRepository.saveAll(timetables.stream()
                .map(timetable -> Timetables.builder()
                        .stopId(warsawStop.getGroup())
                        .stopNumber(warsawStop.getSlupek())
                        .stopName(warsawStop.getName())
                        .lon(warsawStop.getLon())
                        .lat(warsawStop.getLat())
                        .line(timetable.getLine())
                        .brigade(timetable.getBrigade())
                        .timetableDepartureDate(timetable.getTime())
                        .build())
                .collect(Collectors.toList()));
    }

    public Timetables lastVisitedStop(String line, String brigade) {
        return timetableRepository.findTopByLineAndBrigadeAndDepartureDateIsNotNull(line, brigade);
    }

    public List<Timetables> findTimetables(String line, String brigade, LocalDateTime localDateTime) {
        return timetableRepository.findTop10ByLineAndBrigadeAndTimetableDepartureDateGreaterThan(line, brigade, localDateTime.toLocalDate().atStartOfDay());
    }

}
