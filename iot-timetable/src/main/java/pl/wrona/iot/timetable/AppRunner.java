package pl.wrona.iot.timetable;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.client.warsaw.WarsawStopService;
import pl.wrona.iot.timetable.client.warsaw.WarsawTimetableService;

@Slf4j
@Component
@AllArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final WarsawStopService warsawStopService;

    @Override
    public void run(String... args) throws Exception {
//        warsawStopService.getStops();
    }

}
