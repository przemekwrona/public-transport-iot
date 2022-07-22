package pl.wrona.iothermes.model.postgres;

import lombok.*;
import pl.wrona.iothermes.model.CityCode;
import pl.wrona.iothermes.model.VehicleType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTimetableDelayId implements Serializable {

    private String line;
    private String brigade;
    private Instant time;
}
