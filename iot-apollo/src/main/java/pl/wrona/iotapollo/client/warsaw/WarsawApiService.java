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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarsawApiService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawApiClient warsawApiClient;

    @Timed(description = "Time spent serving orders")
    @Cacheable(cacheNames = "stopsInWarsawCache", key = "#root.methodName")
    public List<WarsawStop> getStops() {
        return Optional.ofNullable(warsawApiClient
                        .getStops(warsawUmApiConfiguration.getStopsResourceId(), warsawUmApiConfiguration.getApikey()))
                .map(ResponseEntity::getBody)
                .map(WarsawStops::getResult)
                .orElse(List.of()).stream()
                .map(WarsawStop::of)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "lineOnStopCache")
    public List<String> getLinesOnStop(String stopId, String stopNumber) {
        return Optional.ofNullable(warsawApiClient.getTimetable(warsawUmApiConfiguration.getApikey(),
                        warsawUmApiConfiguration.getLinesOnStopsResourceId(),
                        stopId,
                        stopNumber))
                .map(ResponseEntity::getBody)
                .map(WarsawTimetables::getResult)
                .orElse(List.of()).stream()
                .map(pl.wrona.warsaw.transport.api.model.WarsawTimetable::getValues)
                .flatMap(Collection::stream)
                .filter(value -> "linia".equals(value.getKey()))
                .map(WarsawTimetableValue::getValue)
                .collect(Collectors.toList());
    }

}
