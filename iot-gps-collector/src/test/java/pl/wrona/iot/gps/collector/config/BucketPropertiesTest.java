package pl.wrona.iot.gps.collector.config;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BucketPropertiesTest {

    private static LocalDate NOW = LocalDate.of(2023, 6, 2);

    private static Stream<Arguments> provideBucketProperties() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(13, 3)), 1, LocalDateTime.of(NOW, LocalTime.of(13, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(13, 3)), 2, LocalDateTime.of(NOW, LocalTime.of(12, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(13, 3)), 3, LocalDateTime.of(NOW, LocalTime.of(12, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(17, 3)), 1, LocalDateTime.of(NOW, LocalTime.of(17, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(17, 3)), 2, LocalDateTime.of(NOW, LocalTime.of(16, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(17, 3)), 3, LocalDateTime.of(NOW, LocalTime.of(15, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(17, 3)), 4, LocalDateTime.of(NOW, LocalTime.of(16, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(17, 3)), 5, LocalDateTime.of(NOW, LocalTime.of(15, 0))),
                Arguments.of(LocalDateTime.of(NOW, LocalTime.of(17, 3)), 6, LocalDateTime.of(NOW, LocalTime.of(12, 0))));
    }

    @ParameterizedTest
    @MethodSource("provideBucketProperties")
    void shouldWindowLocalDate(LocalDateTime dateTime, int window, LocalDateTime windowedDate) {
        // given
        BucketProperties bucketProperties = new BucketProperties();
        bucketProperties.setWindowSizeInHours(window);

        // expect
        assertThat(bucketProperties.windowLocalDate(dateTime)).isEqualTo(windowedDate);
    }

}