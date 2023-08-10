package pl.wrona.iot.timetable.job;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.cache.CacheService;
import pl.wrona.iot.timetable.reload.ReloadService;

import java.io.IOException;

@Component
@AllArgsConstructor
public class WarsawTimetablesJob {

    private final CacheService cacheService;
    private final ReloadService reloadService;

    @Scheduled(cron = "0 0 3 * * ?")
    @Timed(value = "iot_apollo_clear_caches_and_load_stops_and_timetables")
    public void clearAllCachesAndLoadStopsAndTimetables() {
        cacheService.clearCache();

        try {
            reloadService.reloadAll();
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }

}
