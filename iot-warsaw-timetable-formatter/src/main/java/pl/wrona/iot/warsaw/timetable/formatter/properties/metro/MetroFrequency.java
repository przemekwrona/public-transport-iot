package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

import java.time.LocalTime;

@Data
public class MetroFrequency {

    private LocalTime startTime;
    private LocalTime endTime;
    private int timing;
}
