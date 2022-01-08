package pl.wrona.iothermes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleLocation {

    private CityCode cityCode;
    private String line;
    private String lat;
    private String lon;
    private String vehicleNumber;
    private String brigade;
    private String time;

}
