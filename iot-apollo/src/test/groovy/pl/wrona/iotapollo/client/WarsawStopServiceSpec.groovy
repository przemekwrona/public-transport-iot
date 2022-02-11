package pl.wrona.iotapollo.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import pl.wrona.iotapollo.JsonFileUtils
import pl.wrona.iotapollo.WarsawUmApiConfiguration
import pl.wrona.iotapollo.client.warsaw.WarsawApiClient
import pl.wrona.iotapollo.client.warsaw.WarsawApiService
import pl.wrona.iotapollo.client.warsaw.WarsawStop
import pl.wrona.iotapollo.client.warsaw.WarsawStopService
import pl.wrona.warsaw.transport.api.model.WarsawStops
import spock.lang.Ignore
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@ContextConfiguration
class WarsawStopServiceSpec extends Specification {

  public static final String CACHE_NAME = "books"

  @Autowired
  CacheManager cacheManager;

  def warsawUmApiConfiguration = Mock(WarsawUmApiConfiguration)

  def warsawApiClient = Mock(WarsawApiClient)

  def warsawApiService = new WarsawApiService(warsawUmApiConfiguration, warsawApiClient)

  def warsawStopService = new WarsawStopService(warsawApiService)

  def "setup"() {
    cacheManager.getCache(CACHE_NAME).clear()
  }

  @Ignore
  def "should call Warsaw API 1 time"() {
    setup:
    warsawApiClient.getStops(_ as String, _ as String) >> ResponseEntity.ok(new pl.wrona.warsaw.transport.api.model.WarsawStops()
            .result([new pl.wrona.warsaw.transport.api.model.WarsawStop().values([])]))

    when:
    10.times {

      warsawStopService.getClosestStop(52.23, 21.02)
    }

    then:
    cacheManager.getCache(CACHE_NAME) == []
    1 * warsawApiClient.getStops(_ as String, _ as String)
  }

  def "should return the closest stop"() {
    setup:
    warsawUmApiConfiguration.getApikey() >> "api_key"
    warsawUmApiConfiguration.getStopsResourceId() >> "stops_resource_id"

    WarsawStops warsawStops = JsonFileUtils.readJson("/warsaw/stops-mini.json", WarsawStops.class)
    warsawApiClient.getStops(_ as String, _ as String) >> ResponseEntity.ok(warsawStops)

    when:
    WarsawStop warsawStop = warsawStopService.getClosestStop(LAT, LON)

    then:
    warsawStop != null
    warsawStop.getName() == NAME

    where:
    LAT    | LON    || NAME
    52.214 | 20.980 || "Och-Teatr"
    52.220 | 20.985 || "pl.Narutowicza"
    52.222 | 20.985 || "Ochota-Ratusz"
  }

  @TestConfiguration
  @EnableCaching
  static class Config {

    def mockFactory = new DetachedMockFactory()
    def warsawApiClient = mockFactory.Mock(WarsawApiClient)

    @Bean
    CacheManager cacheManager() {
      new ConcurrentMapCacheManager(CACHE_NAME)
    }

    @Bean
    WarsawApiClient warsawApiClient() {
      warsawApiClient
    }

    @Bean
    WarsawUmApiConfiguration warsawUmApiConfiguration() {
      def warsawUmApiConfiguration = new WarsawUmApiConfiguration()
      warsawUmApiConfiguration.stopsResourceId = "stopsResourceId"
      warsawUmApiConfiguration.setApikey("apiKey")
      return warsawUmApiConfiguration;
    }
  }


}
