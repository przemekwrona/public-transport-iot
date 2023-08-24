package pl.wrona.iot.warsaw.timetable.formatter.tree.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LineVariant {
    private String line;
    private String type;
    private String code;
    private String fromStop;
    private String destinationStop;
    private String direction;
    private int pos;

    public static LineVariant of(String line, String variant) {
        LineNumber lineNumber = LineNumber.of(line);

        String[] tokens = variant.replace(",", "").split("\\s{2,}");

        String code = tokens[0];
        String fromStop = tokens[1].trim();
        String fromStopCityCode = tokens[2];
        String destinationStop = tokens[4].trim();
        String directionStopCityCode = tokens[5];
        String direction = tokens[6].replace("Kier.", "").trim();
        int position = Integer.parseInt(tokens[7].replace("Poz.", "").trim());

        return LineVariant.builder()
                .line(lineNumber.getLine())
                .type(lineNumber.getType())
                .code(code)
                .fromStop(fromStop)
                .destinationStop(destinationStop)
                .direction(direction)
                .pos(position)
                .build();
    }
}
