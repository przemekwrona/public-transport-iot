package pl.wrona.iotapollo.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.FinalStop;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class WarsawFinalStopService {

    private final Map<String, Set<Stop>> directions = new HashMap<>();

    @Builder
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"stopName"})
    private static class Stop {
        private final String stopName;

    }

    public void addDirection(String line, String direction) {
        if (isNull(directions.get(line))) {
            directions.put(line, new HashSet<>());
        }
        directions.get(line).add(Stop.builder()
                .stopName(direction)
                .build());
    }

    public boolean isDirection(String line, String direction) {
        return directions.get(line).contains(Stop.builder().stopName(direction).build());
    }

    public ResponseEntity<List<FinalStop>> findAll() {
        return ResponseEntity.ok(directions.keySet().stream()
                .map(key -> directions.get(key).stream()
                        .map(stop -> new FinalStop()
                                .line(key)
                                .stopName(stop.getStopName()))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

}
