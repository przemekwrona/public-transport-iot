package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.config.GCloudProperties;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class GCloudFileNameProvider {

    private final GCloudProperties gCloudProperties;

    public String vehiclesLive(LocalDate date) {
        return String.format("gs://%s/%s.parquet", gCloudProperties.getBucketName(), date);
    }
}
