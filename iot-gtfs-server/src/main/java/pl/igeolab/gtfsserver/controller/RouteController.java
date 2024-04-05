package pl.igeolab.gtfsserver.controller;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.RouteApi;
import org.igeolab.iot.gtfs.server.api.model.Routes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.igeolab.gtfsserver.service.RoutesService;

@RestController
@AllArgsConstructor
public class RouteController implements RouteApi {
    private final RoutesService routesService;

    @Override
    public ResponseEntity<Routes> getRoutes() {
        return ResponseEntity.ok(routesService.getRoutes());
    }
}
