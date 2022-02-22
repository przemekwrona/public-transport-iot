package pl.wrona.iotapollo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class WarsawStopDirectionService {

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
}
