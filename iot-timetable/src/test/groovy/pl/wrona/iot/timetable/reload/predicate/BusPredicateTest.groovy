package pl.wrona.iot.timetable.reload.predicate

import pl.wrona.iot.timetable.reload.predicate.BusPredicate
import spock.lang.Specification

class BusPredicateTest extends Specification {

    private final BusPredicate predicate = new BusPredicate()

    def "should check if predicate return true for buses"() {
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
        "100"  || true
        "182"  || true
        " 182" || true
        "182 " || true
        "701"  || true
        "C23"  || false
        "N14"  || false
        " N14" || false
        "N14 " || false
        "S2"   || false
    }

}
