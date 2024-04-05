package pl.igeolab.gtfsserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trips {

    @Id
    private String tripId;

    @ManyToOne
    @JoinColumn(name="route_id", nullable=false)
    private Routes route;

    @OneToMany(mappedBy = "trips")
    private Set<StopTimes> stopTimes;

    private String tripHeadsign;
    private String tripShortName;
    private String directionId;
    private String shapeId;

    @ManyToOne
    @JoinColumn(name="service_id", nullable=false)
    private CalendarDate calendarDate;
}
