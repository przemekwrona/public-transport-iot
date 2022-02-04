package pl.wrona.iotdemeter.repositories.locations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Data
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
