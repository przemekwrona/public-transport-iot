package pl.wrona.iot.timetable.reload.predicate

import pl.wrona.iot.timetable.reload.predicate.OtherPredicate
import spock.lang.Specification

class OtherPredicateTest extends Specification {

    private final OtherPredicate predicate = new OtherPredicate()

    def "should check if predicate return true for unknown vehicles"() {
        expect:
        predicate.test(LINE) == IS_OTHER

        where:
        LINE   || IS_OTHER
        "0"    || true
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
        "C23"  || true
        "N14"  || false
        " N14" || false
        "N14 " || false
        "S2"   || true
    }
}
