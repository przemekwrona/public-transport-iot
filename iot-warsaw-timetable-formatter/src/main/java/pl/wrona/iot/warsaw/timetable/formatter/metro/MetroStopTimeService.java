package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.StopTime;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;
import pl.wrona.iot.warsaw.timetable.formatter.service.TripService;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class MetroStopTimeService {

    private final MetroProperties metroProperties;
    private final MetroStopService metroStopService;
    private final MetroTripService metroTripService;

    public List<StopTime> getAll() {
        return metroProperties.getRoutes().stream()
                .map(metroRoute -> metroRoute
                        .getTrips().stream()
                        .map(metroTrip -> metroTrip.getFrequencies().stream()
                                .map(metroFrequency ->
                                        metroRoute.getStops().stream()
                                                .map(metroStop -> {
                                                    StopTime stopTime = new StopTime();
                                                    stopTime.setArrivalTime(metroFrequency.getStartTime().toSecondOfDay());
                                                    stopTime.setDepartureTime(metroFrequency.getStartTime().toSecondOfDay());
                                                    stopTime.setStop(metroStopService.findStopById(metroStop.getId()));
                                                    stopTime.setTrip(metroTripService.findTripById(metroTrip.getTripId()));
                                                    return stopTime;
                                                })
                                                .toList())
                                .flatMap(Collection::stream)
                                .toList())
                        .flatMap(Collection::stream)
                        .toList())
                .flatMap(Collection::stream)
                .toList();
    }
}
