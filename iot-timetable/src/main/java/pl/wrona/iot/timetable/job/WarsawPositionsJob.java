package pl.wrona.iot.timetable.job;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.services.WarsawPositionParquetService;

import java.io.IOException;

@Component
@AllArgsConstructor
public class WarsawPositionsJob {

    private final WarsawPositionParquetService warsawPositionParquetService;

    @Scheduled(cron = "*/20 * * * * *")
    public void clearAllCachesAndLoadStopsAndTimetables() throws IOException {
        warsawPositionParquetService.parquetPositions();
    }
}