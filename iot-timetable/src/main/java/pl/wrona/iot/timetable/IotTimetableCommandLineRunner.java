package pl.wrona.iot.timetable;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.job.ClearCacheJob;

@Component
@AllArgsConstructor
public class IotTimetableCommandLineRunner implements CommandLineRunner {

    private final ClearCacheJob clearCacheJob;

    @Override
    public void run(String... args) throws Exception {
        clearCacheJob.loadTimetables();
    }
}
