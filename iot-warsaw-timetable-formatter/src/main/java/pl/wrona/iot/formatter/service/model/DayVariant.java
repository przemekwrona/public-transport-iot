package pl.wrona.iot.formatter.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DayVariant {

    private String dayType;
    private String description;

    public static DayVariant of(String line) {
        String[] tokens = line.split("\\s{2,}");
        return DayVariant.builder()
                .dayType(tokens[0])
                .description(tokens[1])
                .build();
    }
}
