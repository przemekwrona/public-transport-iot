package pl.wrona.iot.timetable.reload.predicate;

import java.util.function.Predicate;

public class TramPredicate implements Predicate<String> {

    @Override
    public boolean test(String s) {
        try {
            long line = Long.parseLong(s.trim());
            return line > 0 && line < 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
