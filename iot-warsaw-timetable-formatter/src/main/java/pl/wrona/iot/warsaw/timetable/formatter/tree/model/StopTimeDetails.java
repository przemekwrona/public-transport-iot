package pl.wrona.iot.warsaw.timetable.formatter.tree.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@Builder
public class StopTimeDetails {

    private String tripId;
    private String stopId;
    private String dayType;
    private LocalTime time;
    private int hour;
    private int minutes;
    private String attribute;

    public static StopTimeDetails of(String line) {
        List<String> stopTimeTokens = Arrays.stream(line.trim().split(" ")).filter(v -> !"".equals(v)).collect(Collectors.toList());
        int hours = Integer.parseInt(stopTimeTokens.get(3).split("\\.")[0]);
        int minutes = Integer.parseInt(stopTimeTokens.get(3).split("\\.")[1]);
        return StopTimeDetails.builder()
                .tripId(stopTimeTokens.get(0))
                .stopId(stopTimeTokens.get(1))
                .dayType(stopTimeTokens.get(2))
                .hour(hours)
                .minutes(minutes)
                .attribute(stopTimeTokens.size() > 4 ? stopTimeTokens.get(4) : "")
                .build();
    }

    public long toSeconds() {
        return TimeUnit.HOURS.toSeconds(hour) + TimeUnit.MINUTES.toSeconds(minutes);
    }
}
