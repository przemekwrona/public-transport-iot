package pl.wrona.iot.gps.collector.job;

import org.apache.avro.Schema;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;
import pl.wrona.iot.gps.collector.client.WarsawPublicTransportService;
import pl.wrona.iot.gps.collector.config.BucketProperties;
import pl.wrona.iot.gps.collector.config.GCloudProperties;
import pl.wrona.iot.gps.collector.model.Vehicle;
import pl.wrona.iot.gps.collector.model.VehicleType;
import pl.wrona.iot.gps.collector.sink.GCloudFileNameProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarsawVehiclesPositionsJobTest {

    @TempDir
    static Path tempDir14oClock;

    @Mock
    WarsawPublicTransportService warsawPublicTransportService;

    @Mock
    GCloudProperties gCloudProperties;

    @Mock
    GCloudFileNameProvider gCloudFileNameProvider;

    @InjectMocks
    WarsawVehiclesPositionsJob warsawVehiclesPositionsJob;

    @Test
    void shouldCreateGCPSinkIfDoesNotExist() throws IOException {
        // given
        LocalDate fifthOfJanuary = LocalDate.of(2023, 1, 5);
        LocalDateTime line182Timestamp1 = LocalDateTime.of(fifthOfJanuary, LocalTime.of(14, 59));

        Vehicle vehicle = new Vehicle();
        vehicle.setLine("182");
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setLon(52.3452f);
        vehicle.setLat(21.0012f);
        vehicle.setVehicleNumber("5240");
        vehicle.setBrigade("4");
        vehicle.setTime(line182Timestamp1);

        when(warsawPublicTransportService.getTrams()).thenReturn(List.of());
        when(warsawPublicTransportService.getBuses()).thenReturn(List.of(vehicle));

        BucketProperties bucketProperties = mock(BucketProperties.class);
        when(bucketProperties.windowLocalDate(line182Timestamp1)).thenReturn(LocalDateTime.of(fifthOfJanuary, LocalTime.of(14, 0)));
        when(gCloudProperties.warsawVehicleLiveBucket()).thenReturn(bucketProperties);
        when(gCloudProperties.getHadoopConfiguration()).thenReturn(new Configuration());

        try (FileInputStream fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:schemas/vehicle_live.avsc"))) {
            String schema = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
            when(bucketProperties.getSchema()).thenReturn(new Schema.Parser().parse(schema));
        }

        String twoOClockPath = tempDir14oClock.toAbsolutePath().toString() + "/vehicles_live_14.parquet";
        when(gCloudFileNameProvider.vehiclesLive(LocalDateTime.of(fifthOfJanuary, LocalTime.of(14, 0)))).thenReturn(twoOClockPath);

        // when
        warsawVehiclesPositionsJob.run();
        warsawVehiclesPositionsJob.close();

        // then
        assertThat(new File(twoOClockPath)).exists();
    }

}