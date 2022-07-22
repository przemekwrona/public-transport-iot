package pl.wrona.iothermes.model.postgres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.wrona.iothermes.model.CityCode;
import pl.wrona.iothermes.model.VehicleType;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(VehicleLocationId.class)
public class VehicleLocation {

    @Enumerated(EnumType.STRING)
    private CityCode cityCode;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Id
    private String line;

    private float lat;

    private float lon;

    private String vehicleNumber;

    @Id
    private String brigade;

    @Id
    private Instant time;
}
