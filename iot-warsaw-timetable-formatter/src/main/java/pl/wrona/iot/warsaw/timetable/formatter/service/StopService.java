package pl.wrona.iot.warsaw.timetable.formatter.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Stop;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StopService {

    public static final String STOP_SECTION = "*ZP";

    private static final String EMPTY_X = "xxx.xxxxxxxx";
    private static final String EMPTY_Y = "yyy.yyyyyyyy";

    private Map<String, Stop> stops;


    public void process(WarsawTree tree) {
        WarsawTree.Node warsawStops = tree.getNode().getNode(STOP_SECTION);

        this.stops = warsawStops.getNodes().stream()
                .map(warsawStop -> {
                    WarsawStopInfo stopInfo = getStopInfo(warsawStop.getValue());

                    return warsawStop.getNode("*PR").getNodes().stream()
                            .map(subStop -> {
                                WarsawSubStopInfo subStopInfo = getSubStopInfo(subStop.getValue());

                                AgencyAndId stopId = new AgencyAndId();
                                stopId.setId(subStopInfo.getStopId());

                                Stop stop = new Stop();
                                stop.setId(stopId);
                                stop.setName(stopInfo.getStopName());
                                stop.setDirection(subStopInfo.getDirection());
                                stop.setLon(subStopInfo.getX());
                                stop.setLat(subStopInfo.getY());
                                return stop;
                            }).collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(stop -> stop.getId().getId(), Function.identity()));
    }

    private WarsawStopInfo getStopInfo(String warsawStop) {
        String[] tokens = warsawStop.split("\\s{2,}");
        String stopId = tokens[0];
        String stopName = tokens[1];
        String city = tokens[3];

        return WarsawStopInfo.builder()
                .stopId(stopId)
                .stopName(stopName)
                .city(city)
                .build();
    }

    private WarsawSubStopInfo getSubStopInfo(String warsawSubStop) {
        String[] tokens = warsawSubStop.split("\\s{2,}");

        String stopId = tokens[0];
        String stopName = tokens[2].replace(",", "").trim();
        String direction = tokens[3].trim().replace("Kier.:", "").replace(",", "").trim();
        String y = tokens[4].trim().replace("Y=", "").trim();
        String x = tokens[5].trim().replace("X=", "").trim();
        String pu = tokens[6].trim().replace("Pu=", "");

        return WarsawSubStopInfo.builder()
                .stopId(stopId)
                .street(stopName)
                .direction(direction)
                .x(EMPTY_X.equals(x) ? 0.0 : Double.parseDouble(x))
                .y(EMPTY_Y.equals(y) ? 0.0 : Double.parseDouble(y))
                .pu(pu)
                .build();
    }

    public Stop findStopByIf(String stopId) {
        return stops.get(stopId);
    }

    public List<Stop> getAll() {
        return new ArrayList<>(stops.values());
    }

    @Data
    @Builder
    private static class WarsawStopInfo {
        private final String stopId;
        private final String stopName;
        private final String city;
    }

    @Data
    @Builder
    private static class WarsawSubStopInfo {
        private final String stopId;
        private final String street;
        private final String direction;
        private final double x;
        private final double y;
        private final String pu;
    }

}
