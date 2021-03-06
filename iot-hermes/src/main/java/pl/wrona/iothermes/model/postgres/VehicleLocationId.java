
package pl.wrona.iothermes.model.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLocationId implements Serializable {

    private String line;
    private String brigade;
    private LocalDateTime time;
}
