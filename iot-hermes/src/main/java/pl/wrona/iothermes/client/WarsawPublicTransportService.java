package pl.wrona.iothermes.client;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class WarsawPublicTransportService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawPublicTransportClient warsawPublicTransportClient;

    public List<WarsawVehicle> getBuses() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "1"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of());
    }

    public List<WarsawVehicle> getTrams() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "2"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of());
    }

}
