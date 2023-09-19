package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

import java.util.List;

@Data
public class MetroTrip {

    private String routeId;
    private List<MetroTripStop> stops;
    private MetroFrequencies mondayFriday;
    private MetroFrequencies saturday;
    private MetroFrequencies sunday;
}
