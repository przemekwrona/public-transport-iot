package pl.wrona.iot.warsaw.timetable.formatter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.warsaw.timetable.formatter.metro.MetroFrequencyService;
import pl.wrona.iot.warsaw.timetable.formatter.metro.MetroRouteService;
import pl.wrona.iot.warsaw.timetable.formatter.metro.MetroStopService;
import pl.wrona.iot.warsaw.timetable.formatter.metro.MetroTripService;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MetroCommandLine implements CommandLineRunner {

    private final MetroStopService metroStopService;
    private final MetroRouteService metroRouteService;
    private final MetroTripService metroTripService;
    private final MetroFrequencyService metroFrequencyService;

    @Override
    public void run(String... args) throws Exception {
        List<Stop> stops = metroStopService.getAll();
        List<Route> routes = metroRouteService.getAll();
        metroTripService.getAll();
        metroFrequencyService.getAll();

        log.info("Load metro configuration");
    }
}
