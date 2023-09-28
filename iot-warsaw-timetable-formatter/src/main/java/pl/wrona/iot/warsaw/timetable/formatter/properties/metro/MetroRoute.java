package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

import java.util.List;

@Data
public class MetroRoute {

    private String id;
    private String longName;
    private List<MetroStop> stops;
    private List<MetroTrip> trips;
}
