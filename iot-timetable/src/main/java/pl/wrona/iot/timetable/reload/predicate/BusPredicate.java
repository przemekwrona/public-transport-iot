package pl.wrona.iot.timetable.reload.predicate;

import java.util.function.Predicate;

public class BusPredicate implements Predicate<String> {

    @Override
    public boolean test(String s) {
        try {
            long line = Long.parseLong(s.trim());
            return line >= 100 && line < 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
