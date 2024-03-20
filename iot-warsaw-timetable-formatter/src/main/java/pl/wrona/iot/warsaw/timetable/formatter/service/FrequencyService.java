package pl.wrona.iot.warsaw.timetable.formatter.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Frequency;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class FrequencyService {

    public List<Frequency> getMetroM1Frequencies() {
        getFrequency(5, 0, 5, 33, 6);
        return List.of(getFrequency(5, 0, 5, 33, 6));
    }

    private Frequency getFrequency(int startHour, int startMinute, int endHour, int endMinute, int headway) {
        Frequency frequency = new Frequency();
        frequency.setStartTime(LocalTime.of(startHour, startMinute).toSecondOfDay());
        frequency.setEndTime(LocalTime.of(endHour, endMinute).toSecondOfDay());
        frequency.setHeadwaySecs((int) TimeUnit.SECONDS.toSeconds(headway));

        return frequency;
    }

    public List<Frequency> getMetroM2Frequencies() {
        return List.of();
    }
}
