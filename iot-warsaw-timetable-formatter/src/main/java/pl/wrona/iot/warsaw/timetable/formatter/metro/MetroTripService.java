package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.*;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;

import java.util.*;

@Service
@AllArgsConstructor
public class MetroTripService {

    private final MetroProperties metroProperties;
    private final MetroRouteService metroRouteService;

    public List<Trip> getAll() {
        return metroProperties.getRoutes().stream()
                .map(metroRoute -> metroRoute
                        .getTrips().stream()
                        .filter(Objects::nonNull)
                        .map(metroTrip -> {
                            AgencyAndId serviceId = new AgencyAndId();
                            serviceId.setId(metroTrip.getTripId());

                            AgencyAndId tripId = new AgencyAndId();
                            tripId.setId(metroTrip.getTripId());

                            Trip trip = new Trip();
                            trip.setId(tripId);
                            trip.setServiceId(serviceId);

                            Route route = metroRouteService.findRouteById(metroRoute.getId());
                            trip.setRoute(route);

                            return trip;
                        }).toList())
                .flatMap(Collection::stream)
                .toList();
    }

    public Trip findTripById(String tripId) {
        return getAll().stream()
                .filter(trip -> tripId.equals(trip.getId().getId()))
                .findFirst()
                .orElseThrow();
    }
}
