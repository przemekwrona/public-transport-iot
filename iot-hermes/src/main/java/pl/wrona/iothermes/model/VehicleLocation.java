package pl.wrona.iothermes.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VehicleLocation {

    private CityCode cityCode;
    private VehicleType vehicleType;
    private String line;
    private float lat;
    private float lon;
    private String vehicleNumber;
    private String brigade;
    private LocalDateTime time;

}
