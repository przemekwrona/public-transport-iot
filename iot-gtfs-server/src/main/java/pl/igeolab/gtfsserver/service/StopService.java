package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.igeolab.gtfsserver.entity.Stops;
import pl.igeolab.gtfsserver.repository.StopRepository;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class StopService {

    private final StopRepository stopRepository;

    public List<Stops> findAll() {
        return stopRepository.findAll();
    }

    @Transactional
    public void save(Collection<org.onebusaway.gtfs.model.Stop> gtfsStops) {
        List<Stops> stops = gtfsStops.stream()
                .map(stop -> Stops.builder()
                        .stopId(stop.getId().getId())
                        .stopCode(stop.getCode())
                        .stopName(stop.getName())
                        .stopDesc(stop.getDesc())
                        .stopLat(stop.getLat())
                        .stopLon(stop.getLon())
                        .zoneId(stop.getZoneId())
                        .stopUrl(stop.getUrl())
                        .locationType(stop.getLocationType())
                        .parentStation(stop.getParentStation())

                        .build())
                .toList();

        stopRepository.saveAll(stops);
    }

    public Stops findById(String stopId) {
        return stopRepository.findById(stopId).orElse(null);
    }

    public org.igeolab.iot.gtfs.server.api.model.Stops getStops() {
        var stops = stopRepository.findAll().stream()
                .map(stop -> new org.igeolab.iot.gtfs.server.api.model.Stop()
                        .stopId(stop.getStopId())
                        .stopCode(stop.getStopCode())
                        .stopName(stop.getStopName())
                        .stopDesc(stop.getStopDesc())
                        .stopLat(stop.getStopLat())
                        .stopLon(stop.getStopLon()))
                .toList();

        return new org.igeolab.iot.gtfs.server.api.model.Stops()
                .stops(stops);
    }
}
