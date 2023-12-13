package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

@Data
public class MetroStop {

    private String id;
    private String name;
    private double lat;
    private double lon;
}
