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

@Component
@RequiredArgsConstructor
public class WarsawVehiclesPositionsJob implements Runnable {

    private final WarsawPublicTransportService warsawPublicTransportService;
    private final SchemaService schemaService;
    private final GCloudProperties gCloudProperties;
    private final GCloudFileNameProvider gCloudFileNameProvider;

    private GCloudSink cloudSink;
    private LocalDate lastSavedDate;
    private int iterations = 10;

    @Override
    public void run() {

        try {
            List<Vehicle> buses = warsawPublicTransportService.getBuses();
            List<Vehicle> trams = warsawPublicTransportService.getTrams();

            LocalDate date = Stream.concat(buses.stream(), trams.stream())
                    .map(Vehicle::getTime)
                    .map(LocalDateTime::toLocalDate)
                    .collect(Collectors.toSet())
                    .stream()
                    .max(Comparator.naturalOrder())
                    .orElse(LocalDate.now());

            this.lastSavedDate = date;

            this.cloudSink = new GCloudSink(schemaService.getVehicleLiveSchema(),
                    new Path(gCloudFileNameProvider.vehiclesLive(date)),
                    gCloudProperties);

            Stream.concat(buses.stream(), trams.stream()).forEach(cloudSink::save);

            this.iterations--;

            if (this.iterations <= 0) {
                this.cloudSink.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
