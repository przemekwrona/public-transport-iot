package pl.wrona.iothermes.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Pong;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class InfluxVehicles {

    private final InfluxDB influxDB;

    public void updateVehicles() {
        Pong response = influxDB.ping();

        log.info("Influx version: {}", response.getVersion());

    }

}
