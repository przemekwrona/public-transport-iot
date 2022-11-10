package pl.wrona.iot.gps.collector.client;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gps.collector.config.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawTimetables;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WarsawTimetableService {

    private final WarsawPublicTransportClient warsawPublicTransportClient;
    private final WarsawUmApiConfiguration warsawUmApiConfiguration;

    public WarsawTimetables getTimetable(String stopId, String stopNumber, String line) {
        return Optional.ofNullable(warsawPublicTransportClient.getTimetable(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getTimetablesResourceId(), stopId, stopNumber, line))
                .map(ResponseEntity::getBody)
                .orElse(new WarsawTimetables());
    }
}
