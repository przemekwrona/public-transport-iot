package pl.wrona.iot.timetable.reload.predicate

import pl.wrona.iot.timetable.reload.predicate.NightBusPredicate
import spock.lang.Specification

class NightBusPredicateTest extends Specification {

    private final NightBusPredicate predicate = new NightBusPredicate()

    def "should check if predicate return true for night buses"() {
        expect:
        predicate.test(LINE) == IS_BUS

        where:
        LINE   || IS_BUS
        "0"    || false
        "1"    || false
        "12"   || false
        "10"   || false
        " 10"  || false
        "10 "  || false
        "99"   || false
        "100"  || false
        "182"  || false
        " 182" || false
        "182 " || false
        "701"  || false
        "C23"  || false
        "N14"  || true
        " N14" || true
        "N14 " || true
        "S2"   || false
    }
}
