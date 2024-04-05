package pl.igeolab.gtfsserver.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StopTimes {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "stopId", column = @Column(name = "STOP_ID")),
            @AttributeOverride(name = "tripId", column = @Column(name = "TRIP_ID"))})
    private StopTimeId stopTimeId;

    @ManyToOne
    @JoinColumn(name="TRIP_ID", nullable=false, insertable=false, updatable=false)
    private Trips trips;

    @ManyToOne
    @JoinColumn(name="STOP_ID", nullable=false, insertable=false, updatable=false)
    private Stops stops;

    private int arrivalTime;
    private int departureTime;
    private int stopSequence;
    private String stopHeadsign;

    private int pickUpType;
    private int dropOffType;
    private double shapeDistTraveled;

}
