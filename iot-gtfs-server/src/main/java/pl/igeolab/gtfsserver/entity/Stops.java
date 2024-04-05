package pl.igeolab.gtfsserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Stops {

    @Id
    @Column(name = "STOP_ID")
    private String stopId;
    private String stopCode;
    private String stopName;
    private String stopDesc;
    private double stopLat;
    private double stopLon;
    private String zoneId;
    private String stopUrl;
    private int locationType;
    private String parentStation;

    @OneToMany(mappedBy = "stops")
    private Set<StopTimes> stopTimes;
}