package pl.wrona.iot.gps.collector.config

import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BucketPropertiesTest extends Specification {

    @Shared
    private LocalDate NOW = LocalDate.of(2023, 6, 2)

    def "should window Local Date"() {
        given:
        BucketProperties bucketProperties = new BucketProperties(windowSizeInHours: WINDOW)

        expect:
        bucketProperties.windowLocalDate(DATE_TIME) == WINDOWED_DATE_TIME

        where:
        DATE_TIME                                  | WINDOW || WINDOWED_DATE_TIME
        LocalDateTime.of(NOW, LocalTime.of(13, 3)) | 1      || LocalDateTime.of(NOW, LocalTime.of(13, 0))
        LocalDateTime.of(NOW, LocalTime.of(13, 3)) | 2      || LocalDateTime.of(NOW, LocalTime.of(12, 0))
        LocalDateTime.of(NOW, LocalTime.of(13, 3)) | 3      || LocalDateTime.of(NOW, LocalTime.of(12, 0))
        LocalDateTime.of(NOW, LocalTime.of(17, 3)) | 1      || LocalDateTime.of(NOW, LocalTime.of(17, 0))
        LocalDateTime.of(NOW, LocalTime.of(17, 3)) | 2      || LocalDateTime.of(NOW, LocalTime.of(16, 0))
        LocalDateTime.of(NOW, LocalTime.of(17, 3)) | 3      || LocalDateTime.of(NOW, LocalTime.of(15, 0))
        LocalDateTime.of(NOW, LocalTime.of(17, 3)) | 4      || LocalDateTime.of(NOW, LocalTime.of(16, 0))
        LocalDateTime.of(NOW, LocalTime.of(17, 3)) | 5      || LocalDateTime.of(NOW, LocalTime.of(15, 0))
        LocalDateTime.of(NOW, LocalTime.of(17, 3)) | 6      || LocalDateTime.of(NOW, LocalTime.of(12, 0))
    }
}
