package pl.wrona.iot.timetable.sink;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class PathUtils {

    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_00");
    private static final DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_mm");

    public String getHourPath(String mask, LocalDateTime time, long frequency) {
        long module = time.getHour() % frequency;

        if (module == 0) {
            return mask.replace("{date}", time.format(HOUR_FORMATTER));
        }

        return getHourPath(mask, time.minusHours(1L), frequency);
    }

    public String getMinutePath(String mask, LocalDateTime time) {
        return mask.replace("{date}", time.format(MINUTE_FORMATTER));
    }

    public Path timetablePath(String dir, LocalDate date) {
        return Path.of(dir, date.format(DateTimeFormatter.ISO_DATE), String.format("%s.timetable.parquet", date));
    }
}
