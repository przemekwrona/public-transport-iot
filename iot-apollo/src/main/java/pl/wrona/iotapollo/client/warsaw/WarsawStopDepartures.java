package pl.wrona.iotapollo.client.warsaw;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class WarsawStopDepartures {

    private String line;
    private String brigade;
    private String direction;
    private String route;
    private LocalTime time;

    private String stopId;
    private String stopNumber;
    private String stopName;
    private float stopLon;
    private float stopLat;
    private String stopDirection;

}
