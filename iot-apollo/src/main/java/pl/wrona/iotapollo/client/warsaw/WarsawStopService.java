package pl.wrona.iotapollo.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WarsawStopService {

    private final WarsawApiService warsawApiService;

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

    }

    public List<WarsawStop> getStops() {
        return warsawApiService.getStops();
    }

    public WarsawStop getClosestStop(float lat, float lon) {
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

    public WarsawStop getClosestStop(float lon, float lat, String line) {
        WarsawStop currentPosition = WarsawStop.builder()
                .lon(lon)
                .lat(lat)
                .build();

        WarsawStop closestStopWithTimetable = warsawApiService.getStops().stream()
                .map(stop -> StopDistance.builder()
                        .warsawStop(stop)
                        .distance(stop.distance(currentPosition))
                        .build())
                .sorted(Comparator.comparingDouble(StopDistance::getDistance))
                .filter(stop -> getLinesOnStop(stop.getWarsawStop().getGroup(), stop.getWarsawStop().getSlupek()).hasLine(line))
                .filter(stop -> warsawApiService.getTimetable(stop.getWarsawStop().getGroup(), stop.getWarsawStop().getSlupek(), line).size() > 0)
                .findFirst()
                .map(StopDistance::getWarsawStop)
                .orElse(null);

        if (closestStopWithTimetable == null) {
            log.error("For line {} on position {}, {} there is no available stop", line, lon, lat);
        }

        return closestStopWithTimetable;
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

}
