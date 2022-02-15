package pl.wrona.iothermes.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.client.warsaw.WarsawPublicTransportService;
import pl.wrona.iothermes.model.VehicleLocation;
import pl.wrona.iothermes.repository.InfluxVehicles;
import pl.wrona.iothermes.repository.postgres.VehicleLocationService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Component
@AllArgsConstructor
public class WarsawService {

    private final WarsawPublicTransportService warsawPublicTransportService;
    private final VehicleLocationService vehicleLocationService;
    private final VehicleTimetableDelayService vehicleDelayService;
    private final InfluxVehicles influxVehicles;

    public void getAndSaveVehicles() {
        List<VehicleLocation> buses = warsawPublicTransportService.getBuses();
        List<VehicleLocation> trams = warsawPublicTransportService.getTrams();

        List<VehicleLocation> vehicles = Stream.concat(buses.stream(), trams.stream())
                .filter(vehicle -> vehicle.getTime().isAfter(Instant.now().minusSeconds(20)))
                .collect(Collectors.toList());

        vehicleLocationService.updateVehicles(vehicles);

        log.info("Number of vehicles in response {}", vehicles.size());

        vehicleLocationService.updateVehiclesWithDelay(vehicles);

        influxVehicles.updateVehicles(vehicles);
    }

}
