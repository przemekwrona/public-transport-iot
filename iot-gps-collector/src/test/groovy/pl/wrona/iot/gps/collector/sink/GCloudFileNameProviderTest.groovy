package pl.wrona.iot.gps.collector.sink

import pl.wrona.iot.gps.collector.config.GCloudProperties
import spock.lang.Specification

import java.time.LocalDateTime

class GCloudFileNameProviderTest extends Specification {

    def gCloudProperties = Mock(GCloudProperties)

    def gCloudFileNameProvider = new GCloudFileNameProvider(gCloudProperties)

    def "should return file name"() {
        given:
        gCloudProperties.getBucketName() >> "vehicles_live"

        expect:
        gCloudFileNameProvider.vehiclesLive(DATE_TIME) == PATH

        where:
        DATE_TIME                              || PATH
        LocalDateTime.of(2022, 12, 13, 14, 15) || "gs://vehicles_live/date=2022-12-13/vehicles_live_2022-12-13--14.parquet"

    }
}
