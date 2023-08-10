package pl.wrona.iot.timetable.client.warsaw;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.properties.WarsawUmApiProperties;
import pl.wrona.warsaw.transport.api.model.WarsawStops;
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue;
import pl.wrona.warsaw.transport.api.model.WarsawTimetables;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class WarsawApiService {

    private final WarsawUmApiProperties warsawUmApiConfiguration;
    private final WarsawApiClient warsawApiClient;

    @Timed(value = "warsaw_api_get_stops")
    @Cacheable(cacheNames = "stopsInWarsawCache", key = "#root.methodName")
    public List<WarsawStop> getStops() {
        return Optional.ofNullable(warsawApiClient
                        .getStops(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getStopsResourceId()))
                .map(ResponseEntity::getBody)
                .map(WarsawStops::getResult)
                .orElse(new LinkedList<>()).stream()
                .map(WarsawStop::of)
                .collect(Collectors.toList());
    }

    @Timed(value = "warsaw_api_lines_on_stop")
    @Cacheable(cacheNames = "lineOnStopCache")
    public List<String> getLinesOnStop(String stopId, String stopNumber) {
        return Optional.ofNullable(warsawApiClient.getTimetable(warsawUmApiConfiguration.getApikey(),
                        warsawUmApiConfiguration.getLinesOnStopsResourceId(),
                        stopId,
                        stopNumber,
                        ""))
                .map(ResponseEntity::getBody)
                .map(WarsawTimetables::getResult)
                .orElse(new LinkedList<>()).stream()
                .map(pl.wrona.warsaw.transport.api.model.WarsawTimetable::getValues)
                .flatMap(Collection::stream)
                .filter(value -> "linia".equals(value.getKey()))
                .map(WarsawTimetableValue::getValue)
                .collect(Collectors.toList());
    }

    @Timed(value = "warsaw_api_timetables")
    @Cacheable(cacheNames = "timetableCache")
    public List<WarsawDepartures> getTimetable(String stopId, String stopNumber, String line) {
        return Optional.ofNullable(warsawApiClient.getTimetable(warsawUmApiConfiguration.getApikey(),
                        warsawUmApiConfiguration.getTimetablesResourceId(),
                        stopId,
                        stopNumber,
                        line))
                .map(ResponseEntity::getBody)
                .map(WarsawTimetables::getResult)
                .orElse(new LinkedList<>()).stream()
                .map(timetable -> WarsawDepartures.of(timetable, line))
                .collect(Collectors.toList());
    }

    public List<WarsawVehicle> getPositions(String vehicleType) {
        return Optional.ofNullable(warsawApiClient.getVehicles(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getResourceId(), vehicleType))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of());
    }

    public List<WarsawVehicle> getBusPositions() {
        return getPositions("1");
    }

    public List<WarsawVehicle> getTramPositions() {
        return getPositions("2");
    }

    public List<WarsawVehicle> getPositions() {
        return Stream.concat(getBusPositions().stream(), getTramPositions().stream()).collect(Collectors.toList());
    }

}
