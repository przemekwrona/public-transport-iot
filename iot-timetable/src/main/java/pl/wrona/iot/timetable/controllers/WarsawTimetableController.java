package pl.wrona.iot.timetable.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.apollo.api.TimetablesApi;
import pl.wrona.iot.apollo.api.model.EdgeTimetables;
import pl.wrona.iot.apollo.api.model.Timetable;

import java.time.OffsetDateTime;

@RestController
@AllArgsConstructor
public class WarsawTimetableController implements TimetablesApi {

    @Override
    public ResponseEntity<EdgeTimetables> getEdgeDepartures() {
        return null;
    }

    @Override
    public ResponseEntity<Timetable> getTimetable(OffsetDateTime time, Float lat, Float lon, String line, String brigade) {
        return null;
    }

}
