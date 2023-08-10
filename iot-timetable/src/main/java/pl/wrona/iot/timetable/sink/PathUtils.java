package pl.wrona.iot.timetable.sink;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class PathUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_00");

    public String getPath(String mask, LocalDateTime time, long frequency) {
        long module = time.getHour() % frequency;

        if (module == 0) {
            return mask.replace("{date}", time.format(FORMATTER));
        }

        return getPath(mask, time.minusHours(1L), frequency);
    }
}
