package pl.wrona.iot.timetable.reload.predicate;

import java.util.function.Predicate;

public class NightBusPredicate implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return s.trim().startsWith("N");
    }
}
