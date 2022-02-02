package pl.wrona.iotapollo.client;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class WarsawTimetable {

    private String stopId;
    private String stopNumber;
    private List<String> lines;

    public boolean hasLine(String line) {
        return lines.contains(line);
    }

}
