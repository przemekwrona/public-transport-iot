package pl.wrona.iot.gtfs.collector;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gtfs.collector.gdansk.GdanskService;
import pl.wrona.iot.gtfs.collector.warszawa.WarszawaService;

@Component
@AllArgsConstructor
public class CommandLine implements CommandLineRunner {

    private final GdanskService gdanskService;
    private final WarszawaService warszawaService;

    @Override
    public void run(String... args) throws Exception {
        warszawaService.getTimetableFromFTP();
//        gdanskService.getLastUpdate();
    }
}
