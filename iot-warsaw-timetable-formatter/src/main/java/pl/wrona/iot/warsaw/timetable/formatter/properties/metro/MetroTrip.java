package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

import java.util.List;

@Data
public class MetroTrip {

    private String tripId;
    private String description;
    private List<MetroFrequency> frequencies;
}
