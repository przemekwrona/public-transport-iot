package pl.wrona.iot.timetable.client.warsaw

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import pl.wrona.iot.timetable.WarsawUmApiConfiguration
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
    warsawApiClient.getStops(_ as String, _ as String) >> ResponseEntity.ok(new WarsawStops()
            .result([new pl.wrona.warsaw.transport.api.model.WarsawStop().values([])]))

    when:
    10.times {

      warsawStopService.getStopsInAreaOf35m(52.23, 21.02)
    }

    then:
    cacheManager.getCache(CACHE_NAME) == []
    1 * warsawApiClient.getStops(_ as String, _ as String)
  }

  def "should return the closest stop"() {
    setup:
    warsawUmApiConfiguration.getApikey() >> "api_key"
    warsawUmApiConfiguration.getStopsResourceId() >> "stops_resource_id"

    WarsawStops warsawStops = pl.wrona.iot.timetable.JsonFileUtils.readJson("/warsaw/stops-narutowicza.json", WarsawStops.class)
    warsawApiClient.getStops(_ as String, _ as String) >> ResponseEntity.ok(warsawStops)

    when:
    WarsawStop warsawStop = warsawStopService.getStopsInAreaOf35m(LAT, LON)

    then:
    warsawStop != null
    warsawStop.getName() == NAME

    where:
    LAT    | LON    || NAME
    52.214 | 20.980 || "Och-Teatr"
    52.220 | 20.985 || "pl.Narutowicza"
    52.222 | 20.985 || "Ochota-Ratusz"
  }

  def "should calculate distance between stop and vehicle"() {
    given:
    WarsawStop warsawStop = WarsawStop.builder()
            .lat(STOP_LAT)
            .lon(STOP_LON)
            .build()

    WarsawStop vehicleStop = WarsawStop.builder()
            .lat(VEHICLE_LAT)
            .lon(VEHICLE_LON)
            .build()

    expect:
    warsawStop.distance(vehicleStop) == DISTANCE

    where:
    VEHICLE_LAT | VEHICLE_LON | STOP_LAT  | STOP_LON  | DISTANCE
    52.23465f   | 21.01522f   | 52.23069f | 21.01585f | 442
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
