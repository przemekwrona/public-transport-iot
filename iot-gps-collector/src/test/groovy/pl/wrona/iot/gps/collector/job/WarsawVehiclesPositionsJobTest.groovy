package pl.wrona.iot.gps.collector.job


import org.apache.avro.Schema
import org.apache.commons.io.IOUtils
import org.apache.hadoop.conf.Configuration
import org.springframework.util.ResourceUtils
import pl.wrona.iot.gps.collector.client.WarsawPublicTransportService
import pl.wrona.iot.gps.collector.config.GCloudProperties
import pl.wrona.iot.gps.collector.model.Vehicle
import pl.wrona.iot.gps.collector.model.VehicleType

import pl.wrona.iot.gps.collector.sink.GCloudFileNameProvider
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class WarsawVehiclesPositionsJobTest extends Specification {

    @TempDir
    @Shared
    Path tempDir14oClock

    @TempDir
    @Shared
    Path tempDir15oClock

    def warsawPublicTransportService = Mock(WarsawPublicTransportService)

    def schemaService = Mock(SchemaService)

    def gCloudProperties = Mock(GCloudProperties)

    def gCloudFileNameProvider = Mock(GCloudFileNameProvider)

    def warsawVehiclesPositionsJob = new WarsawVehiclesPositionsJob(warsawPublicTransportService, schemaService, gCloudProperties, gCloudFileNameProvider)

    void "should create GCP sink if does not exist"() {
        given:
        LocalDate fifthOfJanuary = LocalDate.of(2023, 1, 5)
        LocalDateTime line182Timestamp1 = LocalDateTime.of(fifthOfJanuary, LocalTime.of(14, 59))

        and:
        warsawPublicTransportService.getBuses() >> List.of(new Vehicle(line: "182", vehicleType: VehicleType.BUS, lon: 52.3452, lat: 21.0012, vehicleNumber: "5240", brigade: "4", time: line182Timestamp1))
        warsawPublicTransportService.getTrams() >> List.of()

        and:
        gCloudProperties.getBucketName() >> "vehicles_live"
        gCloudProperties.getHadoopConfiguration() >> new Configuration()

        and:
        try (FileInputStream fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:schemas/vehicle_live.avsc"))) {
            String schema = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8)
            schemaService.getVehicleLiveSchema() >> new Schema.Parser().parse(schema)
        }

        String twoOClockPath = tempDir14oClock.toAbsolutePath().toString() + "/vehicles_live_14.parquet"
        gCloudFileNameProvider.vehiclesLive(LocalDateTime.of(fifthOfJanuary, LocalTime.of(14, 0))) >> twoOClockPath

        when:
        warsawVehiclesPositionsJob.run()
        warsawVehiclesPositionsJob.close()

        then:
        new File(twoOClockPath).exists() == true
    }
}
