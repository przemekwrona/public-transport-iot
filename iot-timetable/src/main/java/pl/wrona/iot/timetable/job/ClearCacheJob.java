package pl.wrona.iot.timetable.job;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.cache.CacheService;
import pl.wrona.iot.timetable.reload.ReloadComponent;
import pl.wrona.iot.timetable.reload.ReloadService;
import pl.wrona.iot.timetable.reload.predicate.BusPredicate;
import pl.wrona.iot.timetable.reload.predicate.NightBusPredicate;
import pl.wrona.iot.timetable.reload.predicate.OtherPredicate;
import pl.wrona.iot.timetable.reload.predicate.TramPredicate;

import java.util.concurrent.CountDownLatch;

@Component
@AllArgsConstructor
public class ClearCacheJob {

    private final CacheService cacheService;
    private final ReloadService reloadService;

    @Scheduled(cron = "0 0 1 * * ?")
    @Timed(value = "iot_apollo_clear_caches_and_load_stops_and_timetables")
    public void clearAllCachesAndLoadStopsAndTimetables() throws InterruptedException {
        cacheService.clearCache();
        reloadService.reloadAll();
    }

}
