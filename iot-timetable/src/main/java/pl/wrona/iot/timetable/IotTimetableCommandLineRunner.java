package pl.wrona.iot.timetable;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.timetable.reload.ReloadService;

@Component
@AllArgsConstructor
public class IotTimetableCommandLineRunner implements CommandLineRunner {

    private final ReloadService reloadService;

    @Override
    public void run(String... args) throws Exception {
        reloadService.reloadAll();
    }
}
