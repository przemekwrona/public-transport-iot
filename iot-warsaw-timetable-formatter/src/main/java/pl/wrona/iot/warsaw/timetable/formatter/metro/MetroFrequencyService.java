package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Frequency;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class MetroFrequencyService {

    private final MetroProperties metroProperties;
    private final MetroTripService metroTripService;

    public List<Frequency> getAll() {
        return metroProperties.getRoutes().stream()
                .map(metroRoute -> metroRoute
                        .getTrips().stream()
                        .map(metroTrip -> metroTrip.getFrequencies().stream()
                                .map(metroFrequency -> {
                                    Frequency frequency = new Frequency();
                                    frequency.setTrip(metroTripService.findTripById(metroTrip.getTripId()));
                                    frequency.setStartTime(metroFrequency.getStartTime().toSecondOfDay());
                                    frequency.setEndTime(metroFrequency.getEndTimeSecondOfDay());
                                    frequency.setExactTimes(1);
                                    frequency.setHeadwaySecs(metroFrequency.getTiming());
                                    return frequency;
                                })
                                .toList())
                        .flatMap(Collection::stream)
                        .toList())
                .flatMap(Collection::stream)
                .toList();
    }

}
