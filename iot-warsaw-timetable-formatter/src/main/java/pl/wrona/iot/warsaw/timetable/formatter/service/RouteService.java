package pl.wrona.iot.warsaw.timetable.formatter.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.LineVariant;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteService {
    public static final String SECTION = "*LL";
    public static final String ROUTE_SECTION = "*TR";

    private final AgencyService agencyService;
    private Map<String, Route> routes = new HashMap<>();

    public void process(WarsawTree tree) {
        WarsawTree.Node warsawStops = tree.getNode().getNode(SECTION);

        this.routes = warsawStops.getNodes().stream()
                .map(warsawRoute -> warsawRoute.getNode(ROUTE_SECTION).getNodes().stream()
                        .map(routeOption -> LineVariant.of(warsawRoute.getValue(), routeOption.getValue()))
                        .sorted(Comparator.comparing(LineVariant::getPos).thenComparing(LineVariant::getDirection))
                        .limit(1)
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .map(warsawVariant -> buildRoute(warsawVariant))
                .collect(Collectors.toMap(route -> route.getId().getId(), Function.identity()));
    }

    private Route buildRoute(LineVariant warsawVariant) {
        AgencyAndId routeId = new AgencyAndId();
        routeId.setId(warsawVariant.getLine());

        Route route = new Route();
        route.setId(routeId);
        route.setAgency(agencyService.getAgency());
        route.setShortName(warsawVariant.getLine());
        route.setLongName(String.format("%s - %s", warsawVariant.getFromStop(), warsawVariant.getDestinationStop()));

        return route;
    }

    public Route findRouteByIf(String routeId) {
        return this.routes.get(routeId);
    }

    public List<Route> getAll() {
        return new ArrayList<>(this.routes.values());
    }
}
