package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

@Data
public class MetroTripStop {

    private String id;
    private int stopSequence;
    private int time;
}
