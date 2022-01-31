package pl.wrona.iothermes.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class VehicleLocation {

    private CityCode cityCode;
    private VehicleType vehicleType;
    private String line;
    private String lat;
    private String lon;
    private String vehicleNumber;
    private String brigade;
    private Instant time;

}
