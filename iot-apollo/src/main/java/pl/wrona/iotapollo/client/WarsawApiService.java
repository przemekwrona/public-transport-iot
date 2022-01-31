package pl.wrona.iotapollo.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iotapollo.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawStops;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarsawApiService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawApiClient warsawApiClient;

    public List<WarsawStop> getStops() {
        return Optional.ofNullable(warsawApiClient
                        .getStops(warsawUmApiConfiguration.getStopsResourceId(), warsawUmApiConfiguration.getApikey()))
                .map(ResponseEntity::getBody)
                .map(WarsawStops::getResult)
                .orElse(List.of()).stream()
                .map(WarsawStop::of)
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    private class StopDistance {
        private WarsawStop warsawStop;
        private double distance;

    }

    public WarsawStop getClosestStop(float lon, float lat) {
        WarsawStop currentPosition = WarsawStop.builder()
                .lon(lon)
                .lat(lat)
                .build();

        return getStops().stream()
                .map(stop -> StopDistance.builder()
                        .warsawStop(stop)
                        .distance(stop.distance(currentPosition))
                        .build())
                .min(Comparator.comparingDouble(StopDistance::getDistance))
                .map(StopDistance::getWarsawStop)
                .orElse(null);
    }

//    public WarsawStop getClosestStop(String line) {
//        return null;
//    }

}
