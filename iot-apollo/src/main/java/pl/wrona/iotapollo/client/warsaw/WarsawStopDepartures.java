package pl.wrona.iotapollo.client.warsaw;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class WarsawStopDepartures {

    private String line;
    private String brigade;
    private String route;

    private boolean isOnStop;
    private boolean hasTimetable;

    private LocalTime timetableDeparture;

    private String stopId;
    private String stopNumber;
    private String stopName;
    private float stopLon;
    private float stopLat;
    private long stopDistance;
    private String vehicleDirection;

}
