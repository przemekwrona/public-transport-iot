package pl.wrona.iot.warsaw.timetable.formatter.tree.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StopDetails {
    private String stopId;
    private String stopName;
    private String x;
    private String y;
    private String pu;

    public static StopDetails of(String line) {
        String[] tokens = line.split("\\s{2,}");

        return StopDetails.builder()
                .stopId(tokens[0])
                .stopName(tokens[1])
                .y(tokens[3].replace("Y=", "").trim())
                .x(tokens[4].replace("X=", "").trim())
                .pu(tokens[4].replace("Pu=", "").trim())
                .build();
    }
}
