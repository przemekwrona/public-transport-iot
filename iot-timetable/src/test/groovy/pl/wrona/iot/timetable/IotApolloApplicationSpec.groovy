package pl.wrona.iot.timetable

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class IotApolloApplicationSpec extends Specification {

  def contextLoads() {
    expect:
    1 == 1
  }
}
