package pl.wrona.iot.timetable.job;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.client.warsaw.WarsawApiService;
import pl.wrona.iot.timetable.client.warsaw.WarsawStopService;
import pl.wrona.iot.timetable.cache.CacheService;
import pl.wrona.iot.timetable.client.warsaw.WarsawTimetableService;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ClearCacheJob {

    private final CacheService cacheService;
    private final WarsawStopService warsawStopService;
    private final WarsawApiService warsawApiService;

    private final WarsawTimetableService warsawTimetableService;


    @Scheduled(cron = "0 0 3 * * ?")
    @Timed(value = "iot_apollo_clear_caches_and_load_stops_and_timetables")
    public void clearAllCachesAndLoadStopsAndTimetables() {
        cacheService.clearCache();
        loadTimetables();
    }

    public void loadTimetables() {
        if (!warsawTimetableService.hasTimetable(LocalDate.now())) {
            warsawStopService.getStops()
                    .forEach(warsawStop -> warsawStopService.getLinesOnStop(warsawStop.getGroup(), warsawStop.getSlupek()).getLines()
                            .forEach(line -> warsawStopService.saveTimetable(warsawStop.getGroup(), warsawStop.getSlupek(),
                                    warsawApiService.getTimetable(warsawStop.getGroup(), warsawStop.getSlupek(), line))));
        }
    }


}
