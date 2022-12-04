package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.config.GCloudProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Component
@AllArgsConstructor
public class GCloudFileNameProvider {

    private final GCloudProperties gCloudProperties;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd--HH")
            .toFormatter();

    public String vehiclesLive(LocalDateTime date) {
        String fileName = String.format("%s_%s.parquet", gCloudProperties.getBucketName(), date.format(DATE_TIME_FORMATTER));
        return String.format("gs://%s/date=%s/%s", gCloudProperties.getBucketName(), date.toLocalDate(), fileName);
    }
}
