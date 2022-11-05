package pl.wrona.iot.gps.collector.job;

import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.client.WarsawPublicTransportService;
import pl.wrona.iot.gps.collector.config.GCloudProperties;
import pl.wrona.iot.gps.collector.model.Vehicle;
import pl.wrona.iot.gps.collector.parquet.SchemaService;
import pl.wrona.iot.gps.collector.sink.GCloudFileNameProvider;
import pl.wrona.iot.gps.collector.sink.GCloudSink;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class WarsawVehiclesPositionsJob implements Runnable {

    private final WarsawPublicTransportService warsawPublicTransportService;
    private final SchemaService schemaService;
    private final GCloudProperties gCloudProperties;
    private final GCloudFileNameProvider gCloudFileNameProvider;

    private GCloudSink gCloudSink;
    private LocalDate lastSavedDate;

    @Override
    public void run() {

        try {
            List<Vehicle> buses = warsawPublicTransportService.getBuses();
            List<Vehicle> trams = warsawPublicTransportService.getTrams();
            Stream<Vehicle> vehicles = Stream.concat(buses.stream(), trams.stream());

            LocalDate date = vehicles
                    .map(Vehicle::getTime)
                    .map(LocalDateTime::toLocalDate)
                    .collect(Collectors.toSet())
                    .stream()
                    .max(Comparator.naturalOrder())
                    .orElse(LocalDate.now());

            if (isNull(gCloudSink)) {
                this.gCloudSink = new GCloudSink(schemaService.getVehicleLiveSchema(),
                        new Path(gCloudFileNameProvider.vehiclesLive(date)),
                        gCloudProperties);
            }

            if (nonNull(lastSavedDate) && date.isAfter(lastSavedDate)) {
                this.gCloudSink.close();
                this.gCloudSink = new GCloudSink(schemaService.getVehicleLiveSchema(),
                        new Path(gCloudFileNameProvider.vehiclesLive(date)),
                        gCloudProperties);
            }

            vehicles.forEach(gCloudSink::save);
            this.lastSavedDate = date;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
