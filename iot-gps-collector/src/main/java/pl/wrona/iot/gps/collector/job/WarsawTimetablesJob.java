package pl.wrona.iot.gps.collector.job;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.client.WarsawStopService;
import pl.wrona.iot.gps.collector.client.WarsawTimetableService;
import pl.wrona.iot.gps.collector.timetable.Timetable;
import pl.wrona.warsaw.transport.api.model.WarsawStopValue;
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@AllArgsConstructor
public class WarsawTimetablesJob implements Runnable {

    private final WarsawStopService warsawStopService;
    private final WarsawTimetableService warsawTimetableService;

    @Override
    public void run() {
        LocalDate now = LocalDate.now();

        warsawStopService.getStops().getResult()
                .forEach(stop -> {
                    String stopId = stop.getValues().stream().filter(v -> v.getKey().equals("zespol")).findFirst().map(WarsawStopValue::getValue).orElse("");
                    String stopNumber = stop.getValues().stream().filter(v -> v.getKey().equals("slupek")).findFirst().map(WarsawStopValue::getValue).orElse("");

                    warsawStopService.getLinesOnStop(stopId, stopNumber).getResult().forEach(lineOnStop -> {
                        String line = lineOnStop.getValues().stream().filter(v -> v.getKey().equals("linia")).findFirst().map(WarsawTimetableValue::getValue).orElse("");

                        warsawTimetableService.getTimetable(stopId, stopNumber, line).getResult().forEach(timetable -> {
                            LocalTime time = timetable.getValues().stream().filter(v -> v.getKey().equals("czas")).findFirst().map(WarsawTimetableValue::getValue).map(LocalTime::parse).orElse(null);
                            String brigade = timetable.getValues().stream().filter(v -> v.getKey().equals("brygada")).findFirst().map(WarsawTimetableValue::getValue).orElse("");
                            String direction = timetable.getValues().stream().filter(v -> v.getKey().equals("kierunek")).findFirst().map(WarsawTimetableValue::getValue).orElse("");
                            String route = timetable.getValues().stream().filter(v -> v.getKey().equals("trasa")).findFirst().map(WarsawTimetableValue::getValue).orElse("");

                            Timetable record = Timetable.builder()
                                    .stopId(stopId)
                                    .stopNumber(stopNumber)
                                    .line(line)
                                    .brigade(brigade)
                                    .direction(direction)
                                    .time(LocalDateTime.of(now, time))
                                    .build();

                        });
                    });
                });
    }

}
