package pl.wrona.iotapollo.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import pl.wrona.iotapollo.WarsawUmApiConfiguration
import spock.lang.Specification

@SpringBootTest
class WarsawStopServiceSpec extends Specification {

  @Autowired
  WarsawUmApiConfiguration warsawUmApiConfiguration

  def warsawApiClientMock = Mock(WarsawApiClient)

  def "should call Warsaw API 1 time"() {
    setup:
    def warsawApiService = new WarsawApiService(warsawUmApiConfiguration, warsawApiClientMock, null)
    def warsawStopService = new WarsawStopService(warsawApiService)

    and:
    warsawApiClientMock.getStops(_ as String, _ as String) >> ResponseEntity.ok(new pl.wrona.warsaw.transport.api.model.WarsawStops())

    when:
    10.times { warsawStopService.getClosestStop(52.23, 21.02) }

    then:
    1 * warsawApiClientMock.getStops(_ as String, _ as String)
  }


}
