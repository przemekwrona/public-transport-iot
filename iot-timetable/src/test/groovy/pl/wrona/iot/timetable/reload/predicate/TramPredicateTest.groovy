package pl.wrona.iot.timetable.reload.predicate

import pl.wrona.iot.timetable.reload.predicate.TramPredicate
import spock.lang.Specification

class TramPredicateTest extends Specification {

    private TramPredicate predicate = new TramPredicate();

    def "should check if predicate return true for trams"() {
        expect:
        predicate.test(LINE) == IS_TRAM

        where:
        LINE  || IS_TRAM
        "0"   || false
        "1"   || true
        "12"  || true
        "10"  || true
        " 10" || true
        "10 " || true
        "99"  || true
        "100" || false
        "182" || false
        "701" || false
        "C23" || false
        "N14" || false
        "S2"  || false
    }
}
