package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Frequency;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroTrip;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroTripStop;
import pl.wrona.iot.warsaw.timetable.formatter.service.RouteService;
import pl.wrona.iot.warsaw.timetable.formatter.service.StopService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MetroTripService {

    private final MetroProperties metroProperties;
    private final RouteService routeService;
    private final StopService stopService;

    public List<Trip> getAll() {

        metroProperties.getTrips().forEach(metroTrip -> {

            Route route = routeService.findRouteByIf(metroTrip.getRouteId());

            Optional.of(metroTrip)
                    .map(MetroTrip::getMondayFriday)
                    .ifPresent(warsawMetroTrip -> {
                        Trip trip = new Trip();
                        trip.setRoute(route);

                        warsawMetroTrip.getFrequencies().forEach(metroFrequency -> {
                            Frequency frequency = new Frequency();
                            frequency.setTrip(trip);
                            frequency.setStartTime(metroFrequency.getStartTime().toSecondOfDay());
                            frequency.setEndTime(metroFrequency.getEndTime().toSecondOfDay());
                            frequency.setExactTimes(metroFrequency.getTiming());
                        });

                        List<StopTime> stopTimes = new ArrayList<>();

                        LocalTime startTime = warsawMetroTrip.getFrequencies().get(0).getStartTime();

                        for(MetroTripStop tripStop: metroTrip.getStops()) {
                            startTime.plusMinutes(tripStop.getTime());

                            StopTime stopTime = new StopTime();
                            stopTime.setTrip(trip);
                            stopTime.setStop(stopService.findStopByIf(tripStop.getId()));
                            stopTime.setArrivalTime(startTime.toSecondOfDay());
                            stopTime.setDepartureTime(startTime.toSecondOfDay());
                        }
                    });
        });

        return List.of();
    }
}
