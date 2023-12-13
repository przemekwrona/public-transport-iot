package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Data;

import java.time.LocalTime;

@Data
public class MetroFrequency {

    private final static int ONE_DAY = 24 * 60 * 60;

    private LocalTime startTime;
    private LocalTime endTime;
    private int timing;

    public int getEndTimeSecondOfDay() {
        return startTime.isBefore(endTime) ? endTime.toSecondOfDay() : endTime.toSecondOfDay() + ONE_DAY;
    }
}
