package pl.wrona.iot.timetable.sink

import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PathUtilsTest extends Specification {

  @Shared
  private LocalDate CURRENT_DATE = LocalDate.of(2023, 8, 1)

  def "should return HDFS path"() {
    when:
    String path = PathUtils.getPath(MASK, DATE, FREQUENCY)

    then:
    path == PATH

    where:
    DATE                                                 | MASK                    | FREQUENCY || PATH
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(12, 32)) | "%s/{date}.gps.parquet" | 1L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(12, 32)) | "%s/{date}.gps.parquet" | 2L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(12, 32)) | "%s/{date}.gps.parquet" | 3L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(12, 32)) | "%s/{date}.gps.parquet" | 4L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(12, 32)) | "%s/{date}.gps.parquet" | 5L        || "%s/2023-08-01T10_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(13, 32)) | "%s/{date}.gps.parquet" | 1L        || "%s/2023-08-01T13_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(13, 32)) | "%s/{date}.gps.parquet" | 2L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(13, 32)) | "%s/{date}.gps.parquet" | 3L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(13, 32)) | "%s/{date}.gps.parquet" | 4L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(13, 32)) | "%s/{date}.gps.parquet" | 5L        || "%s/2023-08-01T10_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(14, 32)) | "%s/{date}.gps.parquet" | 1L        || "%s/2023-08-01T14_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(14, 32)) | "%s/{date}.gps.parquet" | 2L        || "%s/2023-08-01T14_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(14, 32)) | "%s/{date}.gps.parquet" | 3L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(14, 32)) | "%s/{date}.gps.parquet" | 4L        || "%s/2023-08-01T12_00.gps.parquet"
    LocalDateTime.of(CURRENT_DATE, LocalTime.of(14, 32)) | "%s/{date}.gps.parquet" | 5L        || "%s/2023-08-01T10_00.gps.parquet"

  }
}
