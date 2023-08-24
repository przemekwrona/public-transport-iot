package pl.wrona.iot.formatter.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Trip;
import org.springframework.stereotype.Service;
import pl.wrona.iot.formatter.service.model.DayVariant;
import pl.wrona.iot.formatter.service.model.DepartureDetails;
import pl.wrona.iot.formatter.service.model.LineNumber;
import pl.wrona.iot.formatter.service.model.LineVariant;
import pl.wrona.iot.formatter.service.model.StopDetails;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TripService {
    public static final String SECTION = "*LL";
    public static final String ROUTE_SECTION = "*TR";
    public static final String STOP_SECTION = "*RP";
    public static final String DAY_OF_THE_WEEK_SECTION = "*TD";
    public static final String DEPARTURE_SECTION = "*OD";

    private final StopService stopService;
    private final RouteService routeService;

    private Map<String, Trip> trips;

    public void process(WarsawTree tree) {
        WarsawTree.Node warsawLines = tree.getNode().getNode(SECTION);

        this.trips = warsawLines.getNodes().stream()
                .map(warsawLine -> {
                    LineNumber lineNumber = LineNumber.of(warsawLine.getValue());
                    return warsawLine.getNode(ROUTE_SECTION).getNodes().stream()
                            .map(routesVariant -> {
                                LineVariant lineVariant = LineVariant.of(warsawLine.getValue(), routesVariant.getValue());
                                return routesVariant.getNodes(STOP_SECTION).stream()
                                        .findFirst()
                                        .map(stopVariant -> {
                                            StopDetails stopDetails = StopDetails.of(stopVariant.getValue());
                                            return stopVariant.getNodes(DAY_OF_THE_WEEK_SECTION).stream()
                                                    .map(dayType -> {
                                                        DayVariant dayVariant = DayVariant.of(dayType.getValue());
                                                        return dayType.getNodes(DEPARTURE_SECTION).stream()
                                                                .map(departureVariant -> {
                                                                    DepartureDetails departureDetails = DepartureDetails.of(departureVariant.getValue());

                                                                    AgencyAndId tripId = new AgencyAndId();
                                                                    tripId.setId(departureDetails.getTripId());

                                                                    AgencyAndId serviceId = new AgencyAndId();
                                                                    serviceId.setId(String.format("%s/%s", lineNumber.getLine(), dayVariant.getDayType()));

                                                                    Trip trip = new Trip();
                                                                    trip.setId(tripId);
                                                                    trip.setServiceId(serviceId);
                                                                    trip.setRoute(routeService.findRouteByIf(lineNumber.getLine()));
                                                                    return trip;
                                                                })
                                                                .collect(Collectors.toList());
                                                    })
                                                    .flatMap(Collection::stream)
                                                    .collect(Collectors.toList());
                                        }).orElseThrow();
                            })
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(trip -> trip.getId().getId(), Function.identity(), (c, p) -> c));
    }

    public Trip findTripById(String tripId) {
        return this.trips.get(tripId);
    }

}
