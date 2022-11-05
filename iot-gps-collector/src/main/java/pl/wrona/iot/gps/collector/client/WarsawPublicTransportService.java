package pl.wrona.iot.gps.collector.client;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.config.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class WarsawPublicTransportService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawPublicTransportClient warsawPublicTransportClient;

    public List<WarsawVehicle> getBuses() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getResourceId(), "1"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of());
    }

    public List<WarsawVehicle> getTrams() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getResourceId(), "2"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of());
    }

}
