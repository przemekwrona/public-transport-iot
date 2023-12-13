package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.RequiredArgsConstructor;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Stop;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroStop;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetroStopService {

    private final MetroProperties metroProperties;

    public List<Stop> getAll() {
        return metroProperties.getStops().stream()
                .map(this::buildStop)
                .toList();
    }

    public Stop findStopById(String id) {
        return metroProperties.getStops().stream()
                .filter(stop -> id.equals(stop.getId()))
                .findFirst()
                .map(this::buildStop)
                .orElseThrow();
    }

    private Stop buildStop(MetroStop metroStop) {
        AgencyAndId id = new AgencyAndId();
        id.setId(metroStop.getId());

        Stop stop = new Stop();
        stop.setId(id);
        stop.setName(metroStop.getName());
        stop.setLat(metroStop.getLat());
        stop.setLon(metroStop.getLon());

        return stop;
    }


}
