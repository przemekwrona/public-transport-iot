package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.model.Departure;
import org.igeolab.iot.gtfs.server.api.model.Departures;
import org.springframework.stereotype.Service;
import pl.igeolab.gtfsserver.repository.StopTimeRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@AllArgsConstructor
public class StopTimeService {

    private final StopTimeRepository stopTimeRepository;
    private final StopService stopService;
    private final TripService tripService;

    public Departures getDeparturesByStopId(String agencyCode, String stopId) {
        LocalDateTime now = LocalDateTime.of(2024, 3, 14, 5, 0);
        var stopTimes = stopTimeRepository.findStopTimeByStopId(stopId);

        return new Departures()
                .departures(stopTimes.stream()
                        .map(departure -> new Departure()
                                .line(departure.getTrips().getRoute().getRouteShortName())
                                .headsign(departure.getTrips().getTripHeadsign())
                                .date(departure.getTrips().getCalendarDate().getDate()
                                        .atStartOfDay()
                                        .plusSeconds(departure.getDepartureTime())
                                        .atZone(ZoneId.of("Europe/Warsaw")).toOffsetDateTime()))
                        .toList());
    }

}
