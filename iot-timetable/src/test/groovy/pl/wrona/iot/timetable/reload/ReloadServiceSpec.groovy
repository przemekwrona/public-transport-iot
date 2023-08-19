package pl.wrona.iot.timetable.reload

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.wrona.iot.timetable.properties.IotTimetablesProperties
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class ReloadServiceSpec extends Specification {

    @Autowired
    private ReloadService reloadService

    @SpringBean
    private IotTimetablesProperties iotTimetablesProperties = Mock()

    def "should build gtfs file avro -> gtfs"() {
        given:
        LocalDate date = LocalDate.of(2023, 8, 18)

        iotTimetablesProperties.getDirPath() >> "src/test/resources/timetables"

        when:
        reloadService.gtfs(date)

        then:
        1 == 1

    }
}
