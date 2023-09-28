package pl.wrona.iot.warsaw.timetable.formatter;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.metro.*;
import pl.wrona.iot.warsaw.timetable.formatter.service.*;
import pl.wrona.iot.warsaw.timetable.formatter.tree.WarsawDeliveredTimetableService;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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

    private final MetroAgencyService metroAgencyService;
    private final MetroStopService metroStopService;
    private final MetroRouteService metroRouteService;
    private final MetroTripService metroTripService;
    private final MetroFrequencyService metroFrequencyService;
    private final MetroStopTimeService metroStopTimeService;

    public void gtfs(File source, File destination) throws IOException {
        WarsawTree warsawTree = warsawDeliveredTimetableService.load(source);

        calendarService.process(warsawTree);
        stopService.process(warsawTree);
        routeService.process(warsawTree);
        tripService.process(warsawTree);
        stopTimesService.process(warsawTree);

        GtfsWriter writer = new GtfsWriter();
        writer.setOutputLocation(destination);

        Optional.of(metroAgencyService.getAgency()).ifPresent(writer::handleEntity);
        metroStopService.getAll().forEach(writer::handleEntity);
        metroRouteService.getAll().forEach(writer::handleEntity);
        metroTripService.getAll().forEach(writer::handleEntity);
        metroFrequencyService.getAll().forEach(writer::handleEntity);
        metroStopTimeService.getAll().forEach(writer::handleEntity);

        Optional.of(agencyService.getAgency()).ifPresent(writer::handleEntity);
        calendarService.getAll().forEach(writer::handleEntity);
        stopService.getAll().forEach(writer::handleEntity);
        routeService.getAll().forEach(writer::handleEntity);

        stopTimesService.getAllTrips().forEach(writer::handleEntity);
        stopTimesService.getAllStopTime().forEach(writer::handleEntity);

        writer.close();
    }
}
