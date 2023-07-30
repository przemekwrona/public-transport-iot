package pl.wrona.iot.timetable.reload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.client.warsaw.WarsawApiService;
import pl.wrona.iot.timetable.client.warsaw.WarsawDepartures;
import pl.wrona.iot.timetable.client.warsaw.WarsawLineOnStop;
import pl.wrona.iot.timetable.client.warsaw.WarsawStop;
import pl.wrona.iot.timetable.client.warsaw.WarsawStopService;
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

    @Value("${iot.timetables.dir.path}")
    private String timetableDirectory;

    public void reloadAll() throws IOException {
        long startTime = System.nanoTime();

        List<WarsawStop> warsawStops = warsawStopService.getStops().stream()
                .limit(10)
                .collect(Collectors.toList());

        String path = String.format("%s/%s.timetable.parquet", timetableDirectory, LocalDate.now());
        HadoopOutputFile hadoopPath = HadoopOutputFile.fromPath(new Path(path), new Configuration());

        try (ParquetWriter<Object> writer = AvroParquetWriter.builder(hadoopPath)
                .withSchema(WarsawTimetable.getClassSchema())
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .build()) {

            for (WarsawStop warsawStop : warsawStops) {
                process(warsawStop).forEach(timetable -> {
                    try {
                        writer.write(timetable);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (java.io.IOException e) {
            System.out.printf("Error writing parquet file %s%n", e.getMessage());
            e.printStackTrace();
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
        avro.setLine(timetable.getLine());
        avro.setDirection(timetable.getDirection());
        avro.setBrigade(timetable.getBrigade());
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
