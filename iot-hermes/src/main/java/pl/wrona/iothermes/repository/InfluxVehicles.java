package pl.wrona.iothermes.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
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

    private final InfluxDBClient influxDBClient;

    public void updateVehicles(List<VehicleLocation> vehicles) {
        log.info("Warsaw UM API GET Vehicles {} row to save", vehicles.size());

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        vehicles.stream()
                .map(vehicle -> Point.measurement("location")
                        .addTag("citycode", vehicle.getCityCode().name())
                        .addField("vehiclenumber", vehicle.getVehicleNumber())
                        .addField("vehiclenumber", vehicle.getVehicleNumber())
                        .addField("brigade", vehicle.getBrigade())
                        .addField("latitude", vehicle.getLat())
                        .addField("longitude", vehicle.getLon())
                        .time(Instant.now().toEpochMilli(), WritePrecision.MS))
                .forEach(writeApi::writePoint);
    }

}
