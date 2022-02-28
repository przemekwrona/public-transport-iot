package pl.wrona.iotapollo.client.warsaw;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iotapollo.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawStops;
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue;
import pl.wrona.warsaw.transport.api.model.WarsawTimetables;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarsawApiService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
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

}
