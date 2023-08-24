package pl.wrona.iot.warsaw.timetable.formatter.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.LineNumber;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.StopTimeDetails;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StopTimesService {
    public static final String SECTION = "*LL";
    public static final String STOP_TIME_SECTION = "*WK";

    private final StopService stopService;
    private final RouteService routeService;
    private final TripService tripService;
    private final CalendarService calendarService;

    private List<Pair<Trip, StopTime>> results;

    public void process(WarsawTree tree) {
        WarsawTree.Node warsawLines = tree.getNode().getNode(SECTION);

        this.results = warsawLines.getNodes().stream()
                .map(warsawLine -> {
                    LineNumber lineNumber = LineNumber.of(warsawLine.getValue());

                    AtomicInteger stopSequence = new AtomicInteger();

                    return warsawLine.getNode(STOP_TIME_SECTION).getNodes().stream()
                            .map(warsawStopTime -> {
                                StopTimeDetails stopTimeDetails = StopTimeDetails.of(warsawStopTime.getValue());

                                AgencyAndId serviceId = new AgencyAndId();
                                serviceId.setId(String.format("%s/%s", lineNumber.getLine(), stopTimeDetails.getDayType()));

                                if (!calendarService.has(serviceId.getId())) {
                                    return null;
                                }

                                AgencyAndId tripId = new AgencyAndId();
                                tripId.setId(String.format("%s/%s", lineNumber.getLine(), stopTimeDetails.getTripId()));

                                Trip trip = new Trip();
                                trip.setId(tripId);
                                trip.setServiceId(serviceId);
                                trip.setRoute(routeService.findRouteByIf(lineNumber.getLine()));

                                stopSequence.set(stopSequence.get() + 1);
                                StopTime stopTime = new StopTime();
                                stopTime.setStop(stopService.findStopByIf(stopTimeDetails.getStopId()));
                                stopTime.setTrip(trip);
                                stopTime.setArrivalTime((int) stopTimeDetails.toSeconds());
                                stopTime.setDepartureTime((int) stopTimeDetails.toSeconds());
                                stopTime.setStopSequence(stopSequence.get());
                                stopTime.setTimepoint(1);

                                return Pair.of(trip, stopTime);
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    public List<Trip> getAllTrips() {
        return new ArrayList<>(results.stream()
                .map(Pair::getLeft)
                .collect(Collectors.toMap(trip -> trip.getId().getId(), Function.identity(), (c, p) -> c))
                .values());

    }

    public List<StopTime> getAllStopTime() {
        return results.stream()
                .map(Pair::getRight)
                .collect(Collectors.toList());
    }

}
