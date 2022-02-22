package pl.wrona.iotapollo.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.FinalStop;

import javax.swing.text.html.parser.Entity;
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

    private final Map<String, Set<String>> directions = new HashMap<>();

    public void addDirection(String line, String direction) {
        if (isNull(directions.get(line))) {
            directions.put(line, new HashSet<>());
        }
        directions.get(line).add(line);
    }

    public boolean isDirection(String line, String direction) {
        return directions.get(line).contains(direction);
    }

    public ResponseEntity<List<FinalStop>> findAll() {
        return ResponseEntity.ok(directions.keySet().stream()
                .map(key -> directions.get(key).stream()
                        .map(stopName -> new FinalStop()
                                .line(key)
                                .stopName(stopName))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

}
