package pl.wrona.iotapollo.client.warsaw


import pl.wrona.iotapollo.WarsawUmApiConfiguration
import pl.wrona.warsaw.transport.api.model.WarsawTimetables
import spock.lang.Specification

import java.time.LocalTime

import static org.springframework.http.ResponseEntity.ok
import static pl.wrona.iotapollo.JsonFileUtils.readJson

class WarsawTimetableServiceTest extends Specification {

  String API_KEY = "api_key"
  String STOP_RESOURCE_ID = "stop_resource_id"
  String LINES_ON_STOP_RESOURCE_ID = "lines_on_stop_resource_id"
  String TIMETABLE_RESOURCE_ID = "timetable_resource_id"

  def warsawUmApiConfiguration = Stub(WarsawUmApiConfiguration)

  def warsawApiClient = Stub(WarsawApiClient)

  def warsawApiService = new WarsawApiService(warsawUmApiConfiguration, warsawApiClient)

  def warsawStopService = new WarsawStopService(warsawApiService)

  def warsawTimetableService = new WarsawTimetableService(warsawApiService, warsawStopService)

  def setup() {
    warsawUmApiConfiguration.getApikey() >> API_KEY
    warsawUmApiConfiguration.getStopsResourceId() >> STOP_RESOURCE_ID
    warsawUmApiConfiguration.getLinesOnStopsResourceId() >> LINES_ON_STOP_RESOURCE_ID
    warsawUmApiConfiguration.getTimetablesResourceId() >> TIMETABLE_RESOURCE_ID

    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "01", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "02", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-02.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "03", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-03.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "04", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-04.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "05", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-05.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "06", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-06.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "07", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-07.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "08", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-08.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "09", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-09.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "10", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-10.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "11", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-11.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "12", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-12.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "13", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-13.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "14", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-14.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4001", "15", "") >> ok(readJson("/warsaw/lines-zawiszy-4001-15.json", WarsawTimetables.class))

    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4002", "01", "") >> ok(readJson("/warsaw/lines-ochota-ratusz-4002-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4002", "02", "") >> ok(readJson("/warsaw/lines-ochota-ratusz-4002-02.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4002", "51", "") >> ok(readJson("/warsaw/lines-ochota-ratusz-4002-51.json", WarsawTimetables.class))

    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "01", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "02", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-02.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "03", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-03.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "04", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-04.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "05", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-05.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "06", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-06.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "07", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-07.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "08", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-08.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "09", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-09.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "10", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-10.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "11", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-11.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "12", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-12.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "13", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-13.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "14", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-14.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "15", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-15.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4003", "16", "") >> ok(readJson("/warsaw/lines-pl-narutowicza-4003-16.json", WarsawTimetables.class))

    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4121", "01", "") >> ok(readJson("/warsaw/lines-wawelska-4121-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4121", "02", "") >> ok(readJson("/warsaw/lines-wawelska-4121-02.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4121", "03", "") >> ok(readJson("/warsaw/lines-wawelska-4121-03.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4121", "04", "") >> ok(readJson("/warsaw/lines-wawelska-4121-04.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4121", "05", "") >> ok(readJson("/warsaw/lines-wawelska-4121-05.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4121", "06", "") >> ok(readJson("/warsaw/lines-wawelska-4121-06.json", WarsawTimetables.class))

    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4004", "01", "") >> ok(readJson("/warsaw/lines-och-teatr-4004-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4004", "02", "") >> ok(readJson("/warsaw/lines-och-teatr-4004-02.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4004", "03", "") >> ok(readJson("/warsaw/lines-och-teatr-4004-03.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4004", "04", "") >> ok(readJson("/warsaw/lines-och-teatr-4004-04.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "4004", "52", "") >> ok(readJson("/warsaw/lines-och-teatr-4004-52.json", WarsawTimetables.class))

    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "7033", "01", "") >> ok(readJson("/warsaw/lines-krucza-7033-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "7033", "02", "") >> ok(readJson("/warsaw/lines-krucza-7033-02.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "7033", "03", "") >> ok(readJson("/warsaw/lines-krucza-7033-03.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "7033", "04", "") >> ok(readJson("/warsaw/lines-krucza-7033-04.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "7033", "05", "") >> ok(readJson("/warsaw/lines-krucza-7033-05.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, LINES_ON_STOP_RESOURCE_ID, "7033", "06", "") >> ok(readJson("/warsaw/lines-krucza-7033-06.json", WarsawTimetables.class))

  }

  def "should return timetable for vehicle if is on stop"() {
    given: "mock stops in Warsaw"
    warsawApiClient.getStops(API_KEY, STOP_RESOURCE_ID) >> ok(readJson("/warsaw/stops-narutowicza.json", pl.wrona.warsaw.transport.api.model.WarsawStops.class))

    and: "mock timetables"
    warsawApiClient.getTimetable(API_KEY, TIMETABLE_RESOURCE_ID, STOP_ID, STOP_NUMBER, LINE) >> ok(readJson("/warsaw/timetable-4121-03-9.json", WarsawTimetables.class))

    when: "get vehicle departure on stop in Warsaw"
    WarsawStopDepartures warsawDeparture = warsawTimetableService.getDeparture(TIME, LAT, LON, LINE, BRIGADE)

    then:
    warsawDeparture != null
    warsawDeparture.getBrigade() == BRIGADE
    warsawDeparture.getDirection() == DIRECTION
    warsawDeparture.getRoute() == ROUTE
    warsawDeparture.getTimetableDeparture() == MAPPED_TIME

    where:
    TIME                 | LAT      | LON      | STOP_ID | STOP_NUMBER | STOP_NAME  | LINE | BRIGADE | DIRECTION    | ROUTE      | MAPPED_TIME
    LocalTime.of(17, 23) | 52.2158f | 20.9807f | "4121"  | "03"        | "Wawelska" | "9"  | "6"     | "Wiatraczna" | "TX-WIA11" | LocalTime.of(17, 24)
    LocalTime.of(17, 24) | 52.2158f | 20.9807f | "4121"  | "03"        | "Wawelska" | "9"  | "6"     | "Wiatraczna" | "TX-WIA11" | LocalTime.of(17, 24)
    LocalTime.of(17, 25) | 52.2158f | 20.9807f | "4121"  | "03"        | "Wawelska" | "9"  | "6"     | "Wiatraczna" | "TX-WIA11" | LocalTime.of(17, 24)

  }

  def "should return stop if timetable does not exist"() {
    given: "mock Warsaw API response"
    warsawApiClient.getStops(API_KEY, STOP_RESOURCE_ID) >> ok(readJson("/warsaw/stops-krucza.json", pl.wrona.warsaw.transport.api.model.WarsawStops.class))

    warsawApiClient.getTimetable(API_KEY, TIMETABLE_RESOURCE_ID, "7033", "01", "175") >> ok(readJson("/warsaw/timetable-175-krucza-7033-01.json", WarsawTimetables.class))
    warsawApiClient.getTimetable(API_KEY, TIMETABLE_RESOURCE_ID, "7033", "02", "175") >> ok(readJson("/warsaw/timetable-175-krucza-7033-02.json", WarsawTimetables.class))

    when:
    WarsawStopDepartures warsawStopDeparturesPrevious = warsawTimetableService.getDeparture(TIME, LAT_PREV, LON_PREV, LINE, BRIGADE)
    WarsawStopDepartures warsawStopDepartures = warsawTimetableService.getDeparture(TIME, LAT, LON, LINE, BRIGADE)

    then:
    warsawStopDepartures != null
    warsawStopDepartures.getLine() == LINE
    warsawStopDepartures.getBrigade() == BRIGADE
    warsawStopDepartures.getStopId() == STOP_ID
    warsawStopDepartures.getStopNumber() == STOP_NUMBER
    warsawStopDepartures.getStopName() == STOP_NAME
    warsawStopDepartures.getStopDistance() == STOP_DISTANCE

    where:
    LAT_PREV | LON_PREV | LAT     | LON     | LINE  | BRIGADE | TIME                 | STOP_ID | STOP_NUMBER | STOP_NAME | STOP_DISTANCE
    // 175 Centrum -> Krucza
    52.2301  | 21.0136  | 52.2313 | 21.0182 | "175" | "59"    | LocalTime.of(15, 45) | "7033"  | "01"        | "Krucza"  | 34l
    // 999 Nowy Świat -> Krucza Line 999 does not exist
//    52.2298  | 21.0110  | 52.2313 | 21.0182 | "999" | "59"    | LocalTime.of(15, 45) | ""      | ""          | ""        | 0L
  }

}
