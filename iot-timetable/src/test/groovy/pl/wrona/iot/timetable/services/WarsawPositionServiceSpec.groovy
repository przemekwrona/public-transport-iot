package pl.wrona.iot.timetable.services

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.wrona.iot.timetable.properties.IotPositionsProperties
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class WarsawPositionServiceSpec extends Specification {

  @Autowired
  private WarsawPositionParquetService warsawPositionService

  @SpringBean
  private IotPositionsProperties iotPositionsProperties = Mock()


  def "should merge 2 parquet files"() {
    given:
    LocalDate now = LocalDate.of(2023, 8, 10)

    iotPositionsProperties.getDirPath() >> "src/test/resources"

    when:
    warsawPositionService.mergeParquet(now)

    then:
    1 == 1
  }
}
