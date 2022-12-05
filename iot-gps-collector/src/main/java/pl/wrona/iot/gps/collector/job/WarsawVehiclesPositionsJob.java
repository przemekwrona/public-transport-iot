package pl.wrona.iot.gps.collector.job;

import feign.codec.DecodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.client.WarsawPublicTransportService;
import pl.wrona.iot.gps.collector.config.GCloudProperties;
import pl.wrona.iot.gps.collector.model.Vehicle;
import pl.wrona.iot.gps.collector.sink.GCloudFileNameProvider;
import pl.wrona.iot.gps.collector.sink.GCloudSink;
import pl.wrona.iot.gps.collector.sink.WarsawVehicleGenericRecordMapper;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarsawVehiclesPositionsJob implements Runnable, Closeable {

    private final WarsawPublicTransportService warsawPublicTransportService;
    private final GCloudProperties gCloudProperties;
    private final GCloudFileNameProvider gCloudFileNameProvider;

    private GCloudSink<Vehicle> gCloudSink;
    private LocalDateTime lastSavedDateTime;

    @Override
    public void run() {

        try {
            List<Vehicle> buses = warsawPublicTransportService.getBuses();
            List<Vehicle> trams = warsawPublicTransportService.getTrams();

            LocalDateTime date = Stream.concat(buses.stream(), trams.stream())
                    .map(Vehicle::getTime)
                    .collect(Collectors.toSet())
                    .stream()
                    .max(Comparator.naturalOrder())
                    .map(maxLocalDate -> gCloudProperties.warsawVehicleLiveBucket().windowLocalDate(maxLocalDate))
                    .orElse(LocalDateTime.now());

            if (isNull(gCloudSink)) {
                this.gCloudSink = new GCloudSink<>(new WarsawVehicleGenericRecordMapper(gCloudProperties.warsawVehicleLiveBucket().getSchema()),
                        new Path(gCloudFileNameProvider.vehiclesLive(date)),
                        gCloudProperties);
            }

            if (nonNull(lastSavedDateTime) && date.isAfter(lastSavedDateTime)) {
                this.gCloudSink.close();
                this.gCloudSink = null;

                this.gCloudSink = new GCloudSink<>(
                        new WarsawVehicleGenericRecordMapper(gCloudProperties.warsawVehicleLiveBucket().getSchema()),
                        new Path(gCloudFileNameProvider.vehiclesLive(date)),
                        gCloudProperties);
            }

            Stream.concat(buses.stream(), trams.stream()).forEach(gCloudSink::save);
            this.lastSavedDateTime = date;
        }
        catch (DecodeException ignore) {
            log.error("Can not map results", ignore);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        gCloudSink.close();
        gCloudSink = null;
    }
}
