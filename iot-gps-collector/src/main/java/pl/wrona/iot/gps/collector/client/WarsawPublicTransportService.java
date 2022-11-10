package pl.wrona.iot.gps.collector.client;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.config.WarsawUmApiConfiguration;
import pl.wrona.iot.gps.collector.model.Vehicle;
import pl.wrona.iot.gps.collector.model.VehicleType;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

import java.time.LocalDateTime;
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
    public List<Vehicle> getBuses() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getResourceId(), "1"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of()).stream()
                .map(this::bus)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getTrams() {
        return Optional.ofNullable(warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getResourceId(), "2"))
                .map(ResponseEntity::getBody)
                .map(WarsawVehicles::getResult)
                .orElse(List.of()).stream()
                .map(this::tram)
                .collect(Collectors.toList());
    }

    private Vehicle tram(WarsawVehicle vehicle) {
        return vehicle(vehicle, VehicleType.TRAM);
    }

    private Vehicle bus(WarsawVehicle vehicle) {
        return vehicle(vehicle, VehicleType.BUS);
    }

    private Vehicle vehicle(WarsawVehicle vehicle, VehicleType vehicleType) {
        return Vehicle.builder()
                .vehicleType(vehicleType)
                .vehicleNumber(vehicle.getVehicleNumber())
                .line(vehicle.getLines())
                .lat(vehicle.getLat())
                .lon(vehicle.getLon())
                .brigade(vehicle.getBrigade())
                .time(LocalDateTime.parse(vehicle.getTime(), WARSAW_DATE_TIME_FORMATTER))
                .build();
    }
}
