package pl.wrona.iothermes.client.apollo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.Timetable;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApolloTimetableService {
    private final ZoneId WARSAW_ZONE_ID = ZoneId.of("Europe/Warsaw");

    private final ApolloTimetableClient apolloTimetableClient;

    public Timetable getTimetable(Instant time, float lat, float lon, String line, String brigade) {
        return Optional.ofNullable(apolloTimetableClient.getTimetable(time.atOffset(WARSAW_ZONE_ID.getRules().getOffset(time)), lat, lon, line, brigade))
                .map(ResponseEntity::getBody)
                .orElse(null);
    }

}
