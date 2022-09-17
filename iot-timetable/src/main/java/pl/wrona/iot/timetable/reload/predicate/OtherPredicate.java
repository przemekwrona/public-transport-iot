package pl.wrona.iot.timetable.reload.predicate;

import java.util.function.Predicate;

public class OtherPredicate implements Predicate<String> {

    private final BusPredicate busPredicate = new BusPredicate();
    private final NightBusPredicate nightBusPredicate = new NightBusPredicate();
    private final TramPredicate tramPredicate = new TramPredicate();

    @Override
    public boolean test(String s) {
        return !busPredicate.test(s) && !tramPredicate.test(s) && !nightBusPredicate.test(s);
    }
}
