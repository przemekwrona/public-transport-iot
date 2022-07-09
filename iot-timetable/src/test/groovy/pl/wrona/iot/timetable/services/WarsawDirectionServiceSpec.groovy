package pl.wrona.iot.timetable.services

import spock.lang.Specification

class WarsawDirectionServiceSpec extends Specification {

    def directionService = new WarsawFinalStopService()

    def "should add direction"() {
        when:
        directionService.addDirection(LINE, DIRECTION)

        then:
        directionService.isDirection(LINE, DIRECTION)

        where:
        LINE | DIRECTION
        "1"  | "Annopol"
    }
}
