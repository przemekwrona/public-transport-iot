package pl.wrona.iot.gps.collector.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.job.WarsawTimetablesJob;


@EnableAsync
@Component
@AllArgsConstructor
public class WarsawTimetableScheduler implements Runnable {

    private final WarsawTimetablesJob warsawTimetablesJob;

    @Async
    @Scheduled(cron = "0 0 4 * * ?")
    @Override
    public void run() {
        warsawTimetablesJob.run();
    }
}
