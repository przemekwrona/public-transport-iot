package pl.wrona.iot.gps.collector.sink;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wrona.iot.gps.collector.config.BucketProperties;
import pl.wrona.iot.gps.collector.config.GCloudProperties;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GCloudFileNameProviderTest {

    @Mock
    GCloudProperties gCloudProperties;

    @InjectMocks
    GCloudFileNameProvider gCloudFileNameProvider;

    private static Stream<Arguments> provideGCloudFileNames() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2022, 12, 13, 14, 15), "gs://warsaw_vehicles_live/2022_12_13/warsaw_vehicles_live_2022_12_13__14.parquet"),
                Arguments.of(LocalDateTime.of(2022, 12, 13, 6, 15), "gs://warsaw_vehicles_live/2022_12_13/warsaw_vehicles_live_2022_12_13__06.parquet")
        );
    }

    @ParameterizedTest
    @MethodSource("provideGCloudFileNames")
    void shouldReturnFileName(LocalDateTime date, String path) {
        // given
        BucketProperties bucketProperties = Mockito.mock(BucketProperties.class);
        when(bucketProperties.getBucketName()).thenReturn("warsaw_vehicles_live");
        when(gCloudProperties.warsawVehicleLiveBucket()).thenReturn(bucketProperties);

        // expect
        assertThat(gCloudFileNameProvider.vehiclesLive(date)).isEqualTo(path);
    }

}