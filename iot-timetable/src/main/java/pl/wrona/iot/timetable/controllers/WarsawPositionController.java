package pl.wrona.iot.timetable.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.apollo.api.PositionsApi;
import pl.wrona.iot.apollo.api.model.Positions;
import pl.wrona.iot.timetable.services.WarsawPositionService;

@RestController
@AllArgsConstructor
public class WarsawPositionController implements PositionsApi {

    private final WarsawPositionService warsawPositionService;

    @Override
    public ResponseEntity<Positions> getPositions() {
        return ResponseEntity.ok(warsawPositionService.warsawPositions());
    }
}
