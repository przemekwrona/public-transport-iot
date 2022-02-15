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
import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTimetableDelay {

    @Id
    @GeneratedValue(generator = "seq_vehicle_timetable_delay")
    @SequenceGenerator(name = "seq_vehicle_timetable_delay", sequenceName = "seq_vehicle_timetable_delay", allocationSize = 1)
    private Long vehicleLocationId;

    @Enumerated(EnumType.STRING)
    private CityCode cityCode;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private String line;

    private float lat;

    private float lon;

    private String vehicleNumber;

    private String brigade;

    private Instant time;

    private boolean isOnStop;

    private String stopId;

    private String stopNumber;

    private String stopName;

    private Instant timetableDate;

}
