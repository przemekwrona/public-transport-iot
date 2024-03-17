package pl.wrona.iot.gtfs.collector.warszawa;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimetableUtils {

    private static final DateTimeFormatter FTP_7Z_TIMETABLE_FORMAT = DateTimeFormatter.ofPattern("'RA'YYMMdd'.7z'");
    private static final DateTimeFormatter FTP_TXT_TIMETABLE_FORMAT = DateTimeFormatter.ofPattern("'RA'YYMMdd'.txt'");

    String get7zFileName(LocalDate date) {
        return date.format(FTP_7Z_TIMETABLE_FORMAT);
    }

    String getTxtFileName(LocalDate date) {
        return date.format(FTP_TXT_TIMETABLE_FORMAT);
    }
}
