package pl.wrona.iot.timetable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TimetablesId.class)
public class Timetables {

    @Id
    private String line;

    @Id
    private String brigade;

    @Id
    private LocalDateTime timetableDepartureDate;

    private String stopId;
    private String stopNumber;
    private String stopName;

    private float lon;
    private float lat;

    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
}
