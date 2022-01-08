package pl.wrona.iothermes.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.wrona.iothermes.model.VehicleLocation;

import java.time.Instant;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class InfluxVehicles {

    private final InfluxDB influxDB;

    public void updateVehicles(List<VehicleLocation> vehicles) {
        log.info("Warsaw UM API GET Vehicles {} row to save", vehicles.size());

        log.info("Influx version: {}", response.getVersion());

    }

}
