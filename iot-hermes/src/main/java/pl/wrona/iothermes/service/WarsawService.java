package pl.wrona.iothermes.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.client.WarsawPublicTransportService;
import pl.wrona.iothermes.model.VehicleLocation;
import pl.wrona.iothermes.repository.InfluxVehicles;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Component
@AllArgsConstructor
public class WarsawService {

    private final WarsawPublicTransportService warsawPublicTransportService;
    private final InfluxVehicles influxVehicles;

    public void getAndSaveVehicles() {
        log.info("Warsaw UM API GET Vehicles");
        List<VehicleLocation> buses = warsawPublicTransportService.getBuses();
        List<VehicleLocation> trams = warsawPublicTransportService.getTrams();

        List<VehicleLocation> vehicles = Stream.concat(buses.stream(), trams.stream())
                .collect(Collectors.toList());

        influxVehicles.updateVehicles(vehicles);
    }

}
