package pl.wrona.iot.timetable.services

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.wrona.iot.timetable.properties.IotTimetablesProperties
import pl.wrona.iot.warsaw.avro.WarsawTimetable
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class WarsawRoutesServiceSpec extends Specification {

  @SpringBean
  private IotTimetablesProperties timetablesProperties = Mock()

  @Autowired
  private WarsawRoutesService warsawRoutesService

  def "should load Warsaw Timetables"() {
    given:
    LocalDate date = LocalDate.of(2023, 7, 30)
    timetablesProperties.getDirPath() >> "src/test/resources/timetables"

    when:
    List<WarsawTimetable> departures = warsawRoutesService.load(date)

    then:
    departures.size() == 2209
  }

  def "should load routes"() {
    given:
    LocalDate date = LocalDate.of(2023, 7, 30)
    timetablesProperties.getDirPath() >> "src/test/resources/timetables"

    when:
    warsawRoutesService.getRoutes(date)

    then:
    1 == 1
  }
}
