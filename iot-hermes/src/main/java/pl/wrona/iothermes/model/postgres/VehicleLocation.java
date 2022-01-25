package pl.wrona.iothermes.model.postgres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.wrona.iothermes.model.CityCode;
import pl.wrona.iothermes.model.VehicleType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLocation {

    @Id
    @GeneratedValue(generator = "seq_vehicle_location")
    @SequenceGenerator(name = "seq_vehicle_location", sequenceName = "seq_vehicle_location", allocationSize = 1)
    private Long vehicleLocationId;

    @Enumerated(EnumType.STRING)
    private CityCode cityCode;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private String line;

    private String lat;

    private String lon;

    private String vehicleNumber;

    private String brigade;

    private LocalDateTime time;
}
