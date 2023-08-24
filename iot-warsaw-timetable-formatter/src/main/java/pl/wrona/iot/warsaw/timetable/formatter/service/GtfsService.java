package pl.wrona.iot.warsaw.timetable.formatter.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.tree.WarsawDeliveredTimetableService;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class GtfsService {
    private final WarsawDeliveredTimetableService warsawDeliveredTimetableService;

    private final AgencyService agencyService;

    private CalendarService calendarService;
    private final StopService stopService;
    private final RouteService routeService;
    private final TripService tripService;
    private final StopTimesService stopTimesService;

    public void gtfs(File source, File destination) throws IOException {
        WarsawTree warsawTree = warsawDeliveredTimetableService.load(source);

        calendarService.process(warsawTree);
        stopService.process(warsawTree);
        routeService.process(warsawTree);
        tripService.process(warsawTree);
        stopTimesService.process(warsawTree);

        GtfsWriter writer = new GtfsWriter();
        writer.setOutputLocation(destination);

        writer.handleEntity(agencyService.getAgency());
        calendarService.getAll().forEach(writer::handleEntity);
        stopService.getAll().forEach(writer::handleEntity);
        routeService.getAll().forEach(writer::handleEntity);

        stopTimesService.getAllTrips().forEach(writer::handleEntity);
        stopTimesService.getAllStopTime().forEach(writer::handleEntity);

        writer.close();
    }
}
