package pl.wrona.iot.gtfs.collector.cyprus;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igeolab.gtfs.collector.cyprus.api.model.*;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
class CyprusService {
    public static final int AGENCY_LARNACA = 10;
    public static final int MONDAY_THURSDAY = 0;
    public static final int FRIDAY = 1;
    public static final int SATURDAY = 2;
    public static final int SUNDAY = 3;

    private final CyprusClient cyprusClient;

    public void generateGtfs() {
        for (Integer agencyId : List.of(AGENCY_LARNACA)) {

            RoutesResponse response = cyprusClient.getRoutes(agencyId).getBody();

            for (Routes routes : response.getResult().getRoutes()) {
                RouteFullResponse routesResponse = cyprusClient.getRouteFull(routes.getAgencyId(), routes.getRouteId(), MONDAY_THURSDAY).getBody();

                for (SelectedRoute selectedRoute : routesResponse.getResult().getSelectedRoutes()) {
                    RouteCalendarStoptimesResponse mondayThursday = cyprusClient.getRouteCalendarStoptimes(selectedRoute.getRouteId(), MONDAY_THURSDAY).getBody();
                    RouteCalendarStoptimesResponse friday = cyprusClient.getRouteCalendarStoptimes(selectedRoute.getRouteId(), FRIDAY).getBody();
                    RouteCalendarStoptimesResponse saturday = cyprusClient.getRouteCalendarStoptimes(selectedRoute.getRouteId(), SATURDAY).getBody();
                    RouteCalendarStoptimesResponse sunday = cyprusClient.getRouteCalendarStoptimes(selectedRoute.getRouteId(), SUNDAY).getBody();
                }
            }
        }
    }
}
