package pl.wrona.iotapollo.client;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iotapollo.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawStops;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarsawApiService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawApiClient warsawApiClient;


    //    @Cacheable(cacheNames = "warsawStops", key = "#root.methodName")
//    @Cacheable("warsawStops")
    @Cacheable
    public List<WarsawStop> getStops(String WAWA) {
        return Optional.ofNullable(warsawApiClient
                        .getStops(warsawUmApiConfiguration.getStopsResourceId(), warsawUmApiConfiguration.getApikey()))
                .map(ResponseEntity::getBody)
                .map(WarsawStops::getResult)
                .orElse(List.of()).stream()
                .map(WarsawStop::of)
                .collect(Collectors.toList());
    }

}
