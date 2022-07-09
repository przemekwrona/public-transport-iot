package pl.wrona.iot.timetable.controllers;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.apollo.api.TimetablesApi;
import pl.wrona.iot.apollo.api.model.EdgeTimetables;
import pl.wrona.iot.apollo.api.model.Timetable;
import pl.wrona.iot.timetable.client.warsaw.WarsawTimetableService;

import java.time.OffsetDateTime;

@RestController
@AllArgsConstructor
public class WarsawTimetableController implements TimetablesApi {

    private final WarsawTimetableService warsawTimetableService;

    @Override
    public ResponseEntity<EdgeTimetables> getEdgeDepartures() {
        return warsawTimetableService.getEdgeTimetables();
    }

    @Override
    @Timed(value = "iot_apollo_api_get_timetables")
    public ResponseEntity<Timetable> getTimetable(OffsetDateTime time, Float lat, Float lon, String line, String brigade) {
        return warsawTimetableService.getTimetableResponse(time, lat, lon, line, brigade);
    }

}
