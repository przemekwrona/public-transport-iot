package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

import java.util.List;

@Data
public class MetroFrequencies {

    private List<MetroFrequency> frequencies;
}
