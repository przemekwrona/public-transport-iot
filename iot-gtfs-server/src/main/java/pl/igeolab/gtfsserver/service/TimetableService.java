package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.model.Timetable;
import org.igeolab.iot.gtfs.server.api.model.Timetables;
import org.springframework.stereotype.Service;
import pl.igeolab.gtfsserver.entity.Stops;
import pl.igeolab.gtfsserver.repository.StopTimeRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class TimetableService {

    private final StopTimeRepository stopTimeRepository;
    private final StopService stopService;

    public Timetables getTimetableByStopId(String stopId) {
        Stops stops = stopService.findById(stopId);

        List<Timetable> stopTimes = stopTimeRepository.findStopTimeByStopId(stopId).stream()
                .map(stopTime -> new Timetable()
                        .line(stopTime.getTrips().getRoute().getRouteShortName())
                        .direction(stopTime.getTrips().getTripHeadsign())
                        .arrivalTime(stopTime.getTrips()
                                .getCalendarDate()
                                .getDate()
                                .atStartOfDay(ZoneId.of("Europe/Warsaw"))
                                .plus(stopTime.getArrivalTime(), ChronoUnit.SECONDS)
                                .toOffsetDateTime())
                        .departureTime(stopTime.getTrips()
                                .getCalendarDate()
                                .getDate()
                                .atStartOfDay(ZoneId.of("Europe/Warsaw"))
                                .plus(stopTime.getDepartureTime(), ChronoUnit.SECONDS)
                                .toOffsetDateTime())
                        .stopSequence(stopTime.getStopSequence()))
                .toList();

        return new Timetables()
                .stopId(stops.getStopId())
                .stopName(stops.getStopName())
                .stops(stopTimes);
    }

}
