package pl.wrona.iot.gps.collector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    private String line;
    private VehicleType vehicleType;
    private Float lon;
    private Float lat;
    private String vehicleNumber;
    private String brigade;
    private LocalDateTime time;
}
