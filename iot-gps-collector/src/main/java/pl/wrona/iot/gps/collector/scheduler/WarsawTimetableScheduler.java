package pl.wrona.iot.gps.collector.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.job.WarsawTimetablesJob;

@Component
@AllArgsConstructor
public class WarsawTimetableScheduler implements Runnable {

    private final WarsawTimetablesJob warsawTimetablesJob;

    @Scheduled(fixedRate = 5_000)
    @Override
    public void run() {
        warsawTimetablesJob.run();
    }
}
