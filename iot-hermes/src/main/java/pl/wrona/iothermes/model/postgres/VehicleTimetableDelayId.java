package pl.wrona.iothermes.model.postgres;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTimetableDelayId implements Serializable {

    private String line;
    private String brigade;
    private LocalDateTime time;
}
