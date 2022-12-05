package pl.wrona.iot.gps.collector.sink

import pl.wrona.iot.gps.collector.config.GCloudProperties
import spock.lang.Specification

import java.time.LocalDateTime

class GCloudFileNameProviderTest extends Specification {

    def gCloudProperties = Mock(GCloudProperties)

    def gCloudFileNameProvider = new GCloudFileNameProvider(gCloudProperties)

    def "should return file name"() {
        given:
        gCloudProperties.getBucketName() >> "warsaw_vehicles_live"

        expect:
        gCloudFileNameProvider.vehiclesLive(DATE_TIME) == PATH

        where:
        DATE_TIME                              || PATH
        LocalDateTime.of(2022, 12, 13, 14, 15) || "gs://warsaw_vehicles_live/2022_12_13/warsaw_vehicles_live_2022_12_13__14.parquet"
        LocalDateTime.of(2022, 12, 13, 6, 15)  || "gs://warsaw_vehicles_live/2022_12_13/warsaw_vehicles_live_2022_12_13__06.parquet"

    }
}
