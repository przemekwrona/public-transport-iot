package pl.wrona.iot.gps.collector.timetable;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Timetable {

    private String stopId;
    private String stopNumber;
    private String line;
    private String brigade;
    private String direction;
    private LocalDateTime time;
}
