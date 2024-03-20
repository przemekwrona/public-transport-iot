package pl.wrona.iot.warsaw.timetable.formatter;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Frequency;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import org.onebusaway.gtfs_merge.GtfsMerger;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.metro.*;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroCalendarService;
import pl.wrona.iot.warsaw.timetable.formatter.service.*;
import pl.wrona.iot.warsaw.timetable.formatter.service.AgencyService;
import pl.wrona.iot.warsaw.timetable.formatter.service.CalendarService;
import pl.wrona.iot.warsaw.timetable.formatter.service.FrequencyService;
import pl.wrona.iot.warsaw.timetable.formatter.service.RouteService;
import pl.wrona.iot.warsaw.timetable.formatter.service.StopService;
import pl.wrona.iot.warsaw.timetable.formatter.service.StopTimesService;
import pl.wrona.iot.warsaw.timetable.formatter.service.TripService;
import pl.wrona.iot.warsaw.timetable.formatter.tree.WarsawDeliveredTimetableService;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;
import pl.wrona.iot.warsaw.timetable.formatter.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.List;

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
    private final MetroCalendarService metroCalendarService;

    public File gtfs(File source, File destination) throws IOException {
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
        metroCalendarService.getAll(warsawTree).forEach(writer::handleEntity);

        Optional.of(agencyService.getAgency()).ifPresent(writer::handleEntity);
        calendarService.getAll().forEach(writer::handleEntity);
        stopService.getAll().forEach(writer::handleEntity);
        routeService.getAll().forEach(writer::handleEntity);

        stopTimesService.getAllTrips().forEach(writer::handleEntity);
        stopTimesService.getAllStopTime().forEach(writer::handleEntity);

        writer.close();

        return ZipUtils.zip(destination);
    }

    public File merge(List<File> files, File outputFile) throws IOException {
        GtfsMerger merger = new GtfsMerger();
        merger.run(files, outputFile);

        return outputFile;
    }
}
