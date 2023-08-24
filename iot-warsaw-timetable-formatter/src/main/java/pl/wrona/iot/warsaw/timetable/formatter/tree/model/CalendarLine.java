package pl.wrona.iot.warsaw.timetable.formatter.tree.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarLine {

    private String line;
    private String dateType;

    public static CalendarLine of(String line) {
        String[] tokens = line.split("\\s{2,}");
        return CalendarLine.builder()
                .line(tokens[0])
                .dateType(tokens[1])
                .build();
    }

}
