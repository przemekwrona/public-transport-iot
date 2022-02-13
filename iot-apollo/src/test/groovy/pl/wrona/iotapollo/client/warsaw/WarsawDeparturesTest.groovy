package pl.wrona.iotapollo.client.warsaw

import pl.wrona.warsaw.transport.api.model.WarsawTimetable
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue
import spock.lang.Specification

import java.time.LocalTime

class WarsawDeparturesTest extends Specification {


  def "should map response to WarsawDeparture"() {
    given:
    WarsawTimetable warsawTimetable = new WarsawTimetable().values([
            new WarsawTimetableValue().key("symbol_1").value(""),
            new WarsawTimetableValue().key("symbol_2").value(""),
            new WarsawTimetableValue().key("brygada").value(BRIGADE),
            new WarsawTimetableValue().key("kierunek").value(DIRECTION),
            new WarsawTimetableValue().key("trasa").value(ROUTE),
            new WarsawTimetableValue().key("czas").value(TIME)
    ])
    when:
    WarsawDepartures warsawDepartures = WarsawDepartures.of(warsawTimetable)

    then:
    warsawDepartures != null
    warsawDepartures.getBrigade() == BRIGADE
    warsawDepartures.getDirection() == DIRECTION
    warsawDepartures.getRoute() == ROUTE
    warsawDepartures.getTime() == MAPPED_TIME

    where:
    BRIGADE | DIRECTION  | ROUTE    | TIME       | MAPPED_TIME
    "4"     | "Gocławek" | "TP-GCW" | "06:35:00" | LocalTime.of(6, 35, 00)
  }

  def "should map response to WarsawDeparture if time is grater than 23"() {
    given:
    WarsawTimetable warsawTimetable = new WarsawTimetable().values([
            new WarsawTimetableValue().key("czas").value(TIME)
    ])
    when:
    WarsawDepartures warsawDepartures = WarsawDepartures.of(warsawTimetable)

    then:
    warsawDepartures.getTime() == MAPPED_TIME

    where:
    TIME       || MAPPED_TIME
    "25:35:00" || LocalTime.of(1, 35, 00)
  }

}