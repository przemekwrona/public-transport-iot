package pl.wrona.iot.warsaw.timetable.formatter.tree.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LineNumber {

    private String line;
    private String type;

    public static LineNumber of(String line) {
        String[] lineAndTypeTokens = line.replace("Linia:", "").trim().split("\\s{2,}");
        String lineNumber = lineAndTypeTokens[0].trim();
        String type = lineAndTypeTokens[1].replace("-", "").trim();

        return LineNumber.builder()
                .line(lineNumber)
                .type(type)
                .build();
    }
}
