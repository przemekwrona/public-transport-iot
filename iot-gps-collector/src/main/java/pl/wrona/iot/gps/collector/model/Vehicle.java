package pl.wrona.iot.gps.collector.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Vehicle {

    private String line;
    private VehicleType vehicleType;
    private Float lon;
    private Float lat;
    private String vehicleNumber;
    private String brigade;
    private LocalDateTime time;
}
