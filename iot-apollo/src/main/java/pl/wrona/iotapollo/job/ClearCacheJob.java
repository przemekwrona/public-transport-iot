package pl.wrona.iotapollo.job;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iotapollo.cache.CacheService;
import pl.wrona.iotapollo.client.warsaw.WarsawApiService;
import pl.wrona.iotapollo.client.warsaw.WarsawStopService;

@Component
@AllArgsConstructor
public class ClearCacheJob {

    private final CacheService cacheService;
    private final WarsawStopService warsawStopService;
    private final WarsawApiService warsawApiService;


    @Scheduled(cron = "0 0 4 * * ?")
    @Timed(value = "iot_apollo_clear_caches_and_load_stops_and_timetables")
    public void clearAllCachesAndLoadStopsAndTimetables() {
        cacheService.clearCache();

        warsawStopService.getStops()
                .forEach(warsawStop -> warsawStopService.getLinesOnStop(warsawStop.getGroup(), warsawStop.getSlupek()).getLines()
                        .forEach(line -> warsawApiService.getTimetable(warsawStop.getGroup(), warsawStop.getSlupek(), line)));
    }

}
