
package pl.wrona.iot.timetable.job;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.services.WarsawPositionParquetService;

import java.io.IOException;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class WarsawPositionsMergerJob {
    private final WarsawPositionParquetService warsawPositionParquetService;

    @Scheduled(cron = "0 0 2 * * *")
    public void clearAllCachesAndLoadStopsAndTimetables() throws IOException {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1L);
        warsawPositionParquetService.mergeParquet(yesterday);
    }
}
