package pl.wrona.iot.gps.collector;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.job.WarsawTimetablesJob;

@Component
@AllArgsConstructor
public class IotGpsCollectorCommandLine implements CommandLineRunner {

    private final WarsawTimetablesJob warsawTimetablesJob;

    @Override
    public void run(String... args) throws Exception {
        warsawTimetablesJob.run();
    }
}
