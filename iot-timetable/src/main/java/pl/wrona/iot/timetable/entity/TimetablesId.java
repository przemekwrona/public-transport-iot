package pl.wrona.iot.timetable.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimetablesId implements Serializable {

    private String line;
    private String brigade;
    private LocalDateTime timetableDepartureDate;
}
