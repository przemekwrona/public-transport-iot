package pl.wrona.iothermes.model.postgres;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wrona.iothermes.model.CityCode;
import pl.wrona.iothermes.model.VehicleType;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(VehicleTimetableDelayId.class)
public class VehicleTimetableDelay {

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

    private boolean isOnStop;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean hasTimetable;

    private String stopId;

    private String stopNumber;

    private String stopName;

    private Instant timetableDepartureDate;

    public boolean hasTimetable() {
        return this.hasTimetable;
    }

    public void setHasTimetable(boolean hasTimetable) {
        this.hasTimetable = hasTimetable;
    }

}
