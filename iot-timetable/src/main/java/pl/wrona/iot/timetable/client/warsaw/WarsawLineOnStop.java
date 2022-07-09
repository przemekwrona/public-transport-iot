package pl.wrona.iot.timetable.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WarsawLineOnStop {

    private String stopId;
    private String stopNumber;
    private List<String> lines;

    public boolean hasLine(String line) {
        return lines.contains(line);
    }

}
