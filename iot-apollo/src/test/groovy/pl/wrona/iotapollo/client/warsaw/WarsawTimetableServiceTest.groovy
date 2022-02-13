package pl.wrona.iotapollo.client.warsaw

import org.springframework.http.ResponseEntity
import pl.wrona.iotapollo.JsonFileUtils
import pl.wrona.iotapollo.WarsawUmApiConfiguration
import pl.wrona.warsaw.transport.api.model.WarsawTimetables
import spock.lang.Specification

import java.time.LocalTime

class WarsawTimetableServiceTest extends Specification {
  def warsawUmApiConfiguration = Stub(WarsawUmApiConfiguration)

  def warsawTimetableApiClient = Stub(WarsawApiClient)

  def warsawTimetableApiService = new WarsawApiService(warsawUmApiConfiguration, warsawTimetableApiClient)

  def warsawStopService = Stub(WarsawStopService)

  def warsawTimetableService = new WarsawTimetableService(warsawTimetableApiService, warsawStopService)


  def "should return timetable for vehicle if is on stop"() {
    given: "configuration of resiurces"
    warsawUmApiConfiguration.getApikey() >> "api_key"
    warsawUmApiConfiguration.getTimetablesResourceId() >> "timetable_resource_id"

    and: "mocked response with timetables"
    WarsawTimetables warsawTimetables = JsonFileUtils.readJson("/warsaw/timetable-4121-03-9.json", WarsawTimetables.class)
    warsawTimetableApiClient.getTimetable(_ as String, _ as String, STOP_ID, STOP_NUMBER, LINE) >> ResponseEntity.ok(warsawTimetables)

    and: "mocked closest stop for defined position"
    warsawStopService.getClosestStop(LAT, LON, LINE) >> WarsawStop.builder()
            .lat(LAT)
            .lon(LON)
            .group(STOP_ID)
            .slupek(STOP_NUMBER)
            .name(STOP_NAME)
            .build()

    when: "get vehicle departure on stop in Warsaw"
    WarsawStopDepartures warsawDeparture = warsawTimetableService.getTimetable(TIME, LAT, LON, LINE, BRIGADE)

    then:
    warsawDeparture != null
    warsawDeparture.getBrigade() == BRIGADE
    warsawDeparture.getDirection() == DIRECTION
    warsawDeparture.getRoute() == ROUTE
    warsawDeparture.getTime() == MAPPED_TIME

    where:
    TIME                 | LAT     | LON     | STOP_ID | STOP_NUMBER | STOP_NAME  | LINE | BRIGADE | DIRECTION    | ROUTE      | MAPPED_TIME
    LocalTime.of(17, 23) | 52.143f | 21.002f | "4121"  | "03"        | "Wawelska" | "9"  | "6"     | "Wiatraczna" | "TX-WIA11" | LocalTime.of(17, 24)
    LocalTime.of(17, 24) | 52.143f | 21.002f | "4121"  | "03"        | "Wawelska" | "9"  | "6"     | "Wiatraczna" | "TX-WIA11" | LocalTime.of(17, 24)
    LocalTime.of(17, 25) | 52.143f | 21.002f | "4121"  | "03"        | "Wawelska" | "9"  | "6"     | "Wiatraczna" | "TX-WIA11" | LocalTime.of(17, 24)

  }
}
