package pl.wrona.iot.gps.collector.job;

import lombok.AllArgsConstructor;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.client.WarsawStopService;
import pl.wrona.iot.gps.collector.client.WarsawTimetableService;
import pl.wrona.iot.gps.collector.config.GCloudProperties;
import pl.wrona.iot.gps.collector.sink.GCloudFileNameProvider;
import pl.wrona.iot.gps.collector.sink.GCloudSink;
import pl.wrona.iot.gps.collector.sink.WarsawTimetableGenericRecordMapper;
import pl.wrona.iot.gps.collector.timetable.Timetable;
import pl.wrona.warsaw.transport.api.model.WarsawStopValue;
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@AllArgsConstructor
public class WarsawTimetablesJob implements Runnable {

    private final WarsawStopService warsawStopService;
    private final WarsawTimetableService warsawTimetableService;

    private final GCloudFileNameProvider gCloudFileNameProvider;

    private GCloudProperties gCloudProperties;

    @Override
    public void run() {
        LocalDate now = LocalDate.now();

        try (GCloudSink<Timetable> gCloudSink = new GCloudSink<>(new WarsawTimetableGenericRecordMapper(gCloudProperties.warsawTimetableBucket().getSchema()), new Path(gCloudFileNameProvider.vehiclesTimetables(now)), gCloudProperties)) {
            warsawStopService.getStops().getResult()
                    .forEach(stop -> {
                        String stopId = stop.getValues().stream().filter(v -> v.getKey().equals("zespol")).findFirst().map(WarsawStopValue::getValue).orElse("");
                        String stopNumber = stop.getValues().stream().filter(v -> v.getKey().equals("slupek")).findFirst().map(WarsawStopValue::getValue).orElse("");

                        warsawStopService.getLinesOnStop(stopId, stopNumber).getResult().forEach(lineOnStop -> {
                            String line = lineOnStop.getValues().stream().filter(v -> v.getKey().equals("linia")).findFirst().map(WarsawTimetableValue::getValue).orElse("");

                            warsawTimetableService.getTimetable(stopId, stopNumber, line).getResult().forEach(timetable -> {
                                LocalDateTime dateTime = timetable.getValues().stream()
                                        .filter(v -> v.getKey().equals("czas"))
                                        .findFirst()
                                        .map(WarsawTimetableValue::getValue)
                                        .map(v -> v.split(":"))
                                        .map(array -> {
                                            if (Integer.parseInt(array[0]) >= 24) {
                                                return LocalDateTime.of(now.plusDays(1), LocalTime.of(Integer.parseInt(array[0]) - 24, Integer.parseInt(array[1]), Integer.parseInt(array[2])));
                                            } else {
                                                return LocalDateTime.of(now, LocalTime.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2])));
                                            }
                                        })
                                        .orElseThrow();

                                String brigade = timetable.getValues().stream().filter(v -> v.getKey().equals("brygada")).findFirst().map(WarsawTimetableValue::getValue).orElse("");
                                String direction = timetable.getValues().stream().filter(v -> v.getKey().equals("kierunek")).findFirst().map(WarsawTimetableValue::getValue).orElse("");
                                String route = timetable.getValues().stream().filter(v -> v.getKey().equals("trasa")).findFirst().map(WarsawTimetableValue::getValue).orElse("");

                                Timetable record = Timetable.builder()
                                        .stopId(stopId)
                                        .stopNumber(stopNumber)
                                        .line(line)
                                        .brigade(brigade)
                                        .direction(direction)
                                        .time(dateTime)
                                        .build();

                                gCloudSink.save(record);

                            });
                        });
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
