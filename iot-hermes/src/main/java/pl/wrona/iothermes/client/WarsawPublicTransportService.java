package pl.wrona.iothermes.client;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.WarsawUmApiConfiguration;
import pl.wrona.iothermes.model.CityCode;
import pl.wrona.iothermes.model.VehicleLocation;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class WarsawPublicTransportService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawPublicTransportClient warsawPublicTransportClient;

    public List<VehicleLocation> getBuses() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "1"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of())
                .stream().map(this::of)
                .collect(Collectors.toList());
    }

    public List<VehicleLocation> getTrams() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "2"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of())
                .stream().map(this::of)
                .collect(Collectors.toList());
    }

    private VehicleLocation of(WarsawVehicle vehicle) {
        return VehicleLocation.builder()
                .cityCode(CityCode.WAWA)
                .vehicleNumber(vehicle.getVehicleNumber())
                .line(vehicle.getLines())
                .lat(vehicle.getLat())
                .lon(vehicle.getLon())
                .brigade(vehicle.getBrigade())
                .time(vehicle.getTime())
                .build();
    }

}
