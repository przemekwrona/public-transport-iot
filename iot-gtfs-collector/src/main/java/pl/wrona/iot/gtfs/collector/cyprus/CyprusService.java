package pl.wrona.iot.gtfs.collector.cyprus;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igeolab.gtfs.collector.cyprus.api.model.RouteFullResult;
import pl.igeolab.gtfs.collector.cyprus.api.model.Routes;
import pl.igeolab.gtfs.collector.cyprus.api.model.RoutesResponse;

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
        RoutesResponse response = cyprusClient.getRoutes(AGENCY_LARNACA).getBody();
        log.info("Response {}", response);

//        for (Routes routes : response.getResult().getRoutes()) {
//            RouteFullResult routesResponse = cyprusClient.getRouteFull(AGENCY_LARNACA, routes.getRouteId(), MONDAY_THURSDAY).getBody();
//            System.out.println("11");
//        }
    }
}
