package pl.wrona.iot.timetable.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class WarsawVehicleDirectionService {

    private final Map<String, String> directions = new HashMap<>();

    public void addDirection(String vehicleNumber, String direction) {
        if (isNull(directions.get(vehicleNumber))) {
            directions.put(vehicleNumber, direction);
        }
    }

    public void clearVehicleDirection(String vehicleNumber) {
        directions.remove(vehicleNumber);
    }

    public String getDirection(String vehicleNumber) {
        return directions.get(vehicleNumber);
    }

}
