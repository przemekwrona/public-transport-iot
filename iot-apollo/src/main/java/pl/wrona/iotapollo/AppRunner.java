package pl.wrona.iotapollo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iotapollo.client.warsaw.WarsawStopService;
import pl.wrona.iotapollo.client.warsaw.WarsawTimetableService;

@Slf4j
@Component
@AllArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final WarsawStopService warsawStopService;
    private final WarsawTimetableService timetableService;

    @Override
    public void run(String... args) throws Exception {
        warsawStopService.getStops();
    }

}
