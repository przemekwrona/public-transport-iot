package pl.wrona.iot.gps.collector.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.job.WarsawVehiclesPositionsJob;

@Component
@AllArgsConstructor
public class WarsawVehiclesPositionsScheduler implements Runnable {

    private static final int TWENTY_SECONDS = 20_000;

    private final WarsawVehiclesPositionsJob warsawVehiclesPositionsJob;

    @Scheduled(fixedRate = TWENTY_SECONDS)
    @Override
    public void run() {
        warsawVehiclesPositionsJob.run();
    }

}
