package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.igeolab.gtfsserver.entity.Trips;
import pl.igeolab.gtfsserver.repository.TripRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final CalendarDateService calendarDateService;
    private final RoutesService routesService;

    @Transactional
    public void save(Collection<org.onebusaway.gtfs.model.Trip> trips) {
        var tripEntities = trips.stream()
                .map(trip -> Trips.builder()
                        .tripId(trip.getId().getId())
                        .route(routesService.findById(trip.getRoute().getId().getId()))
                        .tripHeadsign(trip.getTripHeadsign())
                        .tripShortName(trip.getTripShortName())
                        .directionId(trip.getDirectionId())
                        .shapeId(trip.getShapeId().getId())
                        .calendarDate(calendarDateService.findByServiceId(trip.getServiceId().getId()).orElse(null))
                        .build())
                .toList();

        tripRepository.saveAll(tripEntities);
    }

    public org.igeolab.iot.gtfs.server.api.model.Trips getTrips(String line) {
        var trips = tripRepository.findAllByRouteId(line).stream()
                .map(trip -> new org.igeolab.iot.gtfs.server.api.model.Trip()
                        .routeId(trip.getRoute().getRouteId())
                        .tripId(trip.getTripId())
                        .tripHeadSign(trip.getTripHeadsign())
                        .tripShortName(trip.getTripShortName())
                        .directionId(trip.getDirectionId())
                        .blockId(null)
                        .shapeId(trip.getShapeId()))
                .toList();

        return new org.igeolab.iot.gtfs.server.api.model.Trips()
                .trips(trips);
    }

    public Trips findById(String tripId) {
        return tripRepository.findById(tripId).orElse(null);
    }
}
