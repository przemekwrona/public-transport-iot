package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroRoute;

import java.util.List;

@Service
@AllArgsConstructor
public class MetroRouteService {

    private final MetroProperties metroProperties;
    private final MetroAgencyService metroAgencyService;
    private final MetroStopService metroStopService;

    public List<Route> getAll() {
        return metroProperties.getRoutes().stream()
                .map(this::buildRoute)
                .toList();
    }

    public Route findRouteById(String id) {
        return metroProperties.getRoutes().stream()
                .filter(route -> id.equals(route.getId()))
                .findFirst()
                .map(this::buildRoute)
                .orElseThrow();
    }

    private Route buildRoute(MetroRoute metroRoute) {
        AgencyAndId id = new AgencyAndId();
        id.setId(metroRoute.getId());

        Route route = new Route();
        route.setId(id);
        route.setAgency(metroAgencyService.getAgency());
        route.setShortName(metroRoute.getId());

        route.setLongName(metroRoute.getLongName());

        return route;
    }
}
