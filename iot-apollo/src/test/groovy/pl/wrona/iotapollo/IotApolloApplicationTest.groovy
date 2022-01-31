package pl.wrona.iotapollo

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class IotApolloApplicationTest extends Specification {

  def contextLoads() {
    expect:
    1 == 1
  }
}
