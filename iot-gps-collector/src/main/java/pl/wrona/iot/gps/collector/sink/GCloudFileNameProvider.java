package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.config.GCloudProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Component
@AllArgsConstructor
public class GCloudFileNameProvider {

    private final GCloudProperties gCloudProperties;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy_MM_dd__HH")
            .toFormatter();

    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy_MM_dd")
            .toFormatter();

    public String vehiclesLive(LocalDateTime date) {
        String fileName = String.format("%s_%s.parquet", gCloudProperties.warsawVehicleLiveBucket().getBucketName(), date.format(DATE_TIME_FORMATTER));
        return String.format("gs://%s/%s/%s", gCloudProperties.warsawVehicleLiveBucket().getBucketName(), date.format(DATE_FORMATTER), fileName);
    }

    public String vehiclesTimetables(LocalDate date) {
        String fileName = String.format("%s_%s.parquet", gCloudProperties.warsawTimetableBucket().getBucketName(), date.format(DATE_FORMATTER));
        return String.format("gs://%s/%s/%s", gCloudProperties.warsawTimetableBucket().getBucketName(), date.format(DATE_FORMATTER), fileName);
    }
}
