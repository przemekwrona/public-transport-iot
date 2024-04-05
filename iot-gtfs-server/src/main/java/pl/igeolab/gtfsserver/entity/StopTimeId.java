package pl.igeolab.gtfsserver.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopTimeId implements Serializable {

    private String stopId;
    private String tripId;
}
