package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Route;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.igeolab.gtfsserver.entity.Agency;
import pl.igeolab.gtfsserver.entity.Routes;
import pl.igeolab.gtfsserver.repository.RoutesRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoutesService {

    private AgencyService agencyService;
    private RoutesRepository routesRepository;

    @Transactional
    public void save(Collection<Route> routes) {
        Map<String, Agency> agencyMap = routes.stream()
                .map(route -> route.getAgency().getId())
                .collect(Collectors.toSet()).stream()
                .collect(Collectors.toMap(agencyId -> agencyId, agencyId -> agencyService.findById(agencyId)));

        List<Routes> routesEntities = routes.stream()
                .map(route -> Routes.builder()
                        .agency(agencyMap.get(route.getId().getAgencyId()))
                        .routeId(route.getId().getId())
                        .routeShortName(route.getShortName())
                        .routeLongName(route.getLongName())
                        .routeDesc(route.getDesc())
                        .routeType(route.getType())
                        .routeUrl(route.getUrl())
                        .routeColor(route.getColor())
                        .routeTextColor(route.getTextColor())
                        .build())
                .toList();

        routesRepository.saveAll(routesEntities);
    }

    public org.igeolab.iot.gtfs.server.api.model.Routes getRoutes() {
        var routes = routesRepository.findAll().stream()
                .map(route -> new org.igeolab.iot.gtfs.server.api.model.Route()
                        .agencyId(route.getAgency().getAgencyId())
                        .routeId(route.getRouteId())
                        .routeShortName(route.getRouteShortName())
                        .routeLongName(route.getRouteLongName())
                        .routeDesc(route.getRouteDesc())
                        .routeType(route.getRouteType())
                        .routeUrl(route.getRouteUrl())
                        .routeColor(route.getRouteColor()))
                .toList();

        return new org.igeolab.iot.gtfs.server.api.model.Routes()
                .routes(routes);
    }

    public Routes findById(String routeId) {
        return routesRepository.findById(routeId).orElse(null);
    }
}
