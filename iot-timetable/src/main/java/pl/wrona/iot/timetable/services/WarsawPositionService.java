package pl.wrona.iot.timetable.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Service;
import pl.wrona.iot.apollo.api.model.Position;
import pl.wrona.iot.apollo.api.model.Positions;
import pl.wrona.iot.timetable.client.warsaw.WarsawApiService;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarsawPositionService {
    public static final DateTimeFormatter WARSAW_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final WarsawApiService warsawApiService;

    private List<WarsawVehicle> vehicles;

    private Map<String, List<WarsawVehicle>> previousCollectedVehicles;
    private Map<String, List<WarsawVehicle>> currentCollectedVehicles;


    public List<WarsawVehicle> getPositions() {
        this.vehicles = warsawApiService.getPositions();
        this.previousCollectedVehicles = this.currentCollectedVehicles;
        this.currentCollectedVehicles = vehicles.stream()
                .collect(Collectors.groupingBy(WarsawVehicle::getVehicleNumber));

        return vehicles;
    }

    public Positions warsawPositions() {
        List<Position> positions = this.vehicles.stream()
                .map(v -> {
                    WarsawVehicle prev = previousCollectedVehicles.get(v.getVehicleNumber()).get(0);
                    WarsawVehicle curr = currentCollectedVehicles.get(v.getVehicleNumber()).get(0);

                    LocalDateTime pTime = LocalDateTime.parse(prev.getTime(), WARSAW_FORMATTER);
                    LocalDateTime cTime = LocalDateTime.parse(curr.getTime(), WARSAW_FORMATTER);

                    double meters = SloppyMath.haversinMeters(prev.getLat(), prev.getLon(), curr.getLat(), curr.getLon());
                    long seconds = Duration.between(pTime, cTime).getSeconds();

                    double velocity = (meters / ((double) seconds)) * 3600 / 1000;

                    return new Position()
                            .line(curr.getLines())
                            .brigade(curr.getBrigade())
                            .vehicleNumber(v.getVehicleNumber())
                            .date(OffsetDateTime.MAX)
                            .lon(curr.getLon())
                            .lat(curr.getLat())
                            .distance(Double.valueOf(meters).intValue())
                            .time(seconds)
                            .velocity(Double.valueOf(velocity).intValue())
                            .azimuth(BigDecimal.ZERO);
                })
                .collect(Collectors.toList());

        return new Positions()
                .positions(positions);
    }
}
