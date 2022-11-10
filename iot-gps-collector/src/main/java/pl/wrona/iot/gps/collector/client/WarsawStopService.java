package pl.wrona.iot.gps.collector.client;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gps.collector.config.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawStops;
import pl.wrona.warsaw.transport.api.model.WarsawTimetables;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WarsawStopService {

    private final WarsawPublicTransportClient warsawPublicTransportClient;
    private final WarsawUmApiConfiguration warsawUmApiConfiguration;

    public WarsawStops getStops() {
        return Optional.ofNullable(warsawPublicTransportClient.getStops(warsawUmApiConfiguration.getApikey(), warsawUmApiConfiguration.getStopsResourceId()))
                .map(ResponseEntity::getBody)
                .orElse(new WarsawStops());
    }

    public WarsawTimetables getLinesOnStop(String stopId, String stopNumber) {
        return Optional.ofNullable(warsawPublicTransportClient.getTimetable(warsawUmApiConfiguration.getApikey(),
                        warsawUmApiConfiguration.getLinesOnStopsResourceId(),
                        stopId,
                        stopNumber,
                        ""))
                .map(ResponseEntity::getBody)
                .orElse(new WarsawTimetables());
    }
}
