package pl.wrona.iot.formatter.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class GtfsService {

    private final AgencyService agencyService;

    private CalendarService calendarService;
    private final StopService stopService;
    private final RouteService routeService;
    private final TripService tripService;
    private final StopTimesService stopTimesService;

    public void gtfs() throws IOException {
        String path = "src/test/resources/RA230827.TXT";
        File file = new File(path);

        WarsawTree warsawTree = new WarsawTree();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                warsawTree.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        calendarService.process(warsawTree);
        stopService.process(warsawTree);
        routeService.process(warsawTree);
        tripService.process(warsawTree);
        stopTimesService.process(warsawTree);

        GtfsWriter writer = new GtfsWriter();
        Path gtfsPath = Path.of("src/test/resources/warsaw.gtfs");
        writer.setOutputLocation(new File(gtfsPath.toAbsolutePath().toString()));

        writer.handleEntity(agencyService.getAgency());
        calendarService.getAll().forEach(writer::handleEntity);
        stopService.getAll().forEach(writer::handleEntity);
        routeService.getAll().forEach(writer::handleEntity);

        stopTimesService.getAllTrips().forEach(writer::handleEntity);
        stopTimesService.getAllStopTime().forEach(writer::handleEntity);

        writer.close();
    }
}
