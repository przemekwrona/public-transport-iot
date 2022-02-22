package pl.wrona.iotapollo.services

import spock.lang.Specification

class WarsawDirectionServiceSpec extends Specification {

    def directionService = new WarsawStopDirectionService()

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
