package pl.wrona.iot.timetable.reload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.client.warsaw.WarsawApiService;
import pl.wrona.iot.timetable.client.warsaw.WarsawDepartures;
import pl.wrona.iot.timetable.client.warsaw.WarsawStopService;
import pl.wrona.iot.timetable.reload.predicate.BusPredicate;
import pl.wrona.iot.timetable.reload.predicate.NightBusPredicate;
import pl.wrona.iot.timetable.reload.predicate.OtherPredicate;
import pl.wrona.iot.timetable.reload.predicate.TramPredicate;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class ReloadService {
    private final WarsawStopService warsawStopService;
    private final WarsawApiService warsawApiService;

    public void reloadAll() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);

        new Thread(new ReloadComponent(this, countDownLatch, new BusPredicate())).start();
        new Thread(new ReloadComponent(this, countDownLatch, new TramPredicate())).start();
        new Thread(new ReloadComponent(this, countDownLatch, new NightBusPredicate())).start();
        new Thread(new ReloadComponent(this, countDownLatch, new OtherPredicate())).start();

        countDownLatch.await();
    }

    public void reload(Predicate<String> predicate) {
        warsawStopService.getStops().stream()
                .map(warsawStop -> warsawStopService.getLinesOnStop(warsawStop.getGroup(), warsawStop.getSlupek()))
                .flatMap(warsawStop -> warsawStop.getLines().stream()
                        .map(line -> new WarsawStop(warsawStop.getStopId(), warsawStop.getStopNumber(), line)))
                .filter(warsawStop -> predicate.test(warsawStop.getLine()))
                .map(stop -> {
                    List<WarsawDepartures> timetables = warsawApiService.getTimetable(stop.getStopId(), stop.getStopNumber(), stop.getLine());
                    return new WarsawTimetable(stop.getStopId(), stop.getStopNumber(), timetables);
                })
                .forEach(stop -> warsawStopService.saveTimetable(stop.getStopId(), stop.getStopNumber(), stop.getTimetables()));
    }

    @Data
    private static class WarsawStop {

        private final String stopId;
        private final String stopNumber;
        private final String line;
    }

    @Data
    private static class WarsawTimetable {

        private final String stopId;
        private final String stopNumber;
        private final List<WarsawDepartures> timetables;
    }
}
