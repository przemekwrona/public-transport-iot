package pl.wrona.iot.timetable.reload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.client.warsaw.WarsawApiService;
import pl.wrona.iot.timetable.client.warsaw.WarsawDepartures;
import pl.wrona.iot.timetable.client.warsaw.WarsawLineOnStop;
import pl.wrona.iot.timetable.client.warsaw.WarsawStop;
import pl.wrona.iot.timetable.client.warsaw.WarsawStopService;
import pl.wrona.iot.timetable.properties.IotTimetablesProperties;
import pl.wrona.iot.timetable.sink.ParquetSink;
import pl.wrona.iot.warsaw.avro.WarsawTimetable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReloadService {
    private final WarsawStopService warsawStopService;
    private final WarsawApiService warsawApiService;

    private final IotTimetablesProperties iotTimetablesProperties;

    public void reloadAll() throws IOException {
        long startTime = System.nanoTime();

        List<WarsawStop> warsawStops = warsawStopService.getStops();

        LocalDate now = LocalDate.now();
        String path = String.format("%s/%s/%s.warsaw.timetable.parquet", iotTimetablesProperties.getDirPath(), now, now);
        try (ParquetSink<WarsawTimetable> sink = new ParquetSink<>(new Path(path), WarsawTimetable.getClassSchema())) {
            for (WarsawStop warsawStop : warsawStops) {
                process(warsawStop).forEach(timetable -> {
                    try {
                        sink.write(timetable);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        long endTime = System.nanoTime();
        log.info("App reload Warsaw timetables in {} minutes", TimeUnit.NANOSECONDS.toMinutes(endTime - startTime));
    }

    private List<WarsawTimetable> process(WarsawStop warsawStop) {
        return reload(warsawStop).stream()
                .flatMap(pair -> pair.getRight().stream())
                .map(timetable -> build(warsawStop, timetable))
                .collect(Collectors.toList());
    }

    private WarsawTimetable build(WarsawStop warsawStop, WarsawDepartures timetable) {
        WarsawTimetable avro = new WarsawTimetable();
        avro.setStopId(warsawStop.getGroup());
        avro.setStopNumber(warsawStop.getSlupek());
        avro.setStopName(warsawStop.getName());
        avro.setLat(warsawStop.getLat());
        avro.setLon(warsawStop.getLon());
        avro.setStopDirection(warsawStop.getDirection());
        avro.setLine(timetable.getLine());
        avro.setBrigade(timetable.getBrigade());
        avro.setLineDirection(timetable.getDirection());
        avro.setRoute(timetable.getRoute());
        avro.setTime(timetable.getTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return avro;
    }

    private List<Pair<WarsawLineOnStop, List<WarsawDepartures>>> reload(WarsawStop warsawStop) {
        return Optional.of(warsawStop)
                .map(stop -> warsawStopService.getLinesOnStop(stop.getGroup(), stop.getSlupek()))
                .map(linesOnStop -> linesOnStop.getLines().stream()
                        .map(stop -> WarsawLineOnStop.builder()
                                .stopId(linesOnStop.getStopId())
                                .stopNumber(linesOnStop.getStopNumber())
                                .lines(List.of(stop))
                                .build())
                        .collect(Collectors.toList()))
                .stream()
                .flatMap(Collection::stream)
                .map(stop -> Pair.of(stop, warsawApiService.getTimetable(stop.getStopId(), stop.getStopNumber(), stop.getLines().get(0))))
                .collect(Collectors.toList());
    }
}
