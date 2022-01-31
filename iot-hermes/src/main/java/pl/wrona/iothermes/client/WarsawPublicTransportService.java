package pl.wrona.iothermes.client;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.WarsawUmApiConfiguration;
import pl.wrona.iothermes.model.CityCode;
import pl.wrona.iothermes.model.VehicleLocation;
import pl.wrona.iothermes.model.VehicleType;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WarsawPublicTransportService {

    private static final DateTimeFormatter WARSAW_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawPublicTransportClient warsawPublicTransportClient;

    public List<VehicleLocation> getBuses() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "1"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of())
                .stream().map(vehicle -> of(vehicle, VehicleType.BUS))
                .collect(Collectors.toList());
    }

    public List<VehicleLocation> getTrams() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "2"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of())
                .stream().map(vehicle -> of(vehicle, VehicleType.TRAM))
                .collect(Collectors.toList());
    }

    private VehicleLocation of(WarsawVehicle vehicle, VehicleType vehicleType) {
        return VehicleLocation.builder()
                .cityCode(CityCode.WAWA)
                .vehicleType(vehicleType)
                .vehicleNumber(vehicle.getVehicleNumber())
                .line(vehicle.getLines())
                .lat(vehicle.getLat())
                .lon(vehicle.getLon())
                .brigade(vehicle.getBrigade())
                .time(LocalDateTime.parse(vehicle.getTime(), WARSAW_DATE_TIME_FORMATTER)
                        .toInstant(ZoneOffset.of("Europe/Warsaw")))
                .build();
    }

}
