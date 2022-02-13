package pl.wrona.iothermes.client.apollo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import pl.wrona.iot.apollo.api.model.Timetable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@AllArgsConstructor
public class ApolloTimetableService {

    private final ApolloTimetableClient apolloTimetableClient;

    public Timetable getTimetable(LocalDateTime time, float lat, float lon, String line, String brigade) {
        return Optional.ofNullable(apolloTimetableClient.getTimetable(time.atOffset(ZoneOffset.UTC), lat, lon, line, brigade))
                .map(ResponseEntity::getBody)
                .orElse(null);
    }

}
