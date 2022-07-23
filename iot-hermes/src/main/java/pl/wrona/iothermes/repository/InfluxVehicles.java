package pl.wrona.iothermes.repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.wrona.iothermes.model.VehicleLocation;

import java.time.ZoneOffset;
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
                .map(vehicle -> Point.measurement("vehicle_location")
                        .time(vehicle.getTime().toInstant(ZoneOffset.UTC), WritePrecision.S)
                        .addTag("city_code", vehicle.getCityCode().name())
                        .addTag("vehicle_number", vehicle.getVehicleNumber())
                        .addField("vehicle_type", vehicle.getVehicleType().name())
                        .addField("line", vehicle.getLine())
                        .addField("lat", vehicle.getLat())
                        .addField("lon", vehicle.getLon())
                        .addField("brigade", vehicle.getBrigade()))
                .forEach(writeApi::writePoint);
    }

}
